package team.sjfw.monitoringSystem.log.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.log")
public class ExecuteSqlFileLogConfig {

}
