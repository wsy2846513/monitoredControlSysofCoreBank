package team.sjfw.monitoringSystem.controller;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.wsy.tools.SafeProperties;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * @Tittle: ExecuteSqlFile.java
 * @Author: wsy
 * @Class_name: ExecuteSqlFile
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Execute sql file.
 * @Version: V1.0
 * @Date: 2017/12/30 14:53
 */

@Controller
public class ExecuteSqlFile {

    private String host;
    private String user;
    private String password;
    private String port;
    private String database;
    private String driver;
    private GlobalProperties globalProperties;

    @Autowired
    public ExecuteSqlFile(GlobalProperties inputGlobalProperties) {
        this.globalProperties = inputGlobalProperties;
    }

    public void initializeAll() throws Exception {
        getProperties();
        loadDriver();
    }

    private void getProperties() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: getProperties
         * @Return: void
         * @Param: []
         * @Description: Get properties.
         * @Date: 2017/12/30 15:13
         */

//        Load properties
        SafeProperties properties = new SafeProperties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(globalProperties.getPropertiesFilePath()));
        properties.load(new InputStreamReader(inputStream, "utf-8"));
        inputStream.close();

//        Get properties
        this.host = properties.getProperty("MySQL.host");
        this.user = properties.getProperty("MySQL.user");
        this.password = properties.getProperty("MySQL.password");
        this.port = properties.getProperty("MySQL.port");
        this.database = properties.getProperty("MySQL.database");
        this.driver = properties.getProperty("MySQL.driver");
    }

    private void loadDriver() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: loadDriver
         * @Return: void
         * @Param: []
         * @Description: Load MySQL driver.
         * @Date: 2017/12/30 15:13
         */

        Class.forName(driver).newInstance();
    }

    public void execute(ArrayList<String> pathArray) throws Exception {
        /**
         * @Author: wsy
         * @MethodName: execute
         * @Return: void
         * @Param: [pathArray]
         * @Description:  Execute sql file.
         * @Date: 2017/12/31 13:39
         */

//        get MySQL url
        StringBuffer mySQLurl = new StringBuffer();
        mySQLurl.append("jdbc:mysql://");
        mySQLurl.append(host);
        mySQLurl.append(":");
        mySQLurl.append(port);
        mySQLurl.append("/");
        mySQLurl.append(database);
        mySQLurl.append("?user=");
        mySQLurl.append(user);
        mySQLurl.append("&password=");
        mySQLurl.append(password);
        mySQLurl.append("&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false");

//        Set Connection and ScriptRunner
        Connection connection = DriverManager.getConnection(mySQLurl.toString());
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setAutoCommit(true);
        runner.setStopOnError(false);

//        Get running date for output
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String runningDate = dateFormat.format(new Date());
        StringBuffer stringBuffer = new StringBuffer();
        PrintWriter printWriter = null;

//        Execute all sql file in array
        for (Iterator it = pathArray.iterator(); it.hasNext(); ) {
            String sqlFilePath = it.next().toString();
            File sqlFile = new File(sqlFilePath);

//            Get PrintWriter file path for log in  ScriptRunner
            stringBuffer.setLength(0);
            stringBuffer.append(".\\log\\");
            stringBuffer.append(runningDate);
            stringBuffer.append("-sql-");
            stringBuffer.append(sqlFilePath.substring(sqlFilePath.lastIndexOf('\\')+1,sqlFilePath.lastIndexOf('.')));
            stringBuffer.append(".log");

//            Create log file
            File logFile = new File(stringBuffer.toString());
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

//            Set ScriptRunner log path
            printWriter = new PrintWriter(logFile);
            runner.setLogWriter(printWriter);
            runner.setErrorLogWriter(printWriter);
            if (sqlFile.exists()) {
                printWriter.write("sql file exist:\r\n");
                printWriter.flush();
                runner.runScript(new InputStreamReader(new FileInputStream(sqlFilePath), "UTF-8"));
            } else {
                printWriter.write("sql file not exist:\r\n");
                printWriter.flush();
            }
//            Add finished count
            globalProperties.addCurrentCount(globalProperties.CALLER_IMPORT_SQL);
        }

//        Close IO
        runner.closeConnection();
        connection.close();
        printWriter.close();
    }
}
