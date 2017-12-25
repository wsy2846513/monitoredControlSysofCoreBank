package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.concurrent.Semaphore;

/**
 * @Tittle: ImportKit.java
 * @Author: wsy
 * @Class_name: ImportKit
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Judge if it is ready for import whether auto or manual.
 * @Version: V1.0
 * @Date: 2017/12/25 22:17
 */

@Controller
public class ImportKit implements Runnable {

    @Autowired
    private MasterController masterController;

    private Semaphore allowImport;
    private Semaphore openProgressForm;
    private Semaphore closeMainForm;

    @Autowired
    private GlobalProperties globalProperties;

    private void initializeAll(){
        this.allowImport = globalProperties.getAllowImport();
        this.openProgressForm = globalProperties.getOpenProgressForm();
        this.closeMainForm = globalProperties.getCloseMainForm();
    }

    @Override
    public void run() {
        try {
            initializeAll();
            allowImport.acquire();
            closeMainForm.release();
            openProgressForm.release();
            masterController.startImport();
            allowImport.release();
        } catch (Exception exception) {
            globalProperties.setErrorOccured(true);
            globalProperties.setErrorMessage(exception.toString(),exception);
        }
    }
}
