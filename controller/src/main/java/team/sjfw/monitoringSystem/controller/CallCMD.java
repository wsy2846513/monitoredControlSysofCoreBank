package team.sjfw.monitoringSystem.controller;

import org.springframework.stereotype.Controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @Tittle: CallCMD.java
 * @Author: wsy
 * @Class_name: CallCMD
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Call cmd to execute program according to parameters.
 * @Version: V1.0
 * @Date: 2017/12/25 22:14
 */

@Controller
public class CallCMD {
    private Process process;

    public ArrayList<String> executeCmd(String parameter) throws Exception {
        /**
         * @Author: wsy
         * @MethodName: executeCmd
         * @Return: java.io.BufferedReader
         * @Param: [parameter]
         * @Description:
         * @Date: 2017/11/13 21:32
         **/

//        CmdLog contains the exist code of process at first, and the stdout strings are stored following.
        ArrayList<String> cmdLog = new ArrayList<String>();

        process = Runtime.getRuntime().exec(parameter);

        String lineData = String.valueOf(process.waitFor());
        cmdLog.add(lineData);

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
        while ((lineData = reader.readLine()) != null) {
            cmdLog.add(lineData);
        }
        return cmdLog;
    }

    public ArrayList<String> executeCmdArr(ArrayList<String> cmdCommandArr) throws Exception{
        /**
         * @Author: wsy
         * @MethodName: executeCmdArr
         * @Return: java.util.ArrayList<java.lang.String>
         * @Param: [cmdCommandArr]
         * @Description: Execute all commands in [cmdCommandArr].
         * @Date: 17-11-14 下午12:11
         */
        ArrayList<String> cmdLog = new ArrayList<String>();
        String lineData;
        for (String parameter : cmdCommandArr) {
            process = Runtime.getRuntime().exec(parameter);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

            while ((lineData = reader.readLine()) != null) {
                System.out.println("cmdarr: \t" + lineData);
                cmdLog.add(lineData);
            }
        }
        return cmdLog;
    }
}
