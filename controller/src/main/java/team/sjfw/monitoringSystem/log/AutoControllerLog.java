package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AutoControllerLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @AfterReturning(returning = "autoTime",pointcut = "execution(* team.sjfw.monitoringSystem.controller.AutoController.setAutoTime(..))")
    public void beforeStartLog(Object autoTime){
        logger.info("AFTER----Auto import time is set to {}",autoTime);
    }
}
