package team.sjfw.monitoringSystem.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wsy.tools.interconversion.CalendarAndString;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@Component
public class Duplicator {
    private String twspSrcPath;
    private String twspDestPath;
    private String briefingSrcPath;
    private String briefingDestPath;

    private Calendar startDate;
    private Calendar endDate;
    private Calendar latestDate;

    @Autowired
    public Duplicator(Environment environment) {
        this.twspSrcPath = environment.getProperty("twsp.input");
        this.twspDestPath = environment.getProperty("twsp.output");
        this.startDate = CalendarAndString.StringToCalendar(environment.getProperty("start.date"));
        this.endDate = CalendarAndString.StringToCalendar(environment.getProperty("end.date"));
        this.latestDate = CalendarAndString.StringToCalendar(environment.getProperty("latest.date"));
        test1();
    }

    public void test1(){
        int i = 0;
        while (!startDate.after(endDate)){
            i++;
            System.out.println("startDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(startDate.getTime()));
//            System.out.println("test1 : i = " + i);
            startDate.add(Calendar.DATE,1);
        }
        System.out.println("test1 : i = " + i);
    }

    private boolean copyFilesSuccessfully(){
        String cmdHead = "cmd cp ";
        String fullTwspSrc;
        String fullBriefingSrc;
        String handleCalendarString;
        while (!startDate.after(endDate)){
            handleCalendarString = CalendarAndString.calendarToString(startDate);
            fullTwspSrc = twspSrcPath + handleCalendarString;
            fullBriefingSrc = briefingSrcPath + handleCalendarString;
        }
        return true;
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
