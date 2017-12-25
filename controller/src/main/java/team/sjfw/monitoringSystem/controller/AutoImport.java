package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.CalendarTools;
import pers.wsy.tools.DateTools;
import pers.wsy.tools.SafeProperties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

@Controller
public class AutoImport implements Runnable {
    private String propertiesFilePath;

    private GlobalProperties globalProperties;

    private ImportKit importKit;

    @Autowired
    public AutoImport(GlobalProperties inputGlobalProperties,ImportKit inputImportKit) {
        this.globalProperties = inputGlobalProperties;
        this.importKit = inputImportKit;
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
    }

    private void setDate() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: setDate
         * @Return: void
         * @Param: []
         * @Description:  Set import date [start, end] to [latestDate+1, now].
         * @Date: 2017/12/17 16:04
         */

//        Load properties
        SafeProperties properties = new SafeProperties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(inputStream);
        inputStream.close();

//        Get start date for import
        Calendar startCalendar = CalendarTools.StringToCalendar(properties.getProperty("latest.date"), "yyyy-MM-dd");
        startCalendar.add(Calendar.DATE, 1);
        String startDate = CalendarTools.calendarToString(startCalendar, "yyyy-MM-dd");

//        Get end date for import
        String endDate = DateTools.dateToString(new Date(), "yyyy-MM-dd");

//        Set properties in file
        properties.setProperty("start.date", startDate);
        properties.setProperty("end.date", endDate);

//        Save properties
        FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
        properties.store(fileOutputStream, null);
        fileOutputStream.close();
    }

    @Override
    public void run() {
        try {
            setDate();
            Thread importKitThread = new Thread(importKit);
            importKitThread.start();
            importKitThread.join();
        } catch (Exception exception) {
            globalProperties.setErrorOccured(true);
            globalProperties.setErrorMessage(exception.toString(), exception);
        }
    }
}
