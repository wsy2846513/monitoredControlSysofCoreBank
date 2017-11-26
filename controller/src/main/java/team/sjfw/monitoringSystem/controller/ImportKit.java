package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

@Controller
public class ImportKit implements Runnable {
//    Do not use autowired here, because the masterController need to get the newest properties all the time.
//    When autowired here, the masterController will get bean and properties when the ImportKit object established.
//    Then if any values changed in property files, the masterController would still use the old properties rather than
//    the newest prpperties.

    //    @Autowired
    private MasterController masterController;

//    private String propertiesFilePath;

    private Semaphore allowImport;
    private Semaphore openProgressForm;

//    @Autowired
//    private GlobalProperties globalProperties;

    @Autowired
    public ImportKit(GlobalProperties globalProperties) {
//        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        this.allowImport = globalProperties.getAllowImport();
        this.openProgressForm = globalProperties.getOpenProgressForm();
    }

//    public void initialize() {
//        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
//        this.allowImport = globalProperties.getAllowImport();
////        System.out.println("Thread id = " + Thread.currentThread().getId() + "\tavailable nums = " + allowImport.availablePermits());
//    }

    //    @Override
    public void run() {
        try {
//            this.initialize();
//                Load the properties
//                SafeProperties properties = new SafeProperties();
//                InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
//                properties.load(inputStream);
//                inputStream.close();
//                System.out.println("autoImport.time = " + properties.getProperty("autoImport.time"));

//                System.out.println("autoImport.time = " + properties.getProperty("autoImport.time"));
            System.out.println("Try to get allowImport!");
                allowImport.acquire();
                openProgressForm.release();
                System.out.println("Thread id = " + Thread.currentThread().getId() + "\tget into!!!!!!!!!!!!!!!!!!");
//                Load the properties
//                SafeProperties properties = new SafeProperties();
//                InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
//                properties.load(inputStream);
//                inputStream.close();
//                System.out.println("autoImport.time = " + properties.getProperty("autoImport.time"));
//                get the newest object
                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MasterControllerConfig.class);
                masterController = applicationContext.getBean(MasterController.class);
                masterController.startImport();
//                Thread.sleep(3000);
//                globalProperties.setImportKitStarted(false);
//                System.out.println("Thread id = " + Thread.currentThread().getId() + "\tget out????????????????");
            Thread.sleep(20000);
                allowImport.release();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
