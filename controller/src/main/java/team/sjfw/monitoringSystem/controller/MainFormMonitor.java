package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import team.sjfw.monitoringSystem.view.MainForm;
import java.util.concurrent.Semaphore;

/**
 * @Tittle: MainFormMonitor.java
 * @Author: wsy
 * @Class_name: MainFormMonitor
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Control the mainForm.
 * @Version: V1.0
 * @Date: 2017/12/25 22:18
 */

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
        /**
         * @Author: wsy
         * @MethodName: run
         * @Return: void
         * @Param: []
         * @Description: Show the main form.
         * @Date: 17-12-19 下午7:07
         */
        try{
//            mainForm.initializeAll();
            openMainForm.acquire();
            while (true){
                closeMainForm.acquire();
                mainForm.setFrameVisible(false);
                openMainForm.acquire();
                mainForm.setFrameVisible(true);
            }
        }catch (Exception exception){
            globalProperties.setErrorMessage(exception.toString(), exception);
            globalProperties.setErrorOccured(true);
        }
    }
}
