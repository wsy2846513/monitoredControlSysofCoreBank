package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

/**
 * @Tittle: GlobalProperties.java
 * @Author: wsy
 * @Class_name: GlobalProperties
 * @Package: team.sjfw.monitoringSystem.controller
 * @Description: Store global properties
 * @Version: V1.0
 * @Date: 17-11-20 下午7:25
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GlobalProperties {
//    Properties file path in linux.
//    private String propertiesFilePath = "./src/main/resources/environment.properties";
//    Properties file path in windows.
    private static String propertiesFilePath = "./src/main/resources/environment.properties";
//    Only one ImportKit thread can run at a time.
    private static Semaphore allowImport = new Semaphore(1);
//    When properties file was changed, such as auto.time, the SettingForm will release
// one and the AutoController will require one.
    private static Semaphore refreshProperties = new Semaphore(0);
//    When the ImportKit was started, it will release one and the ProgressForm will require one.
    private static Semaphore openProgressForm = new Semaphore(0);
//    When the ImportKit was finished, it will release one and the Mainform will require one.
    private static Semaphore closeProgressForm = new Semaphore(0);
//    When StartImport button was clicked, the MainForm will release one and
// the ManualController will require one.
    private static Semaphore startManualImport = new Semaphore(0);
//    The targetCount and currentCount are used to get completion percent of one ImportKit task.
// There is no need to use "synchronized" because only one thread will write and only another
// one thread will read it.
    private static int targetCount;
    private static int currentCount;

    private static int numofDaystoProcessed;
    private static boolean errorOccured;
    private static String errorMessage;
    public static final int DUPLICATOR_DELETE_POINT = 1;
    public static final int DUPLICATOR_COPY_POINT = 1;
    public static final int CALLER_ANALYSE_TWSP = 1;
    public static final int CALLER_ANALYSE_BRIEFING = 1;
    public static final int CALLER_ANALYSE_CRITICAL_PATHT = 3;
    public static final int CALLER_IMPORT_SQL = 1;
    public static final int CALLER_REPORT_IMPORT_ASSISTANT = 3;
    public static final int PROGRESS_FORM_REFRESH_MILLISECOND = 1000;



    public static String getPropertiesFilePath() {
        return propertiesFilePath;
    }

    public static Semaphore getAllowImport() {
        return allowImport;
    }

    public static Semaphore getRefreshProperties() {
        return refreshProperties;
    }

    public static Semaphore getOpenProgressForm() {
        return openProgressForm;
    }

    public static Semaphore getStartManualImport() {
        return startManualImport;
    }

    public static Semaphore getCloseProgressForm() {
        return closeProgressForm;
    }

    public static int getPercentage() {
        return (int)(currentCount * 100.0 / targetCount);
    }

    public static void setTargetCount(int targetCount) {
        GlobalProperties.targetCount = targetCount;
    }

    public static void addCurrentCount(int currentCount) {
        GlobalProperties.currentCount += currentCount;
    }

    public static void setCurrentCount(int currentCount) {
        GlobalProperties.currentCount = currentCount;
    }

    public static int getNumofDaystoProcessed() {
        return numofDaystoProcessed;
    }

    public static void setNumofDaystoProcessed(int numofDaystoProcessed) {
        GlobalProperties.numofDaystoProcessed = numofDaystoProcessed;
    }

    public static boolean isErrorOccured() {
        return errorOccured;
    }

    public static void setErrorOccured(boolean errorOccured) {
        GlobalProperties.errorOccured = errorOccured;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(String errorMessage) {
        GlobalProperties.errorMessage = errorMessage;
    }

    public static int getProgressFormRefreshMillisecond() {
        return PROGRESS_FORM_REFRESH_MILLISECOND;
    }
}
