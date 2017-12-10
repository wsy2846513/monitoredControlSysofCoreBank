package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.ManualControllerLog;
import team.sjfw.monitoringSystem.log.config.CallCmdLogConfig;
import team.sjfw.monitoringSystem.log.config.GlobalPropertiesLogConfig;
import team.sjfw.monitoringSystem.log.config.MasterControllerLogConfig;
import team.sjfw.monitoringSystem.log.config.SettingFormLogConfig;
import team.sjfw.monitoringSystem.view.config.MainFormConfig;
import team.sjfw.monitoringSystem.view.config.SettingFormConfig;

@Configuration
@Import({DuplicatorConfig.class, CallerConfig.class,
        GlobalPropertiesConfig.class, MasterControllerLogConfig.class})
@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.controller"})
public class MasterControllerConfig {
}
