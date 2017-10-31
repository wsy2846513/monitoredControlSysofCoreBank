package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.config.DuplicatorLogConfig;

@Configuration
@Import({DuplicatorConfig.class, DuplicatorLogConfig.class})
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
public class MasterControllerConfig {
}
