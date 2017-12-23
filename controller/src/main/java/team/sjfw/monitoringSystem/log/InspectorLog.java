package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class InspectorLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* team.sjfw.monitoringSystem.controller.Inspector.inspectAll(..))")
    public void startInspectAll(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("begin to inspect parameters ...");
        pjp.proceed();
        logger.info("Inspect parameters finished.");
    }

}
