package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

@Controller
public class ManualController implements Runnable {
    private Semaphore startManualImport;
    @Autowired
    private ImportKit importKit;

    @Autowired
    public ManualController(GlobalProperties globalProperties) {
        startManualImport = globalProperties.getStartManualImport();
    }

    @Override
    public void run() {
        while (true) {
            try {
//                ManualController t = getThis();
                System.out.println("ManualController waiting ...");
                startManualImport.acquire();
                Thread importKitThread = new Thread(importKit);
                importKitThread.start();
                importKitThread.join();
                System.out.println("ManualController finished");
//                getThis().waitForImport();
//                getThis().startImport();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private ManualController getThis() {
        return Main.applicationContext.getBean(this.getClass());
    }

    private void waitForImport() throws Exception {
        System.out.println("ManualController waiting ...");
        startManualImport.acquire();
    }

    private void startImport() throws Exception {
        Thread importKitThread = new Thread(importKit);
        importKitThread.start();
        importKitThread.join();
        System.out.println("ManualController finished");
    }
}
