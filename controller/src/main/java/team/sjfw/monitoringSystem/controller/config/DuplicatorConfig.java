package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.config.DuplicatorLogConfig;

@Configuration
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
//@PropertySource(value = "environment.properties",encoding = "UTF-8")
@Import({GlobalPropertiesConfig.class,CallCMDConfig.class,
        DeleterConfig.class, DuplicatorLogConfig.class})
public class DuplicatorConfig {
}
