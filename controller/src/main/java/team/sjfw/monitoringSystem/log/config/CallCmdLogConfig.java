package team.sjfw.monitoringSystem.log.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.log")
public class CallCmdLogConfig {
}
