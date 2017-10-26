package team.sjfw.monitoringSystem.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicatorLog {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void test(){
        logger.info("Class {} -- test()",getClass());
    }

    public static void main(String args[]){
        DuplicatorLog dup = new DuplicatorLog();
        dup.test();
    }

}
