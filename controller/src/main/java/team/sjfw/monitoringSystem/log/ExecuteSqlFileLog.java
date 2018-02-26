package team.sjfw.monitoringSystem.log;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Aspect
@Controller
public class ExecuteSqlFileLog {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Before("execution(* team.sjfw.monitoringSystem.controller.ExecuteSqlFile.execute(String))"
			+ " && args(sqlFilePath)")
	public void executeLog(String sqlFilePath){
		logger.info("start to import sql file : " + sqlFilePath + " ...");
	}
}
