package team.sjfw.monitoringSystem.log.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//据说开启exposeProxy = true,proxyTargetClass = true可以启动内部拦截，但是没成功
//@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass = true)
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "team.sjfw.monitoringSystem.log")
public class SettingFormLogConfig {
}
