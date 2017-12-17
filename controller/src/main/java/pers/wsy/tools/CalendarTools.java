package pers.wsy.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Tittle: CalendarTools.java
 * @Author: wsy
 * @Class_name: CalendarTools
 * @Package: pers.wsy.tools.tools
 * @Description: This class contain methods for calendar to execute copy,
 *              convert and other operation.
 * @Version: V1.0
 * @Date: 17-11-14 上午11:01
 */
public class CalendarTools {

    public static String calendarToString(Calendar calendar, String format) {
        /**
         * @Author: wsy
         * @MethodName: calendarToString
         * @Return: java.lang.String
         * @Param: [calendar, format]
         * @Description: convert [calendar] to string with [format] format.
         *              Format could be like "yyyy-MM-dd".
         * @Date: 17-11-14 上午11:10
         */
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    public static Calendar StringToCalendar(String dateString, String format) {
        /**
         * @Author: wsy
         * @MethodName: StringToCalendar
         * @Return: java.util.Calendar
         * @Param: [dateString, format]
         * @Description: convert [dateString] to calendar with [format] format.
         *              Format could be like "yyyy-MM-dd".
         * @Date: 17-11-14 上午11:15
         */
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            System.out.println("There are one or more exceptions in CalendarTools : StringToCalendar");
            e.printStackTrace();
            return null;
        }
    }

    public static Calendar copyCalendarFault(Calendar src) {
        /**
         * @Author: wsy
         * @MethodName: copyCalendar
         * @Return: java.util.Calendar
         * @Param: [src]
         * @Description: return a new calendar with the value of [src]
         *              由于Calendar类中的set方法在执行完之后，不会立刻更新Calendar，
         *              所以会导致新建的Calendar中的日期与预期不符。add方法可以立刻刷新，
         *              因此可以使用add方法将Calendar先加1再加-1，从而刷新Calendar。
         * @Date: 17-11-14 上午11:52
         */
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(src.get(Calendar.YEAR),src.get(Calendar.MONTH),src.get(Calendar.DAY_OF_MONTH),
                src.get(Calendar.HOUR_OF_DAY),src.get(Calendar.MINUTE),src.get(Calendar.SECOND));
        newCalendar.set(Calendar.MILLISECOND,src.get(Calendar.MILLISECOND));
        return newCalendar;
    }
}
