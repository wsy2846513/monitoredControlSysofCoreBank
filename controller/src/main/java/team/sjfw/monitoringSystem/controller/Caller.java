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
    private String briefingSrcPath;
    private String briefingAnalysisPath;
    private String briefingSqlPath;
    private String mysqlHost;
    private String mysqlUser;
    private String mysqlPassword;
    private String mysqlPort;
    private String mysqlDatabase;

    private String fullCMDCommand;

    private Calendar startDate;
    private Calendar endDate;

    private ArrayList<String> cmdCommandArr;

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
        this.startDate = CalendarAndString.StringToCalendar(environment.getProperty("start.date"));
        this.endDate = CalendarAndString.StringToCalendar(environment.getProperty("end.date"));
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
        * @Description:解析TWSP文件
        * @Date: 2017/11/13 21:30
        **/
        cmdCommandArr = new ArrayList<String>();
//        Calendar processDate = startDate;
        Calendar processDate = Calendar.getInstance();
        processDate.set(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH),startDate.get(Calendar.DATE),
                0,0,0);
        processDate.set(Calendar.MILLISECOND,0);
        int lastYear = endDate.get(Calendar.YEAR);
        int targetYear = processDate.get(Calendar.YEAR);
        System.out.println("targetYear:" + targetYear + "\tlastYear:" + lastYear);
        long startTime = System.nanoTime();
        while (targetYear <= lastYear) {
            fullCMDCommand = "PYTHON " + twspAnalysisProgram + " -s \"" + twspSrcPath
                    + "\" -d \"" + twspSqlPath + "\" -y " + targetYear;
            cmdCommandArr.add(fullCMDCommand);
            processDate.add(Calendar.YEAR, 1);
//            processDate.add(Calendar.DATE, 1);
            targetYear = processDate.get(Calendar.YEAR);
        }
        long endTime = System.nanoTime();
        System.out.println("String\t'+'\tcost:" + (endTime - startTime) + "\t\tarrSize = " + cmdCommandArr.size());
//        callCMD.executeCmdArr(cmdCommandArr);
//        StringBufferTest
        processDate.set(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH),startDate.get(Calendar.DATE),
                0,0,0);
        processDate.set(Calendar.MILLISECOND,0);
        targetYear = processDate.get(Calendar.YEAR);
        targetYear = startDate.get(Calendar.YEAR);
        cmdCommandArr = new ArrayList<String>();
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println("targetYear:" + targetYear + "\tlastYear:" + lastYear);
        startTime = System.nanoTime();
        while (targetYear <= lastYear) {
            stringBuffer.setLength(0);
            stringBuffer.append("PYTHON ").append(twspAnalysisProgram).append(" -s \"").append(twspSrcPath)
                    .append("\" -d \"").append(twspSqlPath).append("\" -y ").append(targetYear);
            cmdCommandArr.add(stringBuffer.toString());
//            processDate.add(Calendar.YEAR, 1);
            processDate.add(Calendar.DATE, 1);
            targetYear = processDate.get(Calendar.YEAR);
        }
        endTime = System.nanoTime();
        System.out.println("StringBuffer\t'setlength'\tcost:" + (endTime - startTime) + "\t\tarrSize = " + cmdCommandArr.size());
//        StringBufferTest new
        processDate = startDate;
        targetYear = processDate.get(Calendar.YEAR);
        cmdCommandArr = new ArrayList<String>();
        System.out.println("targetYear:" + targetYear + "\tlastYear:" + lastYear);
        startTime = System.nanoTime();
        while (targetYear <= lastYear) {
            stringBuffer = new StringBuffer();
            stringBuffer.append("PYTHON ").append(twspAnalysisProgram).append(" -s \"").append(twspSrcPath)
                    .append("\" -d \"").append(twspSqlPath).append("\" -y ").append(targetYear);
            cmdCommandArr.add(stringBuffer.toString());
//            processDate.add(Calendar.YEAR, 1);
            processDate.add(Calendar.DATE, 1);
            targetYear = processDate.get(Calendar.YEAR);
        }
        endTime = System.nanoTime();
        System.out.println("StringBuffer\t'new'\tcost:" + (endTime - startTime) + "\t\tarrSize = " + cmdCommandArr.size());
        //        StringBufferTest delete
        processDate = startDate;
        targetYear = processDate.get(Calendar.YEAR);
        cmdCommandArr = new ArrayList<String>();
        System.out.println("targetYear:" + targetYear + "\tlastYear:" + lastYear);
        startTime = System.nanoTime();
        while (targetYear <= lastYear) {
            stringBuffer.delete(0,stringBuffer.length());
            stringBuffer.append("PYTHON ").append(twspAnalysisProgram).append(" -s \"").append(twspSrcPath)
                    .append("\" -d \"").append(twspSqlPath).append("\" -y ").append(targetYear);
            cmdCommandArr.add(stringBuffer.toString());
//            processDate.add(Calendar.YEAR, 1);
            processDate.add(Calendar.DATE, 1);
            targetYear = processDate.get(Calendar.YEAR);
        }
        endTime = System.nanoTime();
        System.out.println("StringBuffer\t'delete'\tcost:" + (endTime - startTime) + "\t\tarrSize = " + cmdCommandArr.size());
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

        cmdCommandArr = new ArrayList<String>();
        fullCMDCommand = "PYTHON " + briefingAnalysisPath + " -s \"" + briefingSrcPath
                + "\" -d \"" + briefingSqlPath + "\"";
        cmdCommandArr.add(fullCMDCommand);
        callCMD.executeCmdArr(cmdCommandArr);
    }

    public void importSql() {
        cmdCommandArr = new ArrayList<String>();
        Calendar processDate = startDate;
        String sqlFilePath = "123";
        while (!processDate.after(endDate)) {
            fullCMDCommand = "mysql.exe --force --comments --default-character-set=utf8"
                    + " --host=" + mysqlHost + " --port" + mysqlPort + "--database=" + mysqlDatabase
                    + " --user=" + mysqlUser + "--password=" + mysqlPassword + sqlFilePath;
            processDate.add(Calendar.DATE, 1);
        }
    }
}
