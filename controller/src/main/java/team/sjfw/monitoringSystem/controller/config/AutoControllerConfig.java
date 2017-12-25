package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.controller.GlobalProperties;
import team.sjfw.monitoringSystem.log.config.AutoControllerLogConfig;

@Configuration
@Import({GlobalProperties.class, ImportKitConfig.class,
        AutoControllerLogConfig.class,AutoImportConfig.class})
@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.controller"})
//@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)

public class AutoControllerConfig {
}
