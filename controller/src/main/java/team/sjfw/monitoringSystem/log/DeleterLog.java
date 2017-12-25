package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DeleterLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* team.sjfw.monitoringSystem.controller.Deleter.deleteFilesInFolder(..))")
    public void deleteFilesLog(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("begin to delete files in folder {} ...",pjp.getArgs());
        pjp.proceed();
        logger.info("delete files successfully.");
    }
}
