package team.sjfw.monitoringSystem.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.sjfw.monitoringSystem.controller.config.DuplicatorConfig;

@Configuration
@Import(DuplicatorConfig.class)
public class Mainconfig {
}
