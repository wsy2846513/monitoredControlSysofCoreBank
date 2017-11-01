package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
//@PropertySource("classpath:/team/sjfw/monitoringSystem/controller/config/environment.properties")
//@PropertySource(value = "classpath:environment.properties",ignoreResourceNotFound = true)
//@PropertySource("/config/environment.properties")
@PropertySource(value = "environment.properties",encoding = "UTF-8")
public class CallerConfig {
}
