package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.config.CallCmdLogConfig;

@Configuration
@Import({DuplicatorConfig.class,CallerConfig.class,CallCmdLogConfig.class})
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
public class MasterControllerConfig {
}
