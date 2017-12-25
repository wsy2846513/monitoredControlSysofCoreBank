package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CallerLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* team.sjfw.monitoringSystem.controller.Caller.startAnalyseTwsp(..))")
    public void startAnalyseTwsp(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("begin to analyse twsp files ...");
        pjp.proceed();
        logger.info("analyse twsp files successfully.");
    }

    @Around("execution(* team.sjfw.monitoringSystem.controller.Caller.startAnalyseBriefing(..))")
    public void startAnalyseBriefing(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("begin to analyse briefing files ...");
        pjp.proceed();
        logger.info("analyse briefing files successfully.");
    }

    @Around("execution(* team.sjfw.monitoringSystem.controller.Caller.startImportSql(..))")
    public void startImportSql(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("begin to import *.sql files to database ...");
        pjp.proceed();
        logger.info("import *.sql files to database successfully.");
    }

    @Around("execution(* team.sjfw.monitoringSystem.controller.Caller.startAnalyseCriticalPath(..))")
    public void startAnalyseCriticalPath(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("begin to analyse critical path ...");
        pjp.proceed();
        logger.info("analyse critical path successfully.");
    }

    @Around("execution(* team.sjfw.monitoringSystem.controller.Caller.startReportImportAssistant(..))")
    public void startReportImportAssistant(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("begin to execute ReportImportAssistant ...");
        pjp.proceed();
        logger.info("execute ReportImportAssistant successfully.");
    }
}
