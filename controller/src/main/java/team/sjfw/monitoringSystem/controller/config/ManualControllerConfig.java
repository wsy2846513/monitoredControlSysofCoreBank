package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.controller.GlobalProperties;
import team.sjfw.monitoringSystem.controller.ImportKit;
import team.sjfw.monitoringSystem.log.ManualControllerLog;
import team.sjfw.monitoringSystem.log.config.ImportKitLogConfig;
import team.sjfw.monitoringSystem.log.config.ManualControllerLogConfig;

@Configuration
@Import({ImportKitConfig.class, ManualControllerLogConfig.class})
@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.controller"})
public class ManualControllerConfig {
}
