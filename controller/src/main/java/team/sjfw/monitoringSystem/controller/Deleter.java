package team.sjfw.monitoringSystem.controller;

import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * @Tittle: Deleter.java
 * @Author: wsy
 * @Class_name: Deleter
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Delete files.
 * @Version: V1.0
 * @Date: 2017/11/15 20:17
 */
@Controller
public class Deleter {
    public boolean deleteFilesInFolder(String folderPath) throws Exception {
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            throw new Exception("It is not a folder:" + folderPath);
        }
        String[] fileList = folder.list();
        for (int i = 0; i < fileList.length; i++) {
            File toDelete = new File(folderPath + "\\" + fileList[i]);
            toDelete.delete();
        }
        return true;
    }
}
