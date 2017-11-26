package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import team.sjfw.monitoringSystem.view.MainForm;

@Controller
public class Main {
    @Autowired
    private MainForm mainForm;
//    @Autowired
//    private ImportKit importKit;
    @Autowired
    private AutoController autoController;

    @Autowired
    private ManualController manualController;

    private Thread autoThread;
    private Thread manualThread;

    public void start(){
        mainForm.initializeAll();
        autoThread = new Thread(autoController);
        autoThread.start();
        manualThread = new Thread(manualController);
        manualThread.start();
//        System.out.println("main.startImport");
//        try {
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("main.finish");
//        for (int i = 0; i < 2; ++i){
//            new Thread(importKit).startImport();
//        }
    }

    public static void main(String[] args) {
        System.out.println("main startImport");
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MasterControllerConfig.class);
//        MasterController masterController = applicationContext.getBean(MasterController.class);
//        masterController.startImport();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MainConfig.class);
        Main main = applicationContext.getBean(Main.class);
        main.start();
    }
}
