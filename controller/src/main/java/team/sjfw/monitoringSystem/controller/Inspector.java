package team.sjfw.monitoringSystem.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wsy.tools.CalendarTools;
import pers.wsy.tools.SafeProperties;
import team.sjfw.monitoringSystem.controller.config.InspectorConfig;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InspectorConfig.class)
@Controller
public class Inspector {

    private String propertiesFilePath;
    private String startDate;
    private String endDate;
    private String twspSrcPath;

    @Autowired
    private GlobalProperties globalProperties;

    //    public Inspector(GlobalProperties globalProperties) {
    public Inspector() {
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
    }


    private void loadProperties() throws Exception {
//        Load the properties.
        SafeProperties properties = new SafeProperties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(new InputStreamReader(inputStream, "utf-8"));
        inputStream.close();

        startDate = properties.getProperty("start.date");
        endDate = properties.getProperty("end.date");
        twspSrcPath = properties.getProperty("copy.twsp.srcPath");

    }

    @Test
    public void inspectAll() throws Exception {
        loadProperties();
        inspectDate();
        inspectPath();
    }

    @Test
    public void inspectPath() throws Exception {
        String regex = "^.*(?<!\\\\)$";
        String messageTail = "不要以'\\'结尾。";
        if (!Pattern.matches(regex,twspSrcPath)){
            throw new Exception("\"twsp文件复制输入路径\"不可用，" + messageTail);
        }
    }

    private void inspectDate() throws Exception {
        inspectDateFormat();
        inspectDateRelation();
    }

    private void inspectDateFormat() throws Exception{
        /**
         * @Author: wsy
         * @MethodName: inspectDateFormat
         * @Return: void
         * @Param: []
         * @Description:  Inspect date format (including leap year).
         * @Date: 2017/12/17 17:35
         */

        String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

        if (!Pattern.matches(regex, startDate)) {
            throw new Exception("\"开始日期\"格式错误。");
        }

        if (!Pattern.matches(regex, endDate)) {
            throw new Exception("\"结束日期\"格式错误。");
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
