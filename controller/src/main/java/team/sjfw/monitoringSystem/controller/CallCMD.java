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
                System.out.println("CallCMD -- executeCmd:\texecuteCmd: " + parameter);
                process = Runtime.getRuntime().exec(parameter);
//                process = Runtime.getRuntime().exec("ipconfig");
//                process = Runtime.getRuntime().exec("cmd.exe /k xcopy E:\\Z\\2016-10-13\\*twsp*.txt E:\\X\\twsp\\ /F");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                BufferedReader reader = new BufferedReader(new FileReader("E:/123.txt"));
//                System.out.println(reader.readLine());
                while ((lineData = reader.readLine()) != null){
                    System.out.println("cmdarr: \t" + lineData);
                    cmdLog.add(lineData);
                }
//                System.out.println("while over");
//                return cmdLog;
//            return  new BufferedReader(new FileReader("E:/123.txt"));
            }
            return cmdLog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
