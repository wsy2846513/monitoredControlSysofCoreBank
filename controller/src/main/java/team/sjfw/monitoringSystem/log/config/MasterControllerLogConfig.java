package team.sjfw.monitoringSystem.log.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.controller.GlobalProperties;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
//@EnableAspectJAutoProxy
@Import(GlobalProperties.class)
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.log")
public class MasterControllerLogConfig {
}
