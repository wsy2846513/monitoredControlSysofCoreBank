package team.sjfw.monitoringSystem.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wsy.tools.CalendarTools;
import team.sjfw.monitoringSystem.controller.config.MasterControllerConfig;
import team.sjfw.monitoringSystem.view.MainForm;
import team.sjfw.monitoringSystem.view.SettingForm;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Properties;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = MasterControllerConfig.class)
@Controller
public class MasterController {
    @Autowired
    private Duplicator duplicator;

    @Autowired
    private Caller caller;

    @Autowired
    GlobalProperties globalProperties;
//    @Autowired
//    private void setTargetCount(GlobalProperties globalProperties){
    private void setTargetCount(){
        try{
            String propertiesFilePath;
            propertiesFilePath = globalProperties.getPropertiesFilePath();
            Properties properties = new Properties();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(new InputStreamReader(inputStream, "utf-8"));
            inputStream.close();

            Calendar startDate;
            Calendar endDate;
            startDate = CalendarTools.StringToCalendar(properties.getProperty("start.date"), "yyyy-MM-dd");
            endDate = CalendarTools.StringToCalendar(properties.getProperty("end.date"), "yyyy-MM-dd");
            Long targetDays = (endDate.getTimeInMillis() - startDate.getTimeInMillis()) / ( 24 * 60 * 60 * 1000);
//            When there is n days data to be imported:
//              delete files counts 1 point, copy files counts 2n points, analyse twsp files counts 1 point,
//              analyse briefing files counts 1 point, import sql files to database counts n+1 points,
//              use ReportImportAssistant counts 3n points, and analyse critical path counts 3n points.
//            So the total points are 9n+4.
            globalProperties.setNumofDaystoProcessed(targetDays.intValue() + 1);
            globalProperties.setTargetCount(globalProperties.getNumofDaystoProcessed() * 9 + 4);
            globalProperties.setCurrentCount(0);

        }catch (Exception exception){
//            尚未决定等待日志处理或本处处理
        }
    }

    //    @Test
    public void startImport() {
        System.out.println(this.getClass().getSimpleName() + "\tstartImport()");
        this.setTargetCount();
        duplicator.deleteFiles();
        duplicator.copyFiles();
        caller.startAnalyseTwsp();
        caller.startAnalyseBriefing();
        caller.startImportSql();
        caller.startAnalyseCriticalPath();
        caller.startReportImportAssistant();
        System.out.println(this.getClass().getSimpleName() + "\tfinishImport()");
    }

}
