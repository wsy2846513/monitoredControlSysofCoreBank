package pers.wsy.tools.interconversion;

public class CallCMD {



    public static void executeCmd(Process process, String parameter) {
        try {
            System.out.println(this.getClass().getSimpleName() + "\texecuteCmd: " + parameter);
//            process = Runtime.getRuntime().exec(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return true;
    }
}
