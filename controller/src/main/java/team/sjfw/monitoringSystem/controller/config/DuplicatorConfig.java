package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "team.sjfw")
//@PropertySource("classpath:/team/sjfw/monitoringSystem/controller/config/environment.properties")
//@PropertySource(value = "classpath:environment.properties",ignoreResourceNotFound = true)
//@PropertySource("/config/environment.properties")
@PropertySource("environment.properties")
public class DuplicatorConfig {
}
