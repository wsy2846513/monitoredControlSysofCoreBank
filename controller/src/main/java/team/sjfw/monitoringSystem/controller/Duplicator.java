package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.CalendarTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * @Tittle: Duplicator.java
 * @Author: wsy
 * @Class_name: Duplicator
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: copy twsp files and briefing files to specified destination
 * @Version: V1.0
 * @Date: 17-11-14 下午7:13
 */
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
    private String handleCalendarString;

    private Calendar startDate;
    private Calendar endDate;
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
        this.startDate = CalendarTools.StringToCalendar(environment.getProperty("start.date"), "yyyy-MM-dd");
        this.endDate = CalendarTools.StringToCalendar(environment.getProperty("end.date"), "yyyy-MM-dd");
        this.latestDate = CalendarTools.StringToCalendar(environment.getProperty("latest.date"), "yyyy-MM-dd");
    }

    public void copyFiles() {
        /**
         * @Author: wsy
         * @MethodName: copyFiles
         * @Return: void
         * @Param: []
         * @Description: copy twsp files and briefing files to specified destination
         * @Date: 17-11-14 下午7:12
         */
        StringBuffer fullCmdCommand = new StringBuffer();
        ArrayList<String> cmdCommandArr = new ArrayList<String>();
        Calendar processCalendar = (Calendar) startDate.clone();;

        while (!processCalendar.after(endDate)) {
            handleCalendarString = CalendarTools.calendarToString(processCalendar, "yyyy-MM-dd");

//            copy twsp files
            fullCmdCommand.setLength(0);
            fullCmdCommand.append(cmdHead);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(twspSrcPath);
            fullCmdCommand.append("\\");
            fullCmdCommand.append(handleCalendarString);
            fullCmdCommand.append("\\");
            fullCmdCommand.append(twspFileName);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(twspDestPath);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(cmdEnd);
            cmdCommandArr.add(fullCmdCommand.toString());

//            copy briefing files
            fullCmdCommand.setLength(0);
            fullCmdCommand.append(cmdHead);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(briefingSrcPath);
            fullCmdCommand.append("\\");
            fullCmdCommand.append(handleCalendarString);
            fullCmdCommand.append("\\");
            fullCmdCommand.append(briefingFileName);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(briefingDestPath);
            fullCmdCommand.append(" ");
            fullCmdCommand.append(cmdEnd);
            cmdCommandArr.add(fullCmdCommand.toString());

            processCalendar.add(Calendar.DATE, 1);
        }
        for(Iterator<String> it = cmdCommandArr.iterator(); it.hasNext();){
            callCMD.executeCmd(it.next());
        }
    }

    public void testCMD() {

//        cmdCommandArr = new ArrayList<String>(){{add("cmd.exe /k ipconfig");add("cmd.exe /k java -version");}};
//        cmdCommandArr = new ArrayList<String>() {{
//            add("ipconfig");
//            add("java -version");
//        }};
//        cmdCommandArr = new ArrayList<String>() {{
//            add("cmd.exe /k ipconfig");
//            add("cmd.exe /k java -version");
//        }};

//        cmdCommandArr = new ArrayList<String>(){{
//            add("cmd.exe /k xcopy E:\\Z\\2016-10-13\\*国内及全球*简报.xlsx E:\\X\\JB\\ /f /y");
//            add("cmd.exe /k xcopy E:\\Z\\2016-10-14\\*twsp*.txt E:\\X\\twsp\\ /f /y");
//            add("cmd.exe /k xcopy E:\\Z\\2016-10-14\\*国内及全球*简报.xlsx E:\\X\\JB\\ /f /y");
//        }};
//        cmdCommandArr = new ArrayList<String>(){{
//            add("xcopy E:\\Z\\2016-10-13\\*国内及全球*简报.xlsx E:\\X\\JB\\ /f /y");
//            add("xcopy E:\\Z\\2016-10-14\\*twsp*.txt E:\\X\\twsp\\ /f /y");
//            add("xcopy E:\\Z\\2016-10-14\\*国内及全球*简报.xlsx E:\\X\\JB\\ /f /y");
//        }};
//        cmdCommandArr.add("cp /home/wsy/A/AAA /home/wsy/B/");
//        cmdCommandArr.add("ls");
//        cmdCommandArr.add("ls");
//        cmdCommandArr.add("ls");
//        cmdCommandArr.add("ipconfig");
//        callCMD.executeCmdArr(cmdCommandArr);
    }
}
