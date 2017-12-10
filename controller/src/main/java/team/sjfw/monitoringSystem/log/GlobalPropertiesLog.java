package team.sjfw.monitoringSystem.log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class GlobalPropertiesLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //    @After ("execution(* team.sjfw.monitoringSystem.controller.Main.start(..))")
//    @Around("execution(* team.sjfw.monitoringSystem.controller.GlobalProperties.getPropertiesFilePath(..))")
//    @Around("execution(* team.sjfw.monitoringSystem.controller.GlobalProperties.set*(..))")
//    public void setTargetCountLog(ProceedingJoinPoint pjp) {
//        try {
//            logger.info("{} to {}", pjp.getSignature().getName(),pjp.getArgs());
//            pjp.proceed();
//        } catch (Throwable t) {
//            logger.error("Catch exception in method:{},parameters:{}", pjp.getSignature(), pjp.getArgs(), t);
//        }
//    }

    @After("execution(* team.sjfw.monitoringSystem.controller.GlobalProperties.setErrorMessage(String)) && args(message)")
    public void setTargetCountLog(String message) {
        logger.error("Error : {}", message);
    }
}
