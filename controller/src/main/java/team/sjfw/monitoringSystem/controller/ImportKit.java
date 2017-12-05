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
    public ImportKit(GlobalProperties globalProperties) {
        this.allowImport = globalProperties.getAllowImport();
        this.openProgressForm = globalProperties.getOpenProgressForm();
        this.closeMainForm = globalProperties.getCloseMainForm();
    }

    @Override
    public void run() {
        try {
            System.out.println("Import kit thread (id = " + Thread.currentThread().getId() + ")\tget started!!!!!!!!!!!!!!!!!!");
            System.out.println("Try to get allowImport!");
            allowImport.acquire();
            closeMainForm.release();
            openProgressForm.release();
            System.out.println("MasterController is starting import.");
            masterController.startImport();
            allowImport.release();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
