package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

@Controller
public class AutoController implements Runnable{

    private ScheduledExecutorService scheduledExecutorService;
    private String propertiesFilePath;
    private Semaphore refreshProperties;
    private static final long ONEDAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    private String auto_time;

    @Autowired
    public AutoController(GlobalProperties globalProperties) {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        this.refreshProperties = globalProperties.getRefreshProperties();
    }

    @Override
    public void run() {
        while (true){
            try {
                SafeProperties properties = new SafeProperties();
                InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
                properties.load(inputStream);
                inputStream.close();

                auto_time = properties.getProperty("autoImport.time");
                long initDelay = getMillisecond(new Date(),auto_time) - System.currentTimeMillis();
                if (initDelay <= 0 ){
                    initDelay += ONEDAY_MILLISECONDS;
                }

                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MasterControllerConfig.class);
                scheduledExecutorService.scheduleAtFixedRate(applicationContext.getBean(ImportKit.class),
                        initDelay,ONEDAY_MILLISECONDS, TimeUnit.MILLISECONDS);

                refreshProperties.acquire();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private long getMillisecond(Date date,String time){
        /**
         * @Author: wsy
         * @MethodName: getMillisecond
         * @Return: long
         * @Param: [date, time]
         * @Description:  Convert the time to millisecond
         * @Date: 2017/11/26 16:05
         */

        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        try {
            Date result = timeFormat.parse(dateFormat.format(date) + " " + time);
            return result.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
