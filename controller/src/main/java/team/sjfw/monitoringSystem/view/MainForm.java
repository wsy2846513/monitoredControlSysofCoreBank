package team.sjfw.monitoringSystem.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wsy.tools.SafeProperties;
import team.sjfw.monitoringSystem.controller.GlobalProperties;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.Semaphore;

/**
 * @Tittle: MainForm.java
 * @Author: wsy
 * @Class_name: MainForm
 * @Package: team.sjfw.monitoringSystem.view
 * @Description: The mainForm show the main operations for users.
 * @Version: V1.0
 * @Date: 2017/12/25 22:26
 */

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

    private SettingForm settingForm;

    private GlobalProperties globalProperties;

    @Autowired
    public MainForm(GlobalProperties inputGlobalProperties, SettingForm inputSettingForm) {
        globalProperties = inputGlobalProperties;
        settingForm = inputSettingForm;
        frame = new JFrame("");
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();
        this.iconPath = globalProperties.getIconPath();
        this.startManualImport = globalProperties.getStartManualImport();

        this.refresh();
        this.initializeAll();

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4513998)));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 7, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("YouYuan", Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("每日自动导入开关");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelAutoSwitch = new JLabel();
        Font labelAutoSwitchFont = this.$$$getFont$$$("YouYuan", Font.BOLD, 16, labelAutoSwitch.getFont());
        if (labelAutoSwitchFont != null) labelAutoSwitch.setFont(labelAutoSwitchFont);
        labelAutoSwitch.setForeground(new Color(-11355318));
        labelAutoSwitch.setText("三个字");
        panel1.add(labelAutoSwitch, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("YouYuan", Font.BOLD, 16, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("自动导入时间");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelAutoImportTime = new JLabel();
        Font labelAutoImportTimeFont = this.$$$getFont$$$("YouYuan", Font.BOLD, 16, labelAutoImportTime.getFont());
        if (labelAutoImportTimeFont != null) labelAutoImportTime.setFont(labelAutoImportTimeFont);
        labelAutoImportTime.setText("20:00");
        panel1.add(labelAutoImportTime, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonStartImport = new JButton();
        Font buttonStartImportFont = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, buttonStartImport.getFont());
        if (buttonStartImportFont != null) buttonStartImport.setFont(buttonStartImportFont);
        buttonStartImport.setText("开始导入（闭区间）");
        panel2.add(buttonStartImport, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonSet = new JButton();
        Font buttonSetFont = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, buttonSet.getFont());
        if (buttonSetFont != null) buttonSet.setFont(buttonSetFont);
        buttonSet.setText("配置");
        panel2.add(buttonSet, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setEnabled(true);
        Font label3Font = this.$$$getFont$$$("YouYuan", -1, 26, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("截止到");
        panel3.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelLatestDate = new JLabel();
        labelLatestDate.setEnabled(true);
        Font labelLatestDateFont = this.$$$getFont$$$("YouYuan", Font.BOLD | Font.ITALIC, 26, labelLatestDate.getFont());
        if (labelLatestDateFont != null) labelLatestDate.setFont(labelLatestDateFont);
        labelLatestDate.setForeground(new Color(-13343045));
        labelLatestDate.setText("XXXX-XX-XX");
        panel3.add(labelLatestDate, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("YouYuan", Font.BOLD, 26, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("的数据已处理完");
        label4.setVerticalAlignment(0);
        panel3.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(10, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(10, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(40, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 10), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 10), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 40), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer10 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer10, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 40), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer11 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer11, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(40, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("YouYuan", Font.BOLD, 16, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("需要导入的数据起始日期：");
        panel4.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("YouYuan", Font.BOLD, 16, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("需要导入的数据结束日期：");
        panel4.add(label6, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldEndDate = new JTextField();
        Font textFieldEndDateFont = this.$$$getFont$$$("YouYuan", Font.BOLD | Font.ITALIC, -1, textFieldEndDate.getFont());
        if (textFieldEndDateFont != null) textFieldEndDate.setFont(textFieldEndDateFont);
        textFieldEndDate.setText("1900-00-00");
        panel4.add(textFieldEndDate, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, -1), null, 0, false));
        textFieldStartDate = new JTextField();
        Font textFieldStartDateFont = this.$$$getFont$$$("YouYuan", Font.BOLD | Font.ITALIC, -1, textFieldStartDate.getFont());
        if (textFieldStartDateFont != null) textFieldStartDate.setFont(textFieldStartDateFont);
        textFieldStartDate.setText("1900-00-00");
        panel4.add(textFieldStartDate, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer12 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer12, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer13 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer13, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer14 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer14, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 20), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
