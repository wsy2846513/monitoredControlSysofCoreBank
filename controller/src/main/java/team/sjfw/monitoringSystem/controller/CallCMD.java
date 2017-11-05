package team.sjfw.monitoringSystem.controller;

import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Controller
public class CallCMD {
    private Process process;

    public BufferedReader executeCmd(String parameter) {
        try {
            System.out.println("CallCMD -- executeCmd:\texecuteCmd: " + parameter);
            process = Runtime.getRuntime().exec(parameter);
            System.out.println("after process");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            System.out.println("after reader");
            return reader;
//            return  new BufferedReader(new FileReader("E:/123.txt"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> executeCmdArr(ArrayList<String> cmdCommandArr) {
        ArrayList<String> cmdLog = new ArrayList<String>();
        String lineData;
        try {
            for (String parameter : cmdCommandArr) {
                System.out.println("CallCMD -- executeCmdArr:\t" + parameter);
//                process = Runtime.getRuntime().exec(parameter);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
//                while ((lineData = reader.readLine()) != null){
//                    System.out.println("cmdarr: \t" + lineData);
//                    cmdLog.add(lineData);
//                }
            }
            return cmdLog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
