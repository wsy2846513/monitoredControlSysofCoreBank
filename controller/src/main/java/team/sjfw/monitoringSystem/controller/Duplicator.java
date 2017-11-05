package team.sjfw.monitoringSystem.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wsy.tools.interconversion.CalendarAndString;
import team.sjfw.monitoringSystem.controller.config.CallCMDConfig;
import team.sjfw.monitoringSystem.controller.config.DuplicatorConfig;
import team.sjfw.monitoringSystem.controller.config.MasterControllerConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Controller
public class Duplicator {
    private String twspSrcPath;
    private String twspDestPath;
    private String twspFileName;
    private String briefingSrcPath;
    private String briefingDestPath;
    private String briefingFileName;

    private String cmdHead = "cmd.exe /k xcopy";
    private String cmdEnd = "/f /y";
    private String fullTwspSrc;
    private String fullBriefingSrc;
    private String handleCalendarString;
    private String fullCmdCommand;
    private ArrayList<String> cmdCommandArr;

    private Calendar startDate;
    private Calendar endDate;
    private Calendar processDate;
    private Calendar latestDate;

    @Autowired
    private CallCMD callCMD;

    @Autowired
    public Duplicator(Environment environment) {
        this.twspSrcPath = environment.getProperty("copy.twsp.srcPath");
        this.twspDestPath = environment.getProperty("copy.twsp.destPath");
        this.twspFileName = environment.getProperty("copy.twsp.fileName");
        this.briefingSrcPath = environment.getProperty("copy.briefing.srcPath");
        this.briefingDestPath = environment.getProperty("copy.briefing.destPath");
        this.briefingFileName = environment.getProperty("copy.briefing.fileName");
        this.startDate = CalendarAndString.StringToCalendar(environment.getProperty("start.date"));
        this.endDate = CalendarAndString.StringToCalendar(environment.getProperty("end.date"));
        this.processDate = startDate;
        this.latestDate = CalendarAndString.StringToCalendar(environment.getProperty("latest.date"));
    }

    public void copyFiles() {

        cmdCommandArr = new ArrayList<String>();

        while (!processDate.after(endDate)) {
            handleCalendarString = CalendarAndString.calendarToString(processDate);

//            复制TWSP
            fullTwspSrc = twspSrcPath + handleCalendarString + "\\";
            fullCmdCommand = cmdHead + " " + fullTwspSrc + twspFileName + " " + twspDestPath + " " + cmdEnd;
            cmdCommandArr.add(fullCmdCommand);
//            callCMD.executeCmd(fullCmdCommand);

//            复制简报
            fullBriefingSrc = briefingSrcPath + handleCalendarString + "\\";
            fullCmdCommand = cmdHead + " " + fullBriefingSrc + briefingFileName + " " + briefingDestPath + " " + cmdEnd;
            cmdCommandArr.add(fullCmdCommand);
//            callCMD.executeCmd(fullCmdCommand);

            processDate.add(Calendar.DATE, 1);
        }
        callCMD.executeCmdArr(cmdCommandArr);
    }

    public void testCMD(){

        cmdCommandArr = new ArrayList<String>();
//        cmdCommandArr.add("cp /home/wsy/A/AAA /home/wsy/B/");
        cmdCommandArr.add("ls");
        cmdCommandArr.add("ls");
        cmdCommandArr.add("ls");
//        cmdCommandArr.add("ipconfig");
        callCMD.executeCmdArr(cmdCommandArr);
    }

    @Override
    public String toString() {
        return "Duplicator{" +
                "twspSrcPath='" + twspSrcPath + '\'' +
                ", twspDestPath='" + twspDestPath + '\'' +
                ", startDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(startDate.getTime()) +
                ", endDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(endDate.getTime()) +
                ", latestDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(latestDate.getTime()) +
                '}';
    }
}
