package team.sjfw.monitoringSystem.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.sjfw.monitoringSystem.controller.config.MasterControllerConfig;
import team.sjfw.monitoringSystem.view.MainForm;

import javax.swing.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MasterControllerConfig.class)
@Controller
public class MasterController {
    @Autowired
    private Duplicator duplicator;

    @Autowired
    private Caller caller;

    private MainForm mainForm;
    private JFrame frame;
    public void initializeFrame(){
        mainForm = new MainForm();
        mainForm.initialize();
//        frame = new JFrame("MainForm");
//        frame.setContentPane(new MainForm().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }
    @Test
    public void start(){
        System.out.println(this.getClass().getSimpleName() + "\tstart()");
        this.initializeFrame();
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("logger start! 中文测试");

//        duplicator.startCopy();
//        caller.analyseTwsp();
//        caller.analyseBriefing();
//        caller.importSql();
    }

//    启动失败
    public static void main(String[] args){
        System.out.println("main start");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(team.sjfw.monitoringSystem.controller.config.MasterControllerConfig.class);
        MasterController masterController = applicationContext.getBean(MasterController.class);
        masterController.start();
    }
}
