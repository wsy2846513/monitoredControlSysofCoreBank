package team.sjfw.monitoringSystem.main.config;

        import org.springframework.context.annotation.Configuration;
        import org.springframework.context.annotation.Import;
        import team.sjfw.monitoringSystem.controller.config.MasterControllerConfig;

@Configuration
@Import({MasterControllerConfig.class})
public class Mainconfig {
}
