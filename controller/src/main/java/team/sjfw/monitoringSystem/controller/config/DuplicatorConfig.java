package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import team.sjfw.monitoringSystem.controller.Deleter;

@Configuration
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
@PropertySource(value = "environment.properties",encoding = "UTF-8")
@Import({CallCMDConfig.class, DeleterConfig.class})
public class DuplicatorConfig {
}
