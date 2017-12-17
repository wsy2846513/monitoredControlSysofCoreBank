package pers.wsy.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Tittle: DateTools.java
 * @Author: wsy
 * @Class_name: DateTools
 * @Package: pers.wsy.tools
 * @Description: This class contain methods for date to execute
 *              convert and other operation.
 * @Version: V1.0
 * @Date: 2017/12/17 15:57
 */

public class DateTools {

    public static String dateToString(Date date,String format){
        /**
         * @Author: wsy
         * @MethodName: dateToString
         * @Return: java.lang.String
         * @Param: [date, format]
         * @Description:  convert [date] to string with [format] format.
         *              Format could be like "yyyy-MM-dd".
         * @Date: 2017/12/17 15:58
         */

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

}
