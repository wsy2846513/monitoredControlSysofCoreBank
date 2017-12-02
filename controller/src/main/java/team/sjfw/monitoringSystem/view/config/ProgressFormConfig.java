package team.sjfw.monitoringSystem.view.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.controller.GlobalProperties;

@Configuration
@Import({GlobalProperties.class})
@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.view"})
public class ProgressFormConfig {
}
