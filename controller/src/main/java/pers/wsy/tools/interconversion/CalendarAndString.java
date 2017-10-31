package pers.wsy.tools.interconversion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarAndString {

    public static String calendarToString(Calendar calendar){
//        需要用"yyyy-MM-dd"格式，大小写不同代表的含义不同
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    public static Calendar StringToCalendar(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = sdf.parse(string);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }catch (Exception e){
            System.out.println("There are one or more exceptions in CalendarAndString : StringToCalendar");
            e.printStackTrace();
            return null;
        }
    }
}
