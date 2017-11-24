package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;



@Aspect
@Component
public class SettingFormLog {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Before("execution(* team.sjfw.monitoringSystem.view.SettingForm.initialize(..))")
//    public void initialize(){
//        logger.info("before\tSettingForm\tinitializeLog:\t");
//    }
    @Around("execution(* team.sjfw.monitoringSystem.view.SettingForm.initialize(..))")
    public void initialize(ProceedingJoinPoint pjp) {
        try {
            logger.info("start initializing ...");
            pjp.proceed();
            logger.info("initialize complete!");
        } catch (Throwable t) {
            logger.error("Catch exception in method:{},parameters:{}", pjp.getSignature(), pjp.getArgs(), t);

        }
    }

    @Around("execution(* team.sjfw.monitoringSystem.view.SettingForm.saveSettings(..))")
    public void saveSettings(ProceedingJoinPoint pjp) {
        try {
            logger.info("start saving settings ...");
            pjp.proceed();
            logger.info("save complete!");
        } catch (Throwable t) {
            logger.error("Catch exception in method:{},parameters:{}", pjp.getSignature(), pjp.getArgs(), t);

        }
    }
}