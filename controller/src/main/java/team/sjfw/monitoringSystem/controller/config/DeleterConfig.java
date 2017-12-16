package team.sjfw.monitoringSystem.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.log.config.DeleterLogConfig;

/**
 * @Tittle: DeleterConfig.java
 * @Author: wsy
 * @Class_name: DeleterConfig
 * @Package: team.sjfw.monitoringSystem.controller.config
 * @Description:
 * @Version: V1.0
 * @Date: 2017/11/15 20:18
 */
@Configuration
@Import(DeleterLogConfig.class)
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.controller")
public class DeleterConfig {
}
