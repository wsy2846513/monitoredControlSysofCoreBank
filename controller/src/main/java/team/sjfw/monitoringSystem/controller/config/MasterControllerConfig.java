package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.config.CallCmdLogConfig;
import team.sjfw.monitoringSystem.log.config.SettingFormLogConfig;
import team.sjfw.monitoringSystem.view.config.MainFormConfig;
import team.sjfw.monitoringSystem.view.config.SettingFormConfig;

@Configuration
//@Import({DuplicatorConfig.class, CallerConfig.class,
//        CallCmdLogConfig.class, SettingFormLogConfig.class,
//        MainFormConfig.class,SettingFormConfig.class})
@Import({DuplicatorConfig.class, CallerConfig.class})
@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.controller"})
public class MasterControllerConfig {
}
