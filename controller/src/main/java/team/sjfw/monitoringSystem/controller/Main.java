package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import team.sjfw.monitoringSystem.view.MainForm;
import team.sjfw.monitoringSystem.view.ProgressForm;

@Controller
public class Main {
//    Global variable
    public static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MainConfig.class);

    @Autowired
    private MainFormMonitor mainFormMonitor;

    @Autowired
    private AutoController autoController;

    @Autowired
    private ManualController manualController;

    @Autowired
    private ProgressMonitor progressMonitor;

    private Thread mainFormThread;
    private Thread autoThread;
    private Thread manualThread;
    private Thread progressThread;


    public void start(){
//        autoController.setAutoTime();
        mainFormThread = new Thread(mainFormMonitor);
        mainFormThread.start();
        autoThread = new Thread(autoController);
        autoThread.start();
        manualThread = new Thread(manualController);
        manualThread.start();
        progressThread = new Thread(progressMonitor);
        progressThread.start();
    }

    public static void main(String[] args) {
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MainConfig.class);
//        System.out.println(Main.class);
        Main main = applicationContext.getBean(Main.class);
        main.start();
    }
}
