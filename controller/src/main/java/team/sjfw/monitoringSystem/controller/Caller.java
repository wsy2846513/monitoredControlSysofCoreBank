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
        this.briefingAnalysisPath = environment.getProperty("program.briefing.analysisPath");
        this.briefingSqlPath = environment.getProperty("program.briefing.sqlPath");
        this.startDate = CalendarAndString.StringToCalendar(environment.getProperty("start.date"));
        this.endDate = CalendarAndString.StringToCalendar(environment.getProperty("end.date"));
    }

    public void analysis(){
        cmdCommandArr = new ArrayList<String>();
        Calendar processDate = startDate;
        int lastYear = endDate.get(Calendar.YEAR);
        int targetYear = processDate.get(Calendar.YEAR);

        while (targetYear <= lastYear){
//          解析简报
            fullCMDCommand = "PYTHON " + twspSrcPath + " -d \"" + twspSrcPath + "\" -y " + targetYear;
            cmdCommandArr.add(fullCMDCommand);
//            System.out.println(targetYear);
            processDate.add(Calendar.YEAR, 1);
            targetYear = processDate.get(Calendar.YEAR);
        }
        callCMD.executeCmdArr(cmdCommandArr);
    }

    public void importSql(){
        cmdCommandArr = new ArrayList<String>();
        Calendar processDate = startDate;
        while (!processDate.after(endDate)){
//            fullCMDCommand = "mysql.exe" + " --host=" + mysqlHost + " --force --user=" + mysqlUser +
//                    " --port" + mysqlPort
            processDate.add(Calendar.DATE,1);
        }
    }
}
