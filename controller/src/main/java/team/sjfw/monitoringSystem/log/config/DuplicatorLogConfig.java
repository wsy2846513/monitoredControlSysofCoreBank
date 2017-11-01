package team.sjfw.monitoringSystem.log.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import team.sjfw.monitoringSystem.log.DuplicatorLog;

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass = true)
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.log")
public class DuplicatorLogConfig {
//    @Bean
//    public DuplicatorLog duplicatorLog(){
//        return new DuplicatorLog();
//    }
}
