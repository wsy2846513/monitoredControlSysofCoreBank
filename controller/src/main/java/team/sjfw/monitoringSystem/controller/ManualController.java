package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.concurrent.Semaphore;

/**
 * @Tittle: ManualController.java
 * @Author: wsy
 * @Class_name: ManualController
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Wait for manual import command and execute it.
 * @Version: V1.0
 * @Date: 2017/12/25 22:19
 */

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
