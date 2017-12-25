package team.sjfw.monitoringSystem.view.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.controller.GlobalProperties;

/**
 * @Tittle: SettingFormConfig.java
 * @Author: wsy
 * @Class_name: SettingFormConfig
 * @Package: team.sjfw.monitoringSystem.view.config
 * @Description: config class
 * @Version: V1.0
 * @Date: 17-11-20 下午7:50
 */
@Configuration
//@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.view","team.sjfw.monitoringSystem.controller"})
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.view")
@Import(GlobalProperties.class)
public class SettingFormConfig {
}
