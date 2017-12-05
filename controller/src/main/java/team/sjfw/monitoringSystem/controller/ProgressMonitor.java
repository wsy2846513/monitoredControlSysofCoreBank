package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import team.sjfw.monitoringSystem.view.ProgressForm;

import javax.swing.*;
import java.util.concurrent.Semaphore;

@Controller
public class ProgressMonitor implements Runnable {

    private Semaphore openProgressForm;
    private Semaphore openMainForm;
    @Autowired
    private ProgressForm progressForm;

    @Autowired
    private GlobalProperties globalProperties;

    public void initializeAll(){
        openProgressForm = globalProperties.getOpenProgressForm();
        openMainForm = globalProperties.getOpenMainForm();
    }

    @Override
    public void run() {
        try{
            initializeAll();
            while (true){
                openProgressForm.acquire();
                Thread formThread = new Thread(progressForm);
                formThread.start();
                while (formThread.isAlive()) {
//                    Error occurs during MasterController runtime
                    if (globalProperties.isErrorOccured()) {
                        progressForm.setExit(true);
                        JOptionPane.showMessageDialog(null, globalProperties.getErrorMessage());
                        break;
                    }
                }
                globalProperties.setCurrentCount(0);
                openMainForm.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
