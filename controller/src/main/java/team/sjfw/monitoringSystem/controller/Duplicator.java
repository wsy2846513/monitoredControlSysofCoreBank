package team.sjfw.monitoringSystem.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wsy.tools.interconversion.CalendarAndString;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//@RunWith(SpringJUnit4ClassRunner.class)
@Controller
public class Duplicator {
    private String twspSrcPath;
    private String twspDestPath;
    private String twspFileName;
    private String briefingSrcPath;
    private String briefingDestPath;
    private String briefingFileName;

    private String cmdHead = "cmd.exe /k xcopy";
    private String cmdEnd = "/F";
    private String fullTwspSrc;
    private String fullBriefingSrc;
    private String handleCalendarString;
    private String fullCmdCommand;
    private Process processCmd;

    private Calendar startDate;
    private Calendar endDate;
    private Calendar latestDate;

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
        this.latestDate = CalendarAndString.StringToCalendar(environment.getProperty("latest.date"));
        copyFiles();
    }

    public void copyFiles() {

        while (!startDate.after(endDate)) {
            handleCalendarString = CalendarAndString.calendarToString(startDate);
//            复制TWSP
            fullTwspSrc = twspSrcPath + handleCalendarString + "\\";
            fullCmdCommand = cmdHead + " " + fullTwspSrc + twspFileName + " " + twspDestPath + " " + cmdEnd;
            executeCmd(processCmd, fullCmdCommand);

//            复制简报
            fullBriefingSrc = briefingSrcPath + handleCalendarString + "\\";
            fullCmdCommand = cmdHead + " " + fullBriefingSrc + briefingFileName + " " + briefingDestPath + " " + cmdEnd;
            executeCmd(processCmd, fullCmdCommand);
            startDate.add(Calendar.DATE, 1);
        }
    }

//    public void executeCmd(Process process, String parameter) {
//        try {
//            System.out.println(this.getClass().getSimpleName() + "\texecuteCmd: " + parameter);
////            process = Runtime.getRuntime().exec(parameter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        return true;
//    }

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
