package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.CalendarTools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.Semaphore;

/**
 * @Tittle: Duplicator.java
 * @Author: wsy
 * @Class_name: Duplicator
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: copy twsp files and briefing files to specified destination
 * @Version: V1.0
 * @Date: 17-11-14 下午7:13
 */
@Controller
public class Duplicator {
    private String twspSrcPath;
    private String twspDestPath;
    private String twspFileName;
    private String briefingSrcPath;
    private String briefingDestPath;
    private String briefingFileName;

    private String cmdHead = "xcopy";
    private String cmdEnd = "/f /y";
    private String handleCalendarString;

    private String propertiesFilePath;

    private Calendar startDate;
    private Calendar endDate;
    private Calendar latestDate;

    @Autowired
    private CallCMD callCMD;

    @Autowired
    private Deleter deleter;

    @Autowired
    private GlobalProperties globalProperties;

    public void initializeAll() throws Exception{
            this.propertiesFilePath = globalProperties.getPropertiesFilePath();
            Properties properties = new Properties();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(new InputStreamReader(inputStream, "utf-8"));
            inputStream.close();
            this.twspSrcPath = properties.getProperty("copy.twsp.srcPath");
            this.twspDestPath = properties.getProperty("copy.twsp.destPath");
            this.twspFileName = properties.getProperty("copy.twsp.fileName");
            this.briefingSrcPath = properties.getProperty("copy.briefing.srcPath");
            this.briefingDestPath = properties.getProperty("copy.briefing.destPath");
            this.briefingFileName = properties.getProperty("copy.briefing.fileName");
            this.startDate = CalendarTools.StringToCalendar(properties.getProperty("start.date"), "yyyy-MM-dd");
            this.endDate = CalendarTools.StringToCalendar(properties.getProperty("end.date"), "yyyy-MM-dd");
            this.latestDate = CalendarTools.StringToCalendar(properties.getProperty("latest.date"), "yyyy-MM-dd");
    }

    public void deleteFiles() throws Exception {
        deleter.deleteFilesInFolder(twspDestPath);
        deleter.deleteFilesInFolder(briefingDestPath);
        globalProperties.addCurrentCount(globalProperties.DUPLICATOR_DELETE_POINT);
    }


    public void copyFiles() throws Exception{
        /**
         * @Author: wsy
         * @MethodName: copyFiles
         * @Return: void
         * @Param: []
         * @Description: copy twsp files and briefing files to specified destination
         * @Date: 17-11-14 下午7:12
         */
        StringBuffer fullCmdCommand = new StringBuffer();
        ArrayList<String> cmdCommandArr = new ArrayList<String>();
        Calendar processCalendar = (Calendar) startDate.clone();

        while (!processCalendar.after(endDate)) {
            handleCalendarString = CalendarTools.calendarToString(processCalendar, "yyyy-MM-dd");
            String exitCode;

//            copy twsp files
            fullCmdCommand.setLength(0);
            fullCmdCommand.append(cmdHead);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(twspSrcPath);
            fullCmdCommand.append("\\");
            fullCmdCommand.append(handleCalendarString);
            fullCmdCommand.append("\\");
            fullCmdCommand.append(twspFileName);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(twspDestPath);
            fullCmdCommand.append("\\ ");
            fullCmdCommand.append(cmdEnd);

            exitCode = callCMD.executeCmd(fullCmdCommand.toString()).get(0);
            globalProperties.addCurrentCount(globalProperties.DUPLICATOR_COPY_POINT);

//            copy briefing files
            fullCmdCommand.setLength(0);
            fullCmdCommand.append(cmdHead);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(briefingSrcPath);
            fullCmdCommand.append("\\");
            fullCmdCommand.append(handleCalendarString);
            fullCmdCommand.append("\\");
            fullCmdCommand.append(briefingFileName);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(briefingDestPath);
            fullCmdCommand.append("\\ ");
            fullCmdCommand.append(cmdEnd);

            exitCode = callCMD.executeCmd(fullCmdCommand.toString()).get(0);
            globalProperties.addCurrentCount(globalProperties.DUPLICATOR_COPY_POINT);

//            When copy both twsp and briefing files successfully in one day, it is seemed to import the data successfully
            if (Integer.parseInt(exitCode) == 0){
                updateLatestDate(processCalendar);
            }

            processCalendar.add(Calendar.DATE, 1);
        }
    }

    private void updateLatestDate(Calendar date) {
        if (date.after(latestDate)) {
            globalProperties.setLatestDate(CalendarTools.calendarToString(date,"yyyy-MM-dd"));
        }
    }
}
