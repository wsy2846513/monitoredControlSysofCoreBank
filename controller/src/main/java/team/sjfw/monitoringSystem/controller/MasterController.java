package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.CalendarTools;
import pers.wsy.tools.SafeProperties;

import java.io.*;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Semaphore;


@Controller
public class MasterController {

//    private SafeProperties properties;
    private String propertiesFilePath;

    @Autowired
    private Duplicator duplicator;

    @Autowired
    private Caller caller;

    @Autowired
    private GlobalProperties globalProperties;

    private void initializeAll() throws Exception {

        duplicator.initializeAll();
        caller.initializeAll();

        propertiesFilePath = globalProperties.getPropertiesFilePath();
        SafeProperties properties = new SafeProperties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(new InputStreamReader(inputStream, "utf-8"));
        inputStream.close();

        Calendar startDate;
        Calendar endDate;
        startDate = CalendarTools.StringToCalendar(properties.getProperty("start.date"), "yyyy-MM-dd");
        endDate = CalendarTools.StringToCalendar(properties.getProperty("end.date"), "yyyy-MM-dd");
        Long targetDays = (endDate.getTimeInMillis() - startDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);

//        When there is n days data to be imported:
//          delete files counts 1 point, copy files counts 2n points, analyse twsp files counts 1 point,
//          analyse briefing files counts 1 point, import sql files to database counts n+1 points,
//          use ReportImportAssistant counts 3n points, and analyse critical path counts 3n points.
//          So the total points are 9n + 4.
        globalProperties.setNumofDaystoProcessed(targetDays.intValue() + 1);
        globalProperties.setTargetCount(globalProperties.getNumofDaystoProcessed() * 9 + 4);
        globalProperties.setCurrentCount(0);

//        Set current latest date, in case of latest date in environment.properties is null
        globalProperties.setLatestDate(properties.getProperty("latest.date"));
    }

    public void startImport() throws Exception{
        try {
            globalProperties.setErrorOccured(false);
            this.initializeAll();
            duplicator.deleteFiles();
            duplicator.copyFiles();
            caller.startAnalyseTwsp();
            caller.startAnalyseBriefing();
            caller.startImportSql();
            caller.startAnalyseCriticalPath();
            caller.startReportImportAssistant();
        } catch (Exception exception) {
            globalProperties.setErrorMessage(exception.toString(), exception);
            globalProperties.setErrorOccured(true);
        } finally {
            updateLatestDate(globalProperties.getLatestDate());
        }
    }

    private void updateLatestDate(String date) throws Exception{
        SafeProperties properties = new SafeProperties();
//            If properties don't load again here, it will discard annotation when it stores,
//            But I don't know why...
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(inputStream);
        inputStream.close();

        properties.setProperty("latest.date", date);

        FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
        properties.store(fileOutputStream, null);
        fileOutputStream.close();
    }
}
