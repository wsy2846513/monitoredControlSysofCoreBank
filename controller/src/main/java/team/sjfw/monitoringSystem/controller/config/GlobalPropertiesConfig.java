package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.config.GlobalPropertiesLogConfig;

/**
 * @Tittle: GlobalPropertiesConfig.java
 * @Author: wsy
 * @Class_name: GlobalPropertiesConfig
 * @Package: team.sjfw.monitoringSystem.controller.config
 * @Description: config class
 * @Version: V1.0
 * @Date: 17-11-20 下午7:26
 */
@Configuration
@Import(GlobalPropertiesLogConfig.class)
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
public class GlobalPropertiesConfig {
}
