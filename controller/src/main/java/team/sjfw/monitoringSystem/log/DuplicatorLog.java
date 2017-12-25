package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DuplicatorLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* team.sjfw.monitoringSystem.controller.Duplicator.copyFiles(..))")
    public void deleteFilesLog(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("begin to copy files ...");
        pjp.proceed();
        logger.info("copy files successfully.");
    }
}
