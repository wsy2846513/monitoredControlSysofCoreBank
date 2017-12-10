package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.CalendarTools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Semaphore;


@Controller
public class MasterController {

    @Autowired
    private Duplicator duplicator;

    @Autowired
    private Caller caller;

    @Autowired
    private GlobalProperties globalProperties;

    private void initializeAll() throws Exception {

        String propertiesFilePath = globalProperties.getPropertiesFilePath();
        Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(new InputStreamReader(inputStream, "utf-8"));
        inputStream.close();

        Calendar startDate;
        Calendar endDate;
        startDate = CalendarTools.StringToCalendar(properties.getProperty("start.date"), "yyyy-MM-dd");
        endDate = CalendarTools.StringToCalendar(properties.getProperty("end.date"), "yyyy-MM-dd");
        Long targetDays = (endDate.getTimeInMillis() - startDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
//        When there is n days data to be imported:
//          delete files counts 1 point, copy files counts 2 n points, analyse twsp files counts 1 point,
//          analyse briefing files counts 1 point, import sql files to database counts n +1 points,
//          use ReportImportAssistant counts 3 n points, and analyse critical path counts 3 n points.
//          So the total points are 9 n + 4.
        globalProperties.setNumofDaystoProcessed(targetDays.intValue() + 1);
        globalProperties.setTargetCount(globalProperties.getNumofDaystoProcessed() * 9 + 4);
        globalProperties.setCurrentCount(0);
    }

    public void startImport() {
        try {
            globalProperties.setErrorOccured(false);
            System.out.println(this.getClass().getSimpleName() + "\tstartImport()");
            this.initializeAll();
            duplicator.initializeAll();
            caller.initializeAll();
            duplicator.deleteFiles();
            duplicator.copyFiles();
            caller.startAnalyseTwsp();
            caller.startAnalyseBriefing();
            caller.startImportSql();
            caller.startAnalyseCriticalPath();
            caller.startReportImportAssistant();
            System.out.println(this.getClass().getSimpleName() + "\tfinishImport()");
        } catch (Exception exception) {
            globalProperties.setErrorMessage(exception.toString());
            globalProperties.setErrorOccured(true);
        }
    }
}
