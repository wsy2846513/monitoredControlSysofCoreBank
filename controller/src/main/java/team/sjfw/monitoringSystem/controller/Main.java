package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * @Tittle: Main.java
 * @Author: wsy
 * @Class_name: Main
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Main is the program entry.
 * @Version: V1.0
 * @Date: 2017/12/25 22:18
 */

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
        Main main = applicationContext.getBean(Main.class);
        main.start();

    }
}
