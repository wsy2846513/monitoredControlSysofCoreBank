package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;


@Aspect
@Component
public class SettingFormLog {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* team.sjfw.monitoringSystem.view.SettingForm.initialize(..))")
    public void initializeLog(ProceedingJoinPoint pjp) {
        try {
            logger.info("initializeLog:\t{}", pjp.getArgs());
            ArrayList<String> data = (ArrayList<String>) pjp.proceed();
            for (Iterator<String> it = data.iterator(); it.hasNext(); ) {
                logger.info("cmdArrLogs:\t{}", it.next());
            }
        } catch (Throwable t) {
            logger.error("Catch exception in method:{},parameters:{}", pjp.getSignature(), pjp.getArgs(), t);

        }
    }
}