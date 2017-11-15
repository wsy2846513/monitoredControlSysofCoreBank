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
public class DuplicatorLog {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static int n = 1;

    @Around("execution(* team.sjfw.monitoringSystem.controller.CallCMD.executeCmd(..))")
    public void cmdLog(ProceedingJoinPoint pjp) {
        try {
            logger.info("call method [{}],parameters = {}", pjp.getSignature().getName(),pjp.getArgs());
            ArrayList<String> data = (ArrayList<String>) pjp.proceed();
            for (Iterator<String> it = data.iterator(); it.hasNext(); ) {
                logger.info("cmdLogs:\t{}", it.next());
            }
        } catch (Throwable t) {
            logger.error("Catch exception in method:{},parameters:{}",pjp.getSignature(),pjp.getArgs(),t);
//            t.printStackTrace();
        }
    }

    @Around("execution(* team.sjfw.monitoringSystem.controller.CallCMD.executeCmdArr(..))")
    public void cmdArrLog(ProceedingJoinPoint pjp){
        try{
            logger.info("cmdArrLogs:\t{}",pjp.getArgs());
            ArrayList<String> data = (ArrayList<String>) pjp.proceed();
            for(Iterator<String> it = data.iterator(); it.hasNext();){
                logger.info("cmdArrLogs:\t{}",it.next());
            }
        }catch (Throwable t){
            logger.error("Catch exception in method:{},parameters:{}",pjp.getSignature(),pjp.getArgs(),t);

        }
    }

    public void test() {
//        beforeCmdLog();
        logger.info("Class {} -- test()", getClass());
    }

    public static void main(String args[]) {
        DuplicatorLog dup = new DuplicatorLog();
        dup.test();
    }

}
