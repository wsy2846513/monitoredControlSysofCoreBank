/**
 * @Tittle: Caller.java
 * @Author: wsy
 * @Class_name: Caller
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description:
 * @Version: V1.0
 * @Date: 2017/11/13 21:24
 **/

package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.CalendarTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


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
    private String cmdHead = "PYTHON ";


    private Calendar startDate;
    private Calendar endDate;

    @Autowired
    CallCMD callCMD;

    @Autowired
    public Caller(Environment environment) {
        this.twspSrcPath = environment.getProperty("copy.twsp.destPath");
        this.twspAnalysisProgram = environment.getProperty("program.twsp.analysisPath");
        this.twspSqlPath = environment.getProperty("program.twsp.sqlPath");
        this.briefingSrcPath = environment.getProperty("copy.briefing.destPath");
        this.briefingAnalysisPath = environment.getProperty("program.briefing.analysisPath");
        this.briefingSqlPath = environment.getProperty("program.briefing.sqlPath");
        this.startDate = CalendarTools.StringToCalendar(environment.getProperty("start.date"), "yyyy-MM-dd");
        this.endDate = CalendarTools.StringToCalendar(environment.getProperty("end.date"), "yyyy-MM-dd");
        this.mysqlHost = environment.getProperty("mysql.host");
        this.mysqlUser = environment.getProperty("mysql.user");
        this.mysqlPassword = environment.getProperty("mysql.password");
        this.mysqlPort = environment.getProperty("mysql.port");
        this.mysqlDatabase = environment.getProperty("mysql.database");
    }

    public void analyseTwsp() {
        /**
         * @Author: wsy
         * @MethodName: analyseTwsp
         * @Return: void
         * @Param: []
         * @Description: Convert twsp files into sql files.
         * @Date: 17-11-14 下午5:30
         */
        Calendar processCalendar = (Calendar) startDate.clone();
        ArrayList<String> cmdCommandArr = new ArrayList<String>();
        StringBuffer fullCMDCommand = new StringBuffer();
        int lastYear = endDate.get(Calendar.YEAR);
        int targetYear = processCalendar.get(Calendar.YEAR);
        while (targetYear <= lastYear) {
            fullCMDCommand.setLength(0);
            fullCMDCommand.append(cmdHead);
            fullCMDCommand.append(twspAnalysisProgram);
            fullCMDCommand.append(" -s \"");
            fullCMDCommand.append(twspSrcPath);
            fullCMDCommand.append("\" -d \"");
            fullCMDCommand.append(twspSqlPath);
            fullCMDCommand.append("\" -y ");
            fullCMDCommand.append(targetYear);
            cmdCommandArr.add(fullCMDCommand.toString());
            processCalendar.add(Calendar.YEAR, 1);
            targetYear = processCalendar.get(Calendar.YEAR);
        }
        for(Iterator<String> it = cmdCommandArr.iterator(); it.hasNext();){
            callCMD.executeCmd(it.next());
        }
    }

    public void analyseBriefing() {
        /**
         * @Author: wsy
         * @MethodName: analyseBriefing
         * @Return: void
         * @Param: []
         * @Description: 解析简报
         * @Date: 2017/11/13 21:33
         **/

        ArrayList<String> cmdCommandArr = new ArrayList<String>();
        StringBuffer fullCMDCommand = new StringBuffer();
        fullCMDCommand.setLength(0);
        fullCMDCommand.append(cmdHead);
        fullCMDCommand.append(briefingAnalysisPath);
        fullCMDCommand.append(" -s \"");
        fullCMDCommand.append(briefingSrcPath);
        fullCMDCommand.append("\" -d \"");
        fullCMDCommand.append(briefingSqlPath);
        fullCMDCommand.append("\"");
        cmdCommandArr.add(fullCMDCommand.toString());
        for(Iterator<String> it = cmdCommandArr.iterator(); it.hasNext();){
            callCMD.executeCmd(it.next());
        }
    }

    public void importSql() {
        Calendar processCalendar = (Calendar) startDate.clone();
        ArrayList<String> cmdCommandArr = new ArrayList<String>();
        StringBuffer fullCMDCommand = new StringBuffer();
        StringBuffer cmdHead = new StringBuffer();
        String cmdTail = ".sql\"";
        cmdHead.append("mysql.exe --force --comments --default-character-set=utf8 --host=");
        cmdHead.append(mysqlHost);
        cmdHead.append(" --port=");
        cmdHead.append(mysqlPort);
        cmdHead.append(" --database=");
        cmdHead.append(mysqlDatabase);
        cmdHead.append(" --user=");
        cmdHead.append(mysqlUser);
        cmdHead.append(" --password=");
        cmdHead.append(mysqlPassword);
        cmdHead.append(" < \"");
        while (!processCalendar.after(endDate)) {
            fullCMDCommand.setLength(0);
            fullCMDCommand.append(cmdHead);
            fullCMDCommand.append(twspSqlPath);
            fullCMDCommand.append("\\report_");
            fullCMDCommand.append(CalendarTools.calendarToString(processCalendar,"yyyyMMdd"));
            fullCMDCommand.append(cmdTail);
            cmdCommandArr.add(fullCMDCommand.toString());
            processCalendar.add(Calendar.DATE, 1);
        }
        for(Iterator<String> it = cmdCommandArr.iterator(); it.hasNext();){
            callCMD.executeCmd(it.next());
        }
    }
}
