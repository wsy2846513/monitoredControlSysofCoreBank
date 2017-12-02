package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

@Controller
public class ManualController implements Runnable{
    private String propertiesFilePath;
    private Semaphore startManualImport;
//    private Semaphore allowImport;
//    private Semaphore openProgressForm;
//
////    Do not use autowired here, because the masterController need to get the newest properties all the time.
////    When autowired here, the ImportKit object will be established and the masterController will get bean and properties.
////    Then if any values changed in property files, the masterController would still use the old properties rather than
////    the newest prpperties.
//    //    @Autowired
//    private MasterController masterController;

    @Autowired
    public ManualController(GlobalProperties globalProperties) {
//        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        startManualImport = globalProperties.getStartManualImport();
//        allowImport = globalProperties.getAllowImport();
//        openProgressForm = globalProperties.getOpenProgressForm();
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("ManualController waiting ...");
                startManualImport.acquire();
                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MasterControllerConfig.class);
                Thread importKitThread = new Thread(applicationContext.getBean(ImportKit.class));
                importKitThread.start();
                importKitThread.join();
                System.out.println("ManualController finished");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
