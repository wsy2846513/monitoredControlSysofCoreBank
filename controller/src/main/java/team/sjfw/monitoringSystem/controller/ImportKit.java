package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

@Controller
public class ImportKit implements Runnable {

    @Autowired
    private MasterController masterController;

    private Semaphore allowImport;
    private Semaphore openProgressForm;
    private Semaphore closeMainForm;

    @Autowired
    private GlobalProperties globalProperties;

//    public ImportKit(GlobalProperties globalProperties) {
//        this.allowImport = globalProperties.getAllowImport();
//        this.openProgressForm = globalProperties.getOpenProgressForm();
//        this.closeMainForm = globalProperties.getCloseMainForm();
//    }

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
