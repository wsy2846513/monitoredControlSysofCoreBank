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
public class CallCmdLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* team.sjfw.monitoringSystem.controller.CallCMD.executeCmd(..))")
    public void cmdLog(ProceedingJoinPoint pjp) {
        try {
            logger.info("call method [{}], parameters = {}", pjp.getSignature().getName(),pjp.getArgs());
            ArrayList<String> data = (ArrayList<String>) pjp.proceed();
            if (data != null){
                for (Iterator<String> it = data.iterator(); it.hasNext(); ) {
                    logger.info("cmdLogs:\t{}", it.next());
                }
            }else {
                logger.info("the method [{}] return null",pjp.getSignature().getName());
            }
        } catch (Throwable t) {
            logger.error("Catch exception in method:{},parameters:{}",pjp.getSignature(),pjp.getArgs(),t);
        }
    }
}
