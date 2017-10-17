package team.sjfw.monitoringSystem.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wsy.tools.interconversion.CalendarAndString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@RunWith(SpringJUnit4ClassRunner.class)
@Component
public class Duplicator {
    String srcPath;
    String destPath;

    Calendar startDate;
    Calendar endDate;
    Calendar latestDate;

    @Autowired
    public Duplicator(Environment environment) {
        this.srcPath = environment.getProperty("twsp.input");
        this.destPath = environment.getProperty("twsp.output");
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

    public void test0(){
        String temp = "2015-12-13";
        String temp2 = "2015-12-17";
        Date d1;
        Date d2;
        int i = 0;
        try {
            d1 = (new SimpleDateFormat("yyyy-MM-dd")).parse("2015-12-13");
            d2 = (new SimpleDateFormat("yyyy-MM-dd")).parse("2015-12-17");
            while (d1.getTime() < d2.getTime()){
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("test : i = " + i);
    }

    @Override
    public String toString() {
        return "Duplicator{" +
                "srcPath='" + srcPath + '\'' +
                ", destPath='" + destPath + '\'' +
                ", startDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(startDate.getTime()) +
                ", endDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(endDate.getTime()) +
                ", latestDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(latestDate.getTime()) +
                '}';
    }
}
