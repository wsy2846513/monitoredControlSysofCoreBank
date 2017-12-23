package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AutoImportLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before("execution(* team.sjfw.monitoringSystem.controller.AutoImport.run(..))")
    public void beforeRun() {
        logger.info("Auto import start ...");
    }
    @After("execution(* team.sjfw.monitoringSystem.controller.AutoImport.run(..))")
    public void afterRun() {
        logger.info("Auto import is finished.");
    }
}
