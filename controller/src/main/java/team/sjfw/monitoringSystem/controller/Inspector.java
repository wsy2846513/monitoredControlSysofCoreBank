package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.CalendarTools;
import pers.wsy.tools.SafeProperties;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * @Tittle: Inspector.java
 * @Author: wsy
 * @Class_name: Inspector
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Inspect the parameters in properties file.
 * @Version: V1.0
 * @Date: 2017/12/17 16:21
 */

@Controller
public class Inspector {

    private String autoImportSwtich;
    private String autoImportTime;
    private String propertiesFilePath;
    private String startDate;
    private String endDate;
    private String latestDate;
    private String twspSrcPath;
    private String twspDestPath;
    private String twspFileName;
    private String briefingSrcPath;
    private String briefingDestPath;
    private String briefingFileName;
    private String twspAnalysePath;
    private String twspSQLPath;
    private String briefingAnalysePath;
    private String briefingSQLPath;
    private String criticalPath;
    private String reportImportAssistantPath;
    private String MySQLHost;
    private String MySQLUser;
    private String MySQLPassword;
    private String MySQLPort;
    private String MySQLDatabase;

    @Autowired
    public Inspector(GlobalProperties globalProperties) {
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
    }

    public void inspectAll() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: inspectAll
         * @Return: void
         * @Param: []
         * @Description: Inspect all properties.
         * @Date: 17-12-18 下午7:07
         */
//        Load all properties.
        loadProperties();

//        Inspect whether the date has correct format and relation.
        inspectDate();

//        Inspect whether the path has correct format.
        inspectPath();

//        Inspect whether the aoto config has correct format.
        inspetAutoConfig();

//        Inspect whether MySQL config has correct format.
        inspectMySQL();
    }

    private void loadProperties() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: loadProperties
         * @Return: void
         * @Param: []
         * @Description: Load all properties form environment.properties file.
         * @Date: 17-12-18 下午7:08
         */
        SafeProperties properties = new SafeProperties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(new InputStreamReader(inputStream, "utf-8"));
        inputStream.close();

        autoImportSwtich = properties.getProperty("autoImport.swtich");
        autoImportTime = properties.getProperty("autoImport.time");
        startDate = properties.getProperty("start.date");
        endDate = properties.getProperty("end.date");
        latestDate = properties.getProperty("latest.date");
        twspSrcPath = properties.getProperty("copy.twsp.srcPath");
        twspDestPath = properties.getProperty("copy.twsp.destPath");
        twspFileName = properties.getProperty("copy.twsp.fileName");
        briefingSrcPath = properties.getProperty("copy.briefing.srcPath");
        briefingDestPath = properties.getProperty("copy.briefing.destPath");
        briefingFileName = properties.getProperty("copy.briefing.fileName");
        twspAnalysePath = properties.getProperty("program.twsp.analysePath");
        twspSQLPath = properties.getProperty("program.twsp.sqlPath");
        briefingAnalysePath = properties.getProperty("program.briefing.analysePath");
        briefingSQLPath = properties.getProperty("program.briefing.sqlPath");
        criticalPath = properties.getProperty("program.critical.path");
        reportImportAssistantPath = properties.getProperty("program.reportImportAssistant.path");
        MySQLHost = properties.getProperty("MySQL.host");
        MySQLUser = properties.getProperty("MySQL.user");
        MySQLPassword = properties.getProperty("MySQL.password");
        MySQLPort = properties.getProperty("MySQL.port");
        MySQLDatabase = properties.getProperty("MySQL.database");
    }

    private void inspectMySQL() throws Exception{
        /**
         * @Author: wsy
         * @MethodName: inspectMySQL
         * @Return: void
         * @Param: []
         * @Description: Inspect whether MySQL config is correct.
         * @Date: 17-12-18 下午7:11
         */
//        Inspect MySQL IP
        if (!Pattern.matches("^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])" +
                "\\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])" +
                "\\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])" +
                "\\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$", MySQLHost)) {
            throw new Exception("\"MySQL数据库IP\"配置不正确。");
        }
//        Inspect MySQL port
        if (!Pattern.matches("\\d+", MySQLPort)) {
            throw new Exception("\"MySQL数据库端口\"配置不正确。");
        }
    }
    private void inspetAutoConfig() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: inspetAutoConfig
         * @Return: void
         * @Param: []
         * @Description: Inspect whether the aoto config has correct format.
         * @Date: 17-12-18 下午7:02
         */
        if (!Pattern.matches("on|off", autoImportSwtich)) {
            throw new Exception("\"自动导入开关\"配置不正确。");
        }

        if (!Pattern.matches("(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])", autoImportTime)) {
            throw new Exception("\"自动导入时间\"配置不正确。");
        }
    }

    private void inspectPath() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: inspectPath
         * @Return: void
         * @Param: []
         * @Description: Inspect whether the path has correct format.
         * @Date: 17-12-18 下午7:03
         */

        String regex = "^.*(?<!(\\\\)|(/))$";
        String messageTail = "不要以'\\'或'/'结尾";

        if (!Pattern.matches(regex, twspSrcPath)) {
            throw new Exception("\"twsp文件复制输入路径\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, twspDestPath)) {
            throw new Exception("\"twsp文件复制输入路径\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, briefingSrcPath)) {
            throw new Exception("\"简报文件复制输入路径\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, briefingDestPath)) {
            throw new Exception("\"简报文件复制输出路径\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, twspAnalysePath)) {
            throw new Exception("\"twsp解析程序(Python)路径及名称\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, twspSQLPath)) {
            throw new Exception("\"twsp解析程序(Python)生成的文件路径\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, briefingAnalysePath)) {
            throw new Exception("\"简报解析程序(Python)路径及名称\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, briefingSQLPath)) {
            throw new Exception("\"简报解析程序(Python)生成的文件路径\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, criticalPath)) {
            throw new Exception("\"关键路径计算程序(Python)路径及名称\"配置不正确。" + messageTail);
        }

        if (!Pattern.matches(regex, reportImportAssistantPath)) {
            throw new Exception("\"日报导入助手(exe)路径\"配置不正确。" + messageTail);
        }
    }

    private void inspectDate() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: inspectDate
         * @Return: void
         * @Param: []
         * @Description: Inspect whether the path has correct format and relation.
         * @Date: 17-12-18 下午7:05
         */

//        Inspect whether the path has correct format.
        inspectDateFormat();

//        Inspect whether the path has correct relation.
        inspectDateRelation();
    }

    private void inspectDateFormat() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: inspectDateFormat
         * @Return: void
         * @Param: []
         * @Description: Inspect date format (including leap year).
         * @Date: 2017/12/17 17:35
         */

        String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

        if (!Pattern.matches(regex, startDate)) {
            throw new Exception("\"开始日期\"配置不正确。");
        }

        if (!Pattern.matches(regex, endDate)) {
            throw new Exception("\"结束日期\"配置不正确。");
        }

        if (!Pattern.matches(regex, latestDate)) {
            throw new Exception("\"已处理日期'latestDate'\"配置不正确。");
        }
    }

    private void inspectDateRelation() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: inspectDateRelation
         * @Return: void
         * @Param: []
         * @Description: Inspect whether start date is after end date.
         * @Date: 2017/12/17 17:27
         */

        Calendar startCalendar = CalendarTools.StringToCalendar(startDate, "yyyy-MM-dd");
        Calendar endCalendar = CalendarTools.StringToCalendar(endDate, "yyyy-MM-dd");
        if (startCalendar.after(endCalendar)) {
            throw new Exception("\"开始日期\"晚于\"结束日期\"。");
        }
    }
}
