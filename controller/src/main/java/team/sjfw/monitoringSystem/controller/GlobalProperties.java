package team.sjfw.monitoringSystem.controller;

import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

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
//    private String propertiesFilePath = "./src/main/resources/environment.properties";
//    windows
    private String propertiesFilePath = "./src/main/resources/environment.properties";
    private static boolean importKitStarted = false;
    private static Semaphore allowImport = new Semaphore(1);
    private static Semaphore refreshProperties = new Semaphore(0);
    private static Semaphore openProgressForm = new Semaphore(0);
    private static Semaphore startManualImport = new Semaphore(0);
//    private static Semaphore closeProgressForm = new Semaphore(0);


    public String getPropertiesFilePath() {
        return propertiesFilePath;
    }

    public static synchronized boolean isImportKitStarted() {
        return importKitStarted;
    }

    public static synchronized void setImportKitStarted(boolean importKitStarted) {
        GlobalProperties.importKitStarted = importKitStarted;
    }

    public static Semaphore getAllowImport() {
        return allowImport;
    }

    public static Semaphore getRefreshProperties() {
        return refreshProperties;
    }

    public static Semaphore getOpenProgressForm() {
        return openProgressForm;
    }

    public static Semaphore getStartManualImport() {
        return startManualImport;
    }
}
