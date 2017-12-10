package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MasterControllerLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before("execution(* team.sjfw.monitoringSystem.controller.MasterController.startImport(..))")
    public void beforeStartLog(){
        logger.info("Start import");
    }
}
