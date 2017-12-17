package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ManualControllerLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before("execution(* team.sjfw.monitoringSystem.controller.ManualController.run(..))")
    public void waitForImportLog() {
        logger.info("Manual controller start ...");
    }
}
