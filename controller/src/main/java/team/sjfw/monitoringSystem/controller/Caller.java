package team.sjfw.monitoringSystem.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.interconversion.CalendarAndString;

import java.util.ArrayList;
import java.util.Calendar;

@Controller
public class Caller {

    private String twspSrcPath;
    private String twspAnalysisProgram;
    private String twspSqlPath;
    private String briefingSrcPath;
    private String briefingAnalysisPath;
    private String briefingSqlPath;
    private String mysqlHost;
    private String mysqlUser;
    private String mysqlPassword;
    private String mysqlPort;
    private String mysqlDatabase;

    private String fullCMDCommand;

//    private Integer targetYear;
    private Calendar startDate;
    private Calendar endDate;
//    private Calendar processDate;

    private ArrayList<String> cmdCommandArr;

    @Autowired
    CallCMD callCMD;

    @Autowired
    public Caller(Environment environment){
        this.twspSrcPath = environment.getProperty("copy.twsp.destPath");
        this.twspAnalysisProgram = environment.getProperty("program.twsp.analysisPath");
        this.twspSqlPath = environment.getProperty("program.twsp.sqlPath");
        this.briefingSrcPath = environment.getProperty("copy.briefing.destPath");
        this.briefingAnalysisPath = environment.getProperty("program.briefing.analysisPath");
        this.briefingSqlPath = environment.getProperty("program.briefing.sqlPath");
        this.startDate = CalendarAndString.StringToCalendar(environment.getProperty("start.date"));
        this.endDate = CalendarAndString.StringToCalendar(environment.getProperty("end.date"));
        this.mysqlHost = environment.getProperty("mysql.host");
        this.mysqlUser = environment.getProperty("mysql.user");
        this.mysqlPassword = environment.getProperty("mysql.password");
        this.mysqlPort = environment.getProperty("mysql.port");
        this.mysqlDatabase = environment.getProperty("mysql.database");
    }

    public void analyseTwsp(){
        cmdCommandArr = new ArrayList<String>();
        Calendar processDate = startDate;
        int lastYear = endDate.get(Calendar.YEAR);
        int targetYear = processDate.get(Calendar.YEAR);
        long startTime = System.nanoTime();
        while (targetYear <= lastYear){
//            解析twsp
            fullCMDCommand = "PYTHON " + twspAnalysisProgram + " -s \"" + twspSrcPath
                    + "\" -d \"" + twspSqlPath + "\" -y " + targetYear;
            cmdCommandArr.add(fullCMDCommand);
            processDate.add(Calendar.YEAR, 1);
            targetYear = processDate.get(Calendar.YEAR);
        }
        long endTime = System.nanoTime();
        System.out.println("String\t'+'\tcost:" + (endTime - startTime));
//        callCMD.executeCmdArr(cmdCommandArr);
    }

    public void analyseBriefing(){
        cmdCommandArr = new ArrayList<String>();
        //            解析简报
        fullCMDCommand = "PYTHON " + briefingAnalysisPath + " -s \"" + briefingSrcPath
                + "\" -d \"" + briefingSqlPath + "\"";
        cmdCommandArr.add(fullCMDCommand);
        callCMD.executeCmdArr(cmdCommandArr);
    }

    public void importSql(){
        cmdCommandArr = new ArrayList<String>();
        Calendar processDate = startDate;
        String sqlFilePath = "123";
        while (!processDate.after(endDate)){
            fullCMDCommand = "mysql.exe --force --comments --default-character-set=utf8"
                    + " --host=" + mysqlHost + " --port" + mysqlPort + "--database=" + mysqlDatabase
                    + " --user=" + mysqlUser+ "--password=" + mysqlPassword + sqlFilePath;
            processDate.add(Calendar.DATE,1);
        }
    }
}
