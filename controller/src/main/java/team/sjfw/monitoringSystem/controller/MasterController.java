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
import team.sjfw.monitoringSystem.view.SettingForm;

import javax.swing.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = MasterControllerConfig.class)
@Controller
public class MasterController {
    @Autowired
    private Duplicator duplicator;

    @Autowired
    private Caller caller;


    //    @Test
    public void startImport() {
        System.out.println(this.getClass().getSimpleName() + "\tstartImport()");

        duplicator.startCopy();
//        caller.analyseTwsp();
//        caller.analyseBriefing();
//        caller.importSql();
    }

}
