package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.config.*;
import team.sjfw.monitoringSystem.view.config.MainFormConfig;
import team.sjfw.monitoringSystem.view.config.SettingFormConfig;

@Configuration
@Import({DuplicatorConfig.class, CallerConfig.class,
        MainFormConfig.class, SettingFormConfig.class,
        AutoControllerConfig.class, ProgressMonitorConfig.class,
        MainFormConfig.class, AutoControllerLogConfig.class,
        ManualControllerConfig.class, MainLogConfig.class})
@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.controller"})
public class MainConfig {
}
