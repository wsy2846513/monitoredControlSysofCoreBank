package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ImportKitLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* team.sjfw.monitoringSystem.controller.ImportKit.run(..))")
    public void beforeStartLog(ProceedingJoinPoint pjp){
        try {
            logger.info("Start a new thread of ImportKit and try to acquire semaphore named allowImport.");
            pjp.proceed();
            logger.info("Thread of ImportKit is finished and release semaphore named allowImport.");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
