package team.sjfw.monitoringSystem.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.sjfw.monitoringSystem.controller.Duplicator;
import team.sjfw.monitoringSystem.controller.config.DuplicatorConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DuplicatorConfig.class)
public class Main {
    @Autowired
    private Duplicator duplicator;

    @Test
    public void test() {
        System.out.println("9999");
//        System.out.println(duplicator);

    }
}
