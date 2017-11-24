package team.sjfw.monitoringSystem.controller;

import org.springframework.stereotype.Controller;
/**
 * @Tittle: GlobalProperties.java
 * @Author: wsy
 * @Class_name: GlobalProperties
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Store global properties
 * @Version: V1.0
 * @Date: 17-11-20 下午7:25
 */
@Controller
public class GlobalProperties {
//    linux
    private String propertiesFilePath = "./src/main/resources/environment.properties";
//    windows
//    private String propertiesFilePath = "./src/main/resources/environment.properties";


    public String getPropertiesFilePath() {
        return propertiesFilePath;
    }

    public void setPropertiesFilePath(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath;
    }
}
