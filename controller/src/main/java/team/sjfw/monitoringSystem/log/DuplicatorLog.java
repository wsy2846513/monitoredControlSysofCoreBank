package team.sjfw.monitoringSystem.log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;

@Aspect
@Component
public class DuplicatorLog {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static int n = 1;

//    @Before("execution(* team.sjfw.monitoringSystem.controller.Duplicator.copyFiles(..))")
//    public void testlog() {
//        logger.info("testlog\t" + n++);
//    }

//    @Around("execution(* team.sjfw.monitoringSystem.controller.CallCMD.executeCmd(String)) && args(parameter)")
//    public void cmdLog(ProceedingJoinPoint pjp,String parameter){
//        try{
//            logger.info("Para = {}",parameter);
//            BufferedReader reader = (BufferedReader) pjp.proceed();
//            String lineData = "";
//            while (reader != null && (lineData = reader.readLine()) != null){
//                System.out.println("111111111111111");
//                logger.info("log-reader:\t{}",lineData);
//            }
//        }catch (Throwable t){
//            t.printStackTrace();
//        }
//    }

    @Around("execution(* team.sjfw.monitoringSystem.controller.CallCMD.executeCmd(..))")
    public void cmdLog(ProceedingJoinPoint pjp){
        try{
            ArrayList<String> data = (ArrayList<String>) pjp.proceed();
            for(Iterator<String> it = data.iterator(); it.hasNext();){
                logger.info("cmdLogs:\t{}",it);
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
    }


//    @Around("execution(* team.sjfw.monitoringSystem.controller.CallCMD.executeCmd(String)) && args(parameter")
//    @Around("execution(* team.sjfw.monitoringSystem.controller.CallCMD.executeCmd(..))")
//    @AfterReturning(value = "execution(* team.sjfw.monitoringSystemystem.controller.CallCMD.executeCmd(..)) && args(parameter)",returning = "returnReader")
//        public void cmdLog(String parameter){
//        try{
//            System.out.println("parameter = " + parameter);
//        }catch (Throwable t){
//            t.printStackTrace();
//        }
//    }

    public void test() {
//        beforeCmdLog();
        logger.info("Class {} -- test()", getClass());
    }

    public static void main(String args[]) {
        DuplicatorLog dup = new DuplicatorLog();
        dup.test();
    }

}
