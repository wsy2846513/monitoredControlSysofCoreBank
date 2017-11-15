package team.sjfw.monitoringSystem.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.sjfw.monitoringSystem.controller.MasterController;
import team.sjfw.monitoringSystem.main.config.Mainconfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Mainconfig.class)
public class Main {

    @Autowired
    MasterController masterController;
    @Test
    public void test() {
        System.out.println("9999");
//        System.out.println(duplicator);

    }
    public static void main(String[] args){

    }
}
