package pers.wsy.test.EfficiencyTest;

import java.util.ArrayList;

/**
 * @Tittle: StringBufferEfficiency.java
 * @Author: wsy
 * @Class_name: StringBufferEfficiency
 * @Package: pers.wsy.tools.tools
 * @Description: 测试StringBuffer等效率问题
 * @Version: V1.0
 * @Date: 2017/11/13 22:13
 **/

public class StringBufferEfficiency {
    private long endLine = 999999;
    private StringBuffer stringBuffer = new StringBuffer();

    private void testNew(){
        ArrayList<String> arr = new ArrayList<String>();
        long startTime = System.nanoTime();
        for(long i = 0; i < endLine; ++i){
            stringBuffer = new StringBuffer();
            stringBuffer.append("PYTHON ").append("twspAnalysisProgram").append(" -s \"").append("twspSrcPath")
                    .append("\" -d \"").append("twspSqlPath").append("\" -y ").append("targetYear");
            arr.add(stringBuffer.toString());
        }
        long endTime = System.nanoTime();
        System.out.println("StringBuffer\t'new'\t\tcost:" + (endTime - startTime) + "\t\tarrSize = " + arr.size());
    }

    private void testSetLength(){
        ArrayList<String> arr = new ArrayList<String>();
        long startTime = System.nanoTime();
        for(long i = 0; i < endLine; ++i){
            stringBuffer.setLength(0);
            stringBuffer.append("PYTHON ").append("twspAnalysisProgram").append(" -s \"").append("twspSrcPath")
                    .append("\" -d \"").append("twspSqlPath").append("\" -y ").append("targetYear");
            arr.add(stringBuffer.toString());
        }
        long endTime = System.nanoTime();
        System.out.println("StringBuffer\t'setLength'\tcost:" + (endTime - startTime) + "\t\tarrSize = " + arr.size());
    }

    private void testDelete(){
        ArrayList<String> arr = new ArrayList<String>();
        long startTime = System.nanoTime();
        for(long i = 0; i < endLine; ++i){
            stringBuffer = new StringBuffer();
            stringBuffer.append("PYTHON ").append("twspAnalysisProgram").append(" -s \"").append("twspSrcPath")
                    .append("\" -d \"").append("twspSqlPath").append("\" -y ").append("targetYear");
            arr.add(stringBuffer.toString());
        }
        long endTime = System.nanoTime();
        System.out.println("StringBuffer\t'delete'\tcost:" + (endTime - startTime) + "\t\tarrSize = " + arr.size());
    }

    public static void main(String[] args){
        StringBufferEfficiency t = new StringBufferEfficiency();
        t.testDelete();;
        t.testSetLength();
        t.testNew();
    }
}
