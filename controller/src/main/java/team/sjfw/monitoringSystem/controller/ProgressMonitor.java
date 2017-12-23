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
        /**
         * @Author: wsy
         * @MethodName: run
         * @Return: void
         * @Param: []
         * @Description: Inspect whether the import kit has started.
         *              When the import kit start, show the progress bar.
         *              When there is any error, show the error message and close progress bar.
         * @Date: 17-12-18 下午8:13
         */
        try{
            initializeAll();
            while (true){
//                Judge whether import kit has started.
                openProgressForm.acquire();

//                Start progressForm to show the progress bar
                Thread formThread = new Thread(progressForm);
                formThread.start();

//                Wait for progressForm finish initializing.
                while (!progressForm.isInitializeFinished()){
//                    If do not sleep here, this while-loop can not stop sometimes, but I do not know why...
                    Thread.sleep(500);
                }

//                Judge whether there is any error during import kit running.
                while (formThread.isAlive()) {
                    Thread.sleep(500);
//                    Error occurs during MasterController runtime
                    if (globalProperties.isErrorOccured()) {
//                        Close progress bar and show error message.
                        progressForm.setExit(true);
                        JOptionPane.showMessageDialog(null, globalProperties.getErrorMessage());
                        break;
                    }
                }

//                Set the current count to zero for next import.
                globalProperties.setCurrentCount(0);

//                set error occured flag to false for next import.
                globalProperties.setErrorOccured(false);

//                Show the mainForm.
                openMainForm.release();
            }
        }catch (Exception exception){
            globalProperties.setErrorOccured(true);
            globalProperties.setErrorMessage(exception.toString(), exception);
        }

    }
}
