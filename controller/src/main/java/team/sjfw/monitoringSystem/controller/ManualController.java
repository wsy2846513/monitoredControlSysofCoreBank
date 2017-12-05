package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

@Controller
public class ManualController implements Runnable{
    private Semaphore startManualImport;
    @Autowired
    private ImportKit importKit;

    @Autowired
    public ManualController(GlobalProperties globalProperties) {
        startManualImport = globalProperties.getStartManualImport();
    }

    public String test() {
        return "awegasd";
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("ManualController waiting ...");
                startManualImport.acquire();
//                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MasterControllerConfig.class);
//                Thread importKitThread = new Thread(applicationContext.getBean(ImportKit.class));
                Thread importKitThread = new Thread(importKit);
                importKitThread.start();
                importKitThread.join();
                System.out.println("ManualController finished");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
