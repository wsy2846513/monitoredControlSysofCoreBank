package team.sjfw.monitoringSystem.log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Aspect
@Controller
public class DuplicatorLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* team.sjfw.monitoringSystem.controller.Duplicator.executeCmd(Process,String)) && args(process,parameter")
    public void cmdLog(ProceedingJoinPoint pjp,Process process, String parameter){
        try{
            System.out.println("cccc");
            logger.info("Para = {}",parameter);
            pjp.proceed();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public void test(){
        logger.info("Class {} -- test()",getClass());
    }

    public static void main(String args[]){
        DuplicatorLog dup = new DuplicatorLog();
        dup.test();
    }

}
