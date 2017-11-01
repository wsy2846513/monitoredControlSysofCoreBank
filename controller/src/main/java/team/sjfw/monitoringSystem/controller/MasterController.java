package team.sjfw.monitoringSystem.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.sjfw.monitoringSystem.controller.config.MasterControllerConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MasterControllerConfig.class)
@Controller
public class MasterController {
    @Autowired
    private Duplicator duplicator;

    @Test
    public void start(){
        System.out.println(this.getClass().getSimpleName() + "\tstart()");
//        duplicator.copyFiles();
    }

//    启动失败
    public static void main(String[] args){
        MasterController masterController = new MasterController();
        masterController.start();
    }
}
