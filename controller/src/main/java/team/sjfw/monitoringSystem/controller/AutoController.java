package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

/**
 * @Tittle: AutoController.java
 * @Author: wsy
 * @Class_name: AutoController
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Start or stop the schedule according to autoSwitch in properties,
 *              and set the newest time for schedule.
 * @Version: V1.0
 * @Date: 2017/12/25 22:10
 */

@Controller
public class AutoController implements Runnable {

    private static final long ONEDAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    private ScheduledExecutorService scheduledExecutorService;
    private String propertiesFilePath;
    private Semaphore refreshProperties;
    private String autoTime;
    private String autoSwitch;

    private AutoImport autoImport;

    private GlobalProperties globalProperties;

    @Autowired
    public AutoController(GlobalProperties inputGlobalProperties,AutoImport inputAutoImport) {
        globalProperties = inputGlobalProperties;
        autoImport = inputAutoImport;
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        this.refreshProperties = globalProperties.getRefreshProperties();
    }

    public void startScheduledThread() throws Exception {
//        Use getThis method to realize AOP log system.
        getThis().setAutoTime();
        if (autoSwitch.equals("on")) {
            long initDelay = getMillisecond(new Date(), autoTime) - System.currentTimeMillis();
            if (initDelay <= 0) {
                initDelay += ONEDAY_MILLISECONDS;
            }
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(autoImport, initDelay, ONEDAY_MILLISECONDS, TimeUnit.MILLISECONDS);
        } else {
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdown();
            }
        }
    }

    public String setAutoTime() throws Exception {
        SafeProperties properties = new SafeProperties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(inputStream);
        inputStream.close();

        autoTime = properties.getProperty("autoImport.time");
        autoSwitch = properties.getProperty("autoImport.swtich");

//        This message is used to print log.
        String message = "Auto import is " + autoSwitch + ", time is set to " + autoTime;
        return message;
    }

    @Override
    public void run() {
        while (true) {
            try {
//                AutoController t = getThis();
//                getThis获取的不是本对象?
//                getThis().startScheduledThread();
                startScheduledThread();
                refreshProperties.acquire();
            } catch (Exception exception) {
                globalProperties.setErrorOccured(true);
                globalProperties.setErrorMessage(exception.toString(), exception);
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

    private AutoController getThis() {
        return Main.applicationContext.getBean(this.getClass());
    }

}
