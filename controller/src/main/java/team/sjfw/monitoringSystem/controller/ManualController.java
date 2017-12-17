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
    private GlobalProperties globalProperties;

    public ManualController() {
        startManualImport = globalProperties.getStartManualImport();
    }

    @Override
    public void run() {
        while (true) {
            try {
                startManualImport.acquire();
                Thread importKitThread = new Thread(importKit);
                importKitThread.start();
                importKitThread.join();
            } catch (Exception exception) {
                globalProperties.setErrorOccured(true);
                globalProperties.setErrorMessage(exception.toString(),exception);
            }
        }
    }
}
