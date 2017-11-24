package team.sjfw.monitoringSystem.view.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Tittle: MainFormConfig.java
 * @Author: wsy
 * @Class_name: MainFormConfig
 * @Package: team.sjfw.monitoringSystem.view.config
 * @Description: config class
 * @Version: V1.0
 * @Date: 17-11-20 下午8:20
 */
@Configuration
@Import({SettingFormConfig.class})
@ComponentScan(basePackages = {"team.sjfw.monitoringSystem.view"})
public class MainFormConfig {
}
