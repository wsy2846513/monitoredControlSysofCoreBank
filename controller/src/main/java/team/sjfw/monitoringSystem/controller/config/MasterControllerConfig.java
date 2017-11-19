package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.config.CallCmdLogConfig;
import team.sjfw.monitoringSystem.log.config.SettingFormLogConfig;

@Configuration
@Import({DuplicatorConfig.class,CallerConfig.class,CallCmdLogConfig.class, SettingFormLogConfig.class})
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
public class MasterControllerConfig {
}
