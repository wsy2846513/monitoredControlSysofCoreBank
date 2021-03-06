package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.controller.GlobalProperties;
import team.sjfw.monitoringSystem.view.config.ProgressFormConfig;

@Configuration
@Import({GlobalProperties.class, ProgressFormConfig.class})
@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.controller"})
public class ProgressMonitorConfig {
}
