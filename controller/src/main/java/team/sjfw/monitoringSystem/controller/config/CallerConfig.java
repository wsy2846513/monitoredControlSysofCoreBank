package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
@PropertySource(value = "environment.properties",encoding = "UTF-8")
public class CallerConfig {
}