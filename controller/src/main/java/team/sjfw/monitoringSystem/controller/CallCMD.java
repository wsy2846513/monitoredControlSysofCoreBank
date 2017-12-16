package team.sjfw.monitoringSystem.controller;

import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

//        process = Runtime.getRuntime().exec(parameter);
        process = Runtime.getRuntime().exec("cp /home/wsy/A/AAA /home/wsy/B/");
//        process = Runtime.getRuntime().exec("ifconfig wlp3s0");

        String lineData = String.valueOf(process.waitFor());
        cmdLog.add(lineData);

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
        while ((lineData = reader.readLine()) != null) {
            cmdLog.add(lineData);
        }
        return cmdLog;
    }

    public ArrayList<String> executeCmdArr(ArrayList<String> cmdCommandArr) {
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
        try {
            for (String parameter : cmdCommandArr) {
                process = Runtime.getRuntime().exec(parameter);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

                while ((lineData = reader.readLine()) != null) {
                    System.out.println("cmdarr: \t" + lineData);
                    cmdLog.add(lineData);
                }
            }
            return cmdLog;
        } catch (Exception e) {
            //异常处理的信息部分，由日志负责，此处只处理逻辑部分、
            return null;
        }
    }
}
