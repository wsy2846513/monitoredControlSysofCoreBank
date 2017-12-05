package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import team.sjfw.monitoringSystem.view.MainForm;

import java.util.concurrent.Semaphore;

@Controller
public class MainFormMonitor implements Runnable{

    @Autowired
    private GlobalProperties globalProperties;

    @Autowired
    private MainForm mainForm;

    private Semaphore openMainForm;
    private Semaphore closeMainForm;

    public MainFormMonitor() {
        this.openMainForm = globalProperties.getOpenMainForm();
        this.closeMainForm = globalProperties.getCloseMainForm();
    }

    @Override
    public void run() {
        try{
            openMainForm.acquire();
            mainForm.initializeAll();
            while (true){
                closeMainForm.acquire();
                mainForm.setFrameVisible(false);
                openMainForm.acquire();
                mainForm.refresh();
                mainForm.setFrameVisible(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
