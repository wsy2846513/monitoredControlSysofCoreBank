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

    @Before("execution(* team.sjfw.monitoringSystem.controller.ManualController.waitForImport(..))")
    public void waitForImportLog() {
        logger.info("Wait for manual import ...");
    }

    @After("execution(* team.sjfw.monitoringSystem.controller.ManualController.startImport(..))")
    public void startImportLog() {
        logger.info("Manual import has finished.");
    }
}
