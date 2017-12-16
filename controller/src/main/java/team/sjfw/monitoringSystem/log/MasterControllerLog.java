package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wsy.tools.CalendarTools;
import team.sjfw.monitoringSystem.controller.GlobalProperties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Properties;

@Aspect
@Component
public class MasterControllerLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GlobalProperties globalProperties;


    @Before("execution(* team.sjfw.monitoringSystem.controller.MasterController.startImport(..))")
    public void beforeStartLog() {
        try {
            String propertiesFilePath = globalProperties.getPropertiesFilePath();
            Properties properties = new Properties();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(new InputStreamReader(inputStream, "utf-8"));
            inputStream.close();

            String startDate;
            String endDate;
            startDate = properties.getProperty("start.date");
            endDate = properties.getProperty("end.date");
            logger.info("Start import [{},{}]", startDate, endDate);
        } catch (Throwable e) {
            logger.error("Catch exception log system:", e);
        }

    }
}
