package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.CalendarTools;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Tittle: Caller.java
 * @Author: wsy
 * @Class_name: Caller
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Analyse twsp and briefing files and import them to MySQL database.
 * @Version: V1.0
 * @Date: 2017/12/25 22:14
 */

@Controller
public class Caller {

    private String twspSrcPath;
    private String twspAnalysisProgram;
    private String twspSqlPath;
    private String briefingSrcPath;
    private String briefingAnalysisProgram;
    private String briefingSqlPath;
    private String mysqlHost;
    private String mysqlUser;
    private String mysqlPassword;
    private String mysqlPort;
    private String mysqlDatabase;
    private String cmdHead = "PYTHON ";
    private String propertiesFilePath;
    private String reportImportAssistantProgram;
    private String criticalPathProgram;
    private Calendar startDate;
    private Calendar endDate;

    @Autowired
    private CallCMD callCMD;

    @Autowired
    private ExecuteSqlFile executeSqlFile;

    @Autowired
    private GlobalProperties globalProperties;

    public void initializeAll() throws Exception {
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(new InputStreamReader(inputStream, "utf-8"));
        inputStream.close();
        this.twspSrcPath = properties.getProperty("copy.twsp.destPath");
        this.twspAnalysisProgram = properties.getProperty("program.twsp.analysePath");
        this.twspSqlPath = properties.getProperty("program.twsp.sqlPath");
        this.briefingSrcPath = properties.getProperty("copy.briefing.destPath");
        this.briefingAnalysisProgram = properties.getProperty("program.briefing.analysePath");
        this.briefingSqlPath = properties.getProperty("program.briefing.sqlPath");
        this.startDate = CalendarTools.StringToCalendar(properties.getProperty("start.date"), "yyyy-MM-dd");
        this.endDate = CalendarTools.StringToCalendar(properties.getProperty("end.date"), "yyyy-MM-dd");
        this.mysqlHost = properties.getProperty("MySQL.host");
        this.mysqlUser = properties.getProperty("MySQL.user");
        this.mysqlPassword = properties.getProperty("MySQL.password");
        this.mysqlPort = properties.getProperty("MySQL.port");
        this.mysqlDatabase = properties.getProperty("MySQL.database");
        this.reportImportAssistantProgram = properties.getProperty("program.reportImportAssistant.path");
        this.criticalPathProgram = properties.getProperty("program.critical.path");
    }

    public void startAnalyseTwsp() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: startAnalyseTwsp
         * @Return: void
         * @Param: []
         * @Description: Convert twsp files into sql files.
         * @Date: 17-11-14 下午5:30
         */
        Calendar processCalendar = (Calendar) startDate.clone();
        ArrayList<String> cmdCommandArr = new ArrayList<String>();
        StringBuffer fullCMDCommand = new StringBuffer();
        int lastYear = endDate.get(Calendar.YEAR);
        int targetYear = processCalendar.get(Calendar.YEAR);
        while (targetYear <= lastYear) {
            fullCMDCommand.setLength(0);
            fullCMDCommand.append(cmdHead);
            fullCMDCommand.append(twspAnalysisProgram);
            fullCMDCommand.append(" -s \"");
            fullCMDCommand.append(twspSrcPath);
            fullCMDCommand.append("\" -d \"");
            fullCMDCommand.append(twspSqlPath);
            fullCMDCommand.append("\" -y ");
            fullCMDCommand.append(targetYear);
            cmdCommandArr.add(fullCMDCommand.toString());
            processCalendar.add(Calendar.YEAR, 1);
            targetYear = processCalendar.get(Calendar.YEAR);
        }
        for (Iterator<String> it = cmdCommandArr.iterator(); it.hasNext(); ) {
            callCMD.executeCmd(it.next());
        }
//        Because analyse twsp count 1 point, addCurrentCount out of the for-loop
        globalProperties.addCurrentCount(globalProperties.CALLER_ANALYSE_TWSP);
    }

    public void startAnalyseBriefing() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: startAnalyseBriefing
         * @Return: void
         * @Param: []
         * @Description: Convert briefing files into sql files.
         * @Date: 2017/11/13 21:33
         **/

        StringBuffer fullCMDCommand = new StringBuffer();
        fullCMDCommand.setLength(0);
        fullCMDCommand.append(cmdHead);
        fullCMDCommand.append(briefingAnalysisProgram);
        fullCMDCommand.append(" -s \"");
        fullCMDCommand.append(briefingSrcPath);
        fullCMDCommand.append("\" -d \"");
        fullCMDCommand.append(briefingSqlPath);
        fullCMDCommand.append("\"");

        callCMD.executeCmd(fullCMDCommand.toString());
        globalProperties.addCurrentCount(globalProperties.CALLER_ANALYSE_BRIEFING);
    }

    public void startImportSql() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: startImportSql
         * @Return: void
         * @Param: []
         * @Description: Import TWSP and briefing sql files to database.
         * @Date: 17-11-29 上午12:12
         */

        Calendar processCalendar = (Calendar) startDate.clone();
        ArrayList<String> cmdCommandArr = new ArrayList<String>();
        StringBuffer fullCMDCommand = new StringBuffer();
        StringBuffer cmdHead = new StringBuffer();
        String cmdTail = ".sql\"";
        cmdHead.append("mysql.exe --force --comments --default-character-set=utf8 --host=");
        cmdHead.append(mysqlHost);
        cmdHead.append(" --port=");
        cmdHead.append(mysqlPort);
        cmdHead.append(" --database=");
        cmdHead.append(mysqlDatabase);
        cmdHead.append(" --user=");
        cmdHead.append(mysqlUser);
        cmdHead.append(" --password=");
        cmdHead.append(mysqlPassword);
        cmdHead.append(" < \"");

//        import TWSP sql files to database
        while (!processCalendar.after(endDate)) {
//            fullCMDCommand.setLength(0);
//            fullCMDCommand.append(cmdHead);
//            fullCMDCommand.append(twspSqlPath);
//            fullCMDCommand.append("\\report_");
//            fullCMDCommand.append(CalendarTools.calendarToString(processCalendar, "yyyyMMdd"));
//            fullCMDCommand.append(cmdTail);
//            cmdCommandArr.add(fullCMDCommand.toString());

            fullCMDCommand.setLength(0);
            fullCMDCommand.append(twspSqlPath);
            fullCMDCommand.append("\\report_");
            fullCMDCommand.append(CalendarTools.calendarToString(processCalendar, "yyyyMMdd"));
            fullCMDCommand.append(".sql");
            cmdCommandArr.add(fullCMDCommand.toString());
            processCalendar.add(Calendar.DATE, 1);
        }
//        for (Iterator<String> it = cmdCommandArr.iterator(); it.hasNext(); ) {
//            callCMD.executeCmd(it.next());
//            globalProperties.addCurrentCount(globalProperties.CALLER_IMPORT_SQL);
//        }

//        import briefing sql files to database
//        cmdCommandArr.clear();
//        fullCMDCommand.setLength(0);
//        fullCMDCommand.append(cmdHead);
//        fullCMDCommand.append(briefingSqlPath);
//        fullCMDCommand.append("\\summary_");
//        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
//        fullCMDCommand.append(dateFormat.format(new Date()));
//        fullCMDCommand.append(cmdTail);

        fullCMDCommand.setLength(0);
        fullCMDCommand.append(briefingSqlPath);
        fullCMDCommand.append("\\summary_");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fullCMDCommand.append(dateFormat.format(new Date()));
        fullCMDCommand.append(".sql");
        cmdCommandArr.add(fullCMDCommand.toString());

        executeSqlFile.initializeAll();
        executeSqlFile.execute(cmdCommandArr);
//        globalProperties.addCurrentCount(globalProperties.CALLER_IMPORT_SQL);
    }

    public void startReportImportAssistant() throws Exception{
        /**
         * @Author: wsy
         * @MethodName: startReportImportAssistant
         * @Return: void
         * @Param: []
         * @Description:  Start reportImportAssistant program.
         * @Date: 2017/12/25 22:05
         */

        StringBuffer fullCMDCommand = new StringBuffer();
        fullCMDCommand.append(reportImportAssistantProgram);
        fullCMDCommand.append(" -s ");
        fullCMDCommand.append(briefingSrcPath);
        fullCMDCommand.append(" -dI ");
        fullCMDCommand.append(mysqlHost);
        fullCMDCommand.append(" -dN ");
        fullCMDCommand.append(mysqlDatabase);
        fullCMDCommand.append(" -dU ");
        fullCMDCommand.append(mysqlUser);
        fullCMDCommand.append(" -dP ");
        fullCMDCommand.append(mysqlPassword);

        callCMD.executeCmd(fullCMDCommand.toString());
        globalProperties.addCurrentCount(globalProperties.getNumofDaystoProcessed()
                * globalProperties.CALLER_REPORT_IMPORT_ASSISTANT);
    }

    public void startAnalyseCriticalPath() throws Exception{
        /**
         * @Author: wsy
         * @MethodName: startAnalyseCriticalPath
         * @Return: void
         * @Param: []
         * @Description:  Start analyse critical path.
         * @Date: 2017/12/25 22:05
         */

        StringBuffer fullCMDCommand = new StringBuffer();
        fullCMDCommand.append("PYTHON ");
        fullCMDCommand.append(criticalPathProgram);
        fullCMDCommand.append(" -s \"");
        fullCMDCommand.append(CalendarTools.calendarToString(startDate,"yyyy-MM-dd"));
        fullCMDCommand.append("\" -e \"");
        fullCMDCommand.append(CalendarTools.calendarToString(endDate,"yyyy-MM-dd"));
        fullCMDCommand.append("\" -i \"");
        fullCMDCommand.append(mysqlHost);
        fullCMDCommand.append("\" -n \"");
        fullCMDCommand.append(mysqlDatabase);
        fullCMDCommand.append("\" -u \"");
        fullCMDCommand.append(mysqlUser);
        fullCMDCommand.append("\" -k \"");
        fullCMDCommand.append(mysqlPassword);
        fullCMDCommand.append("\" -p \"");
        fullCMDCommand.append(mysqlPort);
        fullCMDCommand.append("\"");

        callCMD.executeCmd(fullCMDCommand.toString());
        globalProperties.addCurrentCount(globalProperties.getNumofDaystoProcessed()
                * globalProperties.CALLER_ANALYSE_CRITICAL_PATHT);
    }
}
