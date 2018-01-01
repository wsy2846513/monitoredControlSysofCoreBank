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
 * @Description: Store global properties and pass exception messages.
 * @Version: V1.0
 * @Date: 17-11-20 下午7:25
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GlobalProperties {
    //    The path of properties file when run in IDE.
//    private static String propertiesFilePath = "./src/main/resources/properties/environment.properties";
//    private static String iconPath =  "./src/main/resources/icon";

//    Need to copy properties manually when run as jar.
    private static String propertiesFilePath = "./properties/environment.properties";
    private static String iconPath =  "./icon";

    //    Only one ImportKit thread can run at a time.
    private static Semaphore allowImport = new Semaphore(1);

    //    When properties file was changed, such as auto.time, the SettingForm will release
// one and the AutoController will require one.
    private static Semaphore refreshProperties = new Semaphore(0);

    //    When the ImportKit was started, it will release one and the ProgressForm will require one.
    private static Semaphore openProgressForm = new Semaphore(0);

    //    When the ImportKit was finished, it will release one and the MainformMonitor will require one.
    private static Semaphore openMainForm = new Semaphore(1);

    //    When the ImportKit was started, it will release one and the MainformMonitor will require one.
    private static Semaphore closeMainForm = new Semaphore(0);

    //    When StartImport button was clicked, the MainForm will release one and
// the ManualController will require one.
    private static Semaphore startManualImport = new Semaphore(0);

    //    The targetCount and currentCount are used to get completion percent of one ImportKit task.
// There is no need to use "synchronized" because only one thread will write and only another
// one thread will read it.
    private static int targetCount;
    private static int currentCount;
    private static String latestDate;
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

    public static Semaphore getOpenMainForm() {
        return openMainForm;
    }

    public static Semaphore getCloseMainForm() {
        return closeMainForm;
    }

    public static int getPercentage() {
        return (int) (currentCount * 100.0 / targetCount);
    }

    public void setTargetCount(int targetCount) {
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

    public void setErrorMessage(String errorMessage,Exception exception) {
        GlobalProperties.errorMessage = errorMessage;
    }

    public static int getProgressFormRefreshMillisecond() {
        return PROGRESS_FORM_REFRESH_MILLISECOND;
    }

    public static String getLatestDate() {
        return latestDate;
    }

    public static void setLatestDate(String latestDate) {
        GlobalProperties.latestDate = latestDate;
    }

    public static String getIconPath() {
        return iconPath;
    }
}
