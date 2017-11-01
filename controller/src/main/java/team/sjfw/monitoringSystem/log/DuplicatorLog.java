package team.sjfw.monitoringSystem.log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Aspect
@Component
public class DuplicatorLog {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static int n = 1;

//    @Before("execution(* team.sjfw.monitoringSystem.controller.Duplicator.executeCmd(..))")
//    @Before("execution(* team.sjfw.monitoringSystem.controller.Duplicator.*(..))")
//    @Before("execution(* team.sjfw.monitoringSystem.controller.Duplicator.copyFiles(..))")
    @Before("execution(* team.sjfw.monitoringSystem.controller.Duplicator.executeCmd(..))")
    public void beforeCmdLog(){
//        System.out.println("before cmd sysout");
        logger.info("before CMD logger" + n++);
    }

//    @Around("execution(* team.sjfw.monitoringSystem.controller.Duplicator.executeCmd(Process,String)) && args(process,parameter")
//    public void cmdLog(ProceedingJoinPoint pjp,Process process, String parameter){
//        try{
//            System.out.println("cccc");
//            logger.info("Para = {}",parameter);
//            pjp.proceed();
//        }catch (Throwable t){
//            t.printStackTrace();
//        }
//    }

    public void test(){
        beforeCmdLog();
        logger.info("Class {} -- test()",getClass());
    }

    public static void main(String args[]){
        DuplicatorLog dup = new DuplicatorLog();
        dup.test();
    }

}
