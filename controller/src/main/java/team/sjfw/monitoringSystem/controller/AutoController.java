package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.wsy.tools.SafeProperties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Controller
public class AutoController implements Runnable {

    private ScheduledExecutorService scheduledExecutorService;
    private String propertiesFilePath;
    private Semaphore refreshProperties;
    private static final long ONEDAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    public String autoTime;

    @Autowired
    private ImportKit importKit;

    @Autowired
    public AutoController(GlobalProperties globalProperties) {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        this.refreshProperties = globalProperties.getRefreshProperties();
    }

    public void startScheduledThread() {
        try {
            getThis().setAutoTime();
            long initDelay = getMillisecond(new Date(), autoTime) - System.currentTimeMillis();
            if (initDelay <= 0) {
                initDelay += ONEDAY_MILLISECONDS;
            }
            scheduledExecutorService.scheduleAtFixedRate(importKit, initDelay, ONEDAY_MILLISECONDS, TimeUnit.MILLISECONDS);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String setAutoTime() {
        try {
            SafeProperties properties = new SafeProperties();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(inputStream);
            inputStream.close();
            String s = properties.getProperty("autoImport.time");
            autoTime = s;
            return s;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            try {
//                AutoController t = getThis();
//                getThis获取的不是本对象?
                getThis().startScheduledThread();
                refreshProperties.acquire();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private long getMillisecond(Date date, String time) {
        /**
         * @Author: wsy
         * @MethodName: getMillisecond
         * @Return: long
         * @Param: [date, time]
         * @Description: Convert the time to millisecond
         * @Date: 2017/11/26 16:05
         */

        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        try {
            Date result = timeFormat.parse(dateFormat.format(date) + " " + time);
            return result.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    private AutoController getThis(){
        return Main.applicationContext.getBean(this.getClass());
    }

//    @Autowired
//    private AutoController getT(AutoController a){
//
//        return a;
//    }
}
