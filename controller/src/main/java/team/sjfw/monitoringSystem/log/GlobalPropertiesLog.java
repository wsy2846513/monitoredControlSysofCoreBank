package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalPropertiesLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @After("execution(* team.sjfw.monitoringSystem.controller.GlobalProperties.setErrorMessage(String,Exception)) && args(message,exception)")
    public void setErrorMessageLog(String message, Exception exception) {
        logger.error("Error : {}", message, exception);
    }
}
