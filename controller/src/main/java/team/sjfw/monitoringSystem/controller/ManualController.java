package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

@Controller
public class ManualController implements Runnable{
    private String propertiesFilePath;
    private Semaphore startManualImport ;
    @Autowired
    public ManualController(GlobalProperties globalProperties) {
//        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        startManualImport = globalProperties.getStartManualImport();
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("ManualController waiting ...");
                startManualImport.acquire();
                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MasterControllerConfig.class);
                new Thread(applicationContext.getBean(ImportKit.class)).start();
                System.out.println("ManualController finished");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
