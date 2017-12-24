package team.sjfw.monitoringSystem.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wsy.tools.SafeProperties;
import team.sjfw.monitoringSystem.controller.GlobalProperties;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.Semaphore;

@Component
public class MainForm {
    private JFrame frame;

    private JPanel mainPanel;
    private JButton buttonStartImport;
    private JButton buttonSet;
    private JTextField textFieldEndDate;
    private JTextField textFieldStartDate;
    private JLabel labelLatestDate;
    private JLabel labelAutoSwitch;
    private JLabel labelAutoImportTime;
    private String propertiesFilePath;
    private String iconPath;
    private MainForm thisObject;


    private Semaphore startManualImport;

    @Autowired
    private SettingForm settingForm;

    @Autowired
    private GlobalProperties globalProperties;

    public MainForm() {
//        frame = new JFrame("");
//        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
//        this.iconPath = globalProperties.getIconPath();
//        this.startManualImport = globalProperties.getStartManualImport();
//
//        this.refresh();
//        this.initializeAll();

        buttonSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * @Author: wsy
                 * @MethodName: actionPerformed
                 * @Return: void
                 * @Param: [e]
                 * @Description: When click setting button, open the setting form.
                 * @Date: 17-12-19 下午7:15
                 */

                try {
                    frame.setVisible(false);
                    settingForm.setMainForm(thisObject);
                    settingForm.show();
                } catch (Exception exception) {
                    globalProperties.setErrorMessage(exception.toString(), exception);
                    globalProperties.setErrorOccured(true);
                }
            }
        });
        buttonStartImport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    /**
                     * @Author: wsy
                     * @MethodName: actionPerformed
                     * @Return: void
                     * @Param: [e]
                     * @Description: When click start button, save properties and release semaphore.
                     * @Date: 17-12-19 下午7:14
                     */

//                    Load the properties
                    SafeProperties properties = new SafeProperties();
                    InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
                    properties.load(inputStream);
                    inputStream.close();

//                    Set new properties values
                    properties.setProperty("start.date", textFieldStartDate.getText());
                    properties.setProperty("end.date", textFieldEndDate.getText());

//                    Write the properties
                    FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
                    properties.store(fileOutputStream, null);
                    fileOutputStream.close();

//                    Start manual import
                    startManualImport.release();
                } catch (Exception exception) {
                    globalProperties.setErrorMessage(exception.toString(), exception);
                    globalProperties.setErrorOccured(true);
                }
            }
        });
    }

    private void refresh() {
        /**
         * @Author: wsy
         * @MethodName: refresh
         * @Return: boolean
         * @Param: []
         * @Description: Refresh the factors in the main form
         * @Date: 17-12-19 下午7:16
         */

        try {
//            Load properties
            SafeProperties properties = new SafeProperties();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(new InputStreamReader(inputStream, "utf-8"));
            inputStream.close();

//            Show properties
            this.labelLatestDate.setText(properties.getProperty("latest.date"));
            if (properties.getProperty("autoImport.swtich").equals("on")) {
                this.labelAutoSwitch.setForeground(new Color(41, 139, 63));
                this.labelAutoSwitch.setText("已开启");
            } else if (properties.getProperty("autoImport.swtich").equals("off")) {
                this.labelAutoSwitch.setForeground(Color.red);
                this.labelAutoSwitch.setText("已关闭");
            }
            this.labelAutoImportTime.setText(properties.getProperty("autoImport.time"));
            this.textFieldStartDate.setText(properties.getProperty("start.date"));
            this.textFieldEndDate.setText(properties.getProperty("end.date"));
        } catch (Exception exception) {
            globalProperties.setErrorMessage(exception.toString(), exception);
            globalProperties.setErrorOccured(true);
        }
    }

    public void initializeAll() {
        /**
         * @Author: wsy
         * @MethodName: initializeAll
         * @Return: void
         * @Param: []
         * @Description: Initialize all factors.
         * @Date: 17-12-19 下午7:22
         */

        frame = new JFrame("");
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        this.iconPath = globalProperties.getIconPath();
        this.startManualImport = globalProperties.getStartManualImport();
        this.refresh();

        this.setThisObject(this);
        this.initializeFrame();
    }

    private void initializeFrame() {
        /**
         * @Author: wsy
         * @MethodName: initializeFrame
         * @Return: void
         * @Param: []
         * @Description: Initialize frame.
         * @Date: 17-12-21 上午11:23
         */
        try {
            Image image = Toolkit.getDefaultToolkit().getImage(iconPath + "\\mainForm.png");
            frame.setIconImage(image);
            frame.setContentPane(this.mainPanel);
            reLocation();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception exception) {
            globalProperties.setErrorMessage(exception.toString(), exception);
            globalProperties.setErrorOccured(true);
        }
    }

    private void reLocation() {
        /**
         * @Author: wsy
         * @MethodName: reLocation
         * @Return: void
         * @Param: []
         * @Description: Set the frame location
         * @Date: 17-12-19 下午7:47
         */

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 560;
        int frameHeight = 386;
        frame.setLocation((screenWidth - frameWidth) / 2,
                (screenHeight - frameHeight) / 2);
    }

    public void setFrameVisible(boolean flag) {
        /**
         * @Author: wsy
         * @MethodName: setFrameVisible
         * @Return: void
         * @Param: [flag]
         * @Description: Show the frame.
         * @Date: 17-11-24 上午12:35
         */

        refresh();
//        The frame will move if do not relocation here.
        reLocation();
        frame.setVisible(flag);
    }

    private void setThisObject(MainForm thisObject) {
        /**
         * @Author: wsy
         * @MethodName: setThisObject
         * @Return: void
         * @Param: [thisObject]
         * @Description: Assign this object to field thisObject to show main form
         *              when setting form closed.
         * @Date: 17-12-19 下午7:22
         */

        this.thisObject = thisObject;
    }
}
