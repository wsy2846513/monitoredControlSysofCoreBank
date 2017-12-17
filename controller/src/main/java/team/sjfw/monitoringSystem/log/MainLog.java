package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MainLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @After("execution(* team.sjfw.monitoringSystem.controller.Main.start(..))")
    public void beforeStartLog(){
        logger.info("System start ...");
    }
}
