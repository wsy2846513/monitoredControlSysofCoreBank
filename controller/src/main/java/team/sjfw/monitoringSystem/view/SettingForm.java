package team.sjfw.monitoringSystem.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wsy.tools.SafeProperties;
import team.sjfw.monitoringSystem.controller.GlobalProperties;
import team.sjfw.monitoringSystem.controller.Inspector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.Semaphore;

/**
 * @Tittle: SettingForm.java
 * @Author: wsy
 * @Class_name: SettingForm
 * @Package: team.sjfw.monitoringSystem.view
 * @Description: This form show the setting operations.
 * @Version: V1.0
 * @Date: 2017/12/25 22:28
 */

@Component
public class SettingForm {
    private JFrame frame;
    private JRadioButton radioButtonAutoImportOn;
    private JRadioButton radioButtonAutoImportOff;
    private JPanel mainPanel;
    private JTextField textFieldMySQLIp;
    private JTextField textFieldProgramReportImportAssistant;
    private JTextField textFieldMySQLPort;
    private JTextField textFieldMySQLDatabase;
    private JTextField textFieldMySQLUser;
    private JTextField textFieldMySQLPassword;
    private JTextField textFieldCopyTwspSrcPath;
    private JButton buttonSave;
    private JButton buttonRecover;
    private JTextField textFieldAutoImportTime;
    private JTextField textFieldCopyTwspDestPath;
    private JTextField textFieldCopyTwspFileName;
    private JTextField textFieldCopyBriefingSrcPath;
    private JTextField textFieldCopyBriefingDestPath;
    private JTextField textFieldCopyBriefingFileNaem;
    private JTextField textFieldProgramTwspAnalyse;
    private JTextField textFieldProgramTwspSQLPath;
    private JTextField textFieldProgramBriefingAnalyse;
    private JTextField textFieldProgramBriefingSQLPath;
    private JTextField textFieldProgramCritical;
    private String propertiesFilePath;
    private String iconPath;
    private Semaphore refreshProperties;
    private MainForm mainForm;

    private Inspector inspector;

    private GlobalProperties globalProperties;

    @Autowired
    public SettingForm(GlobalProperties inputGlobalProperties, Inspector inputInspector) {
        globalProperties = inputGlobalProperties;
        inspector = inputInspector;
        propertiesFilePath = globalProperties.getPropertiesFilePath();
        refreshProperties = globalProperties.getRefreshProperties();
        iconPath = globalProperties.getIconPath();
        frame = new JFrame("");
        initializeAll();

//        This method is used to replace the frame.setDefaultCloseOperation method,
//        so that when click the exit button, it will show the main form and hide the
//        setting form.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                mainForm.setFrameVisible(true);
            }
        });

        buttonRecover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pushButtonRefresh();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pushButtonSave();
            }
        });
    }


    public void setMainForm(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    private void pushButtonSave() {
        /**
         * @Author: wsy
         * @MethodName: pushButtonSave
         * @Return: void
         * @Param: []
         * @Description: Because the inspector only inspect properties in files,
         *              execute saveSettings method before inspectAll method in inspector.
         *              If there is no error during checking, show message dialog.
         * @Date: 17-12-20 上午10:43
         */

        try {
            saveSettings();
            inspector.inspectAll();
            JOptionPane.showMessageDialog(null, "保存成功！");
            refreshProperties.release();
        } catch (Exception exception) {
            globalProperties.setErrorMessage(exception.toString(), exception);
            globalProperties.setErrorOccured(true);
            JOptionPane.showMessageDialog(null, globalProperties.getErrorMessage());
        }
    }

    private void pushButtonRefresh() {
        /**
         * @Author: wsy
         * @MethodName: pushButtonRefresh
         * @Return: void
         * @Param: []
         * @Description: Refresh the properties and show message dialog.
         * @Date: 17-12-20 上午10:53
         */

        try {
            refresh();
            JOptionPane.showMessageDialog(null, "已恢复为上次保存的参数！");
        } catch (Exception exception) {
            globalProperties.setErrorMessage(exception.toString(), exception);
            globalProperties.setErrorOccured(true);
        }
    }

    private void saveSettings() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: saveSettings
         * @Return: void
         * @Param: []
         * @Description: Save all String in textFields into properties file.
         * @Date: 17-12-20 上午10:59
         */

//        Load the properties.
        SafeProperties properties = new SafeProperties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(inputStream);
        inputStream.close();

//        Set new properties values.
        if (radioButtonAutoImportOn.isSelected()) {
            properties.setProperty("autoImport.swtich", "on");
        } else {
            properties.setProperty("autoImport.swtich", "off");
        }
        properties.setProperty("autoImport.time", textFieldAutoImportTime.getText());
        properties.setProperty("MySQL.host", textFieldMySQLIp.getText());
        properties.setProperty("MySQL.port", textFieldMySQLPort.getText());
        properties.setProperty("MySQL.database", textFieldMySQLDatabase.getText());
        properties.setProperty("MySQL.user", textFieldMySQLUser.getText());
        properties.setProperty("MySQL.password", textFieldMySQLPassword.getText());
        properties.setProperty("copy.twsp.srcPath", textFieldCopyTwspSrcPath.getText());
        properties.setProperty("copy.twsp.destPath", textFieldCopyTwspDestPath.getText());
        properties.setProperty("copy.twsp.fileName", textFieldCopyTwspFileName.getText());
        properties.setProperty("copy.briefing.srcPath", textFieldCopyBriefingSrcPath.getText());
        properties.setProperty("copy.briefing.destPath", textFieldCopyBriefingDestPath.getText());
        properties.setProperty("copy.briefing.fileName", textFieldCopyBriefingFileNaem.getText());
        properties.setProperty("program.twsp.analysePath", textFieldProgramTwspAnalyse.getText());
        properties.setProperty("program.twsp.sqlPath", textFieldProgramTwspSQLPath.getText());
        properties.setProperty("program.briefing.analysePath", textFieldProgramBriefingAnalyse.getText());
        properties.setProperty("program.briefing.sqlPath", textFieldProgramBriefingSQLPath.getText());
        properties.setProperty("program.critical.path", textFieldProgramCritical.getText());
        properties.setProperty("program.reportImportAssistant.path", textFieldProgramReportImportAssistant.getText());

//        Write the properties.
        FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
        properties.store(fileOutputStream, null);
        fileOutputStream.close();
    }

    public void refresh() throws Exception {
        /**
         * @Author: wsy
         * @MethodName: refresh
         * @Return: void
         * @Param: []
         * @Description: Set initial values from environment.properties.
         * @Date: 2017/11/19 14:38
         */

//        Load the properties.
        SafeProperties properties = new SafeProperties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
        properties.load(new InputStreamReader(inputStream, "utf-8"));

//        properties.load(new InputStreamReader(inputStream, "8859_1"));
        inputStream.close();
        if (properties.getProperty("autoImport.swtich").equals("on")) {
            radioButtonAutoImportOn.setSelected(true);
        } else if (properties.getProperty("autoImport.swtich").equals("off")) {
            radioButtonAutoImportOff.setSelected(true);
        }
        textFieldAutoImportTime.setText(properties.getProperty("autoImport.time"));
        textFieldMySQLIp.setText(properties.getProperty("MySQL.host"));
        textFieldMySQLPort.setText(properties.getProperty("MySQL.port"));
        textFieldMySQLDatabase.setText(properties.getProperty("MySQL.database"));
        textFieldMySQLUser.setText(properties.getProperty("MySQL.user"));
        textFieldMySQLPassword.setText(properties.getProperty("MySQL.password"));
        textFieldCopyTwspSrcPath.setText(properties.getProperty("copy.twsp.srcPath"));
        textFieldCopyTwspDestPath.setText(properties.getProperty("copy.twsp.destPath"));
        textFieldCopyTwspFileName.setText(properties.getProperty("copy.twsp.fileName"));
        textFieldCopyBriefingSrcPath.setText(properties.getProperty("copy.briefing.srcPath"));
        textFieldCopyBriefingDestPath.setText(properties.getProperty("copy.briefing.destPath"));
        textFieldCopyBriefingFileNaem.setText(properties.getProperty("copy.briefing.fileName"));
        textFieldProgramTwspAnalyse.setText(properties.getProperty("program.twsp.analysePath"));
        textFieldProgramTwspSQLPath.setText(properties.getProperty("program.twsp.sqlPath"));
        textFieldProgramBriefingAnalyse.setText(properties.getProperty("program.briefing.analysePath"));
        textFieldProgramBriefingSQLPath.setText(properties.getProperty("program.briefing.sqlPath"));
        textFieldProgramCritical.setText(properties.getProperty("program.critical.path"));
        textFieldProgramReportImportAssistant.setText(properties.getProperty("program.reportImportAssistant.path"));
    }

    public void initializeAll() {
        /**
         * @Author: wsy
         * @MethodName: initializeAll
         * @Return: void
         * @Param: []
         * @Description: Initialize all factors.
         * @Date: 17-12-20 上午10:57
         */

        try {
            this.refresh();
            this.initializeFrame();
        } catch (Exception exception) {
            globalProperties.setErrorMessage(exception.toString(), exception);
            globalProperties.setErrorOccured(true);
        }
    }

    private void initializeFrame() {
        /**
         * @Author: wsy
         * @MethodName: initializeFrame
         * @Return: void
         * @Param: []
         * @Description: Initialize frame.
         * @Date: 17-12-21 上午11:19
         */

        Image image = Toolkit.getDefaultToolkit().getImage(iconPath + "\\settingForm.png");
        frame.setIconImage(image);
        frame.setContentPane(this.mainPanel);
        reLocation();
        frame.pack();
        frame.setVisible(false);
    }

    public void reLocation() {
        /**
         * @Author: wsy
         * @MethodName: reLocation
         * @Return: void
         * @Param: []
         * @Description: Set the frame location.
         * @Date: 17-12-21 上午11:18
         */

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 403;
        int frameHeight = 589;

        frame.setLocation((screenWidth - frameWidth) / 2,
                (screenHeight - frameHeight) / 2);
    }

    public void show() throws Exception {
        this.refresh();
        this.reLocation();
        this.frame.setVisible(true);
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
        mainPanel.setLayout(new GridLayoutManager(20, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("自动导入开关");
        panel1.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonAutoImportOn = new JRadioButton();
        Font radioButtonAutoImportOnFont = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, radioButtonAutoImportOn.getFont());
        if (radioButtonAutoImportOnFont != null) radioButtonAutoImportOn.setFont(radioButtonAutoImportOnFont);
        radioButtonAutoImportOn.setText("开启");
        panel1.add(radioButtonAutoImportOn, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonAutoImportOff = new JRadioButton();
        Font radioButtonAutoImportOffFont = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, radioButtonAutoImportOff.getFont());
        if (radioButtonAutoImportOffFont != null) radioButtonAutoImportOff.setFont(radioButtonAutoImportOffFont);
        radioButtonAutoImportOff.setText("关闭");
        panel1.add(radioButtonAutoImportOff, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(18, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(1, 0, 18, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("(重起程序生效)MySQL数据库IP:");
        panel2.add(label2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("twsp文件复制输入路径:");
        panel2.add(label3, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("twsp文件复制输出路径:");
        panel2.add(label4, new GridConstraints(7, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("twsp文件复制输入文件名称:");
        panel2.add(label5, new GridConstraints(8, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("简报文件复制输出路径:");
        panel2.add(label6, new GridConstraints(10, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("简报文件复制输入文件名称:");
        panel2.add(label7, new GridConstraints(11, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("(重起程序生效)MySQL数据库名称:");
        panel2.add(label8, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setText("(重起程序生效)MySQL数据库用户名:");
        panel2.add(label9, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        Font label10Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setText("(重起程序生效)MySQL数据库端口:");
        panel2.add(label10, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        Font label11Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label11.getFont());
        if (label11Font != null) label11.setFont(label11Font);
        label11.setText("(重起程序生效)MySQL数据库密码:");
        panel2.add(label11, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        Font label12Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label12.getFont());
        if (label12Font != null) label12.setFont(label12Font);
        label12.setText("简报解析程序(Python)生成的文件路径:");
        panel2.add(label12, new GridConstraints(15, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        Font label13Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label13.getFont());
        if (label13Font != null) label13.setFont(label13Font);
        label13.setText("简报解析程序(Python)路径及名称:");
        panel2.add(label13, new GridConstraints(14, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        Font label14Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label14.getFont());
        if (label14Font != null) label14.setFont(label14Font);
        label14.setText("twsp解析程序(Python)生成的文件路径:");
        panel2.add(label14, new GridConstraints(13, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        Font label15Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label15.getFont());
        if (label15Font != null) label15.setFont(label15Font);
        label15.setText("twsp解析程序(Python)路径及名称:");
        panel2.add(label15, new GridConstraints(12, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        Font label16Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label16.getFont());
        if (label16Font != null) label16.setFont(label16Font);
        label16.setText("关键路径计算程序(Python)路径及名称:");
        panel2.add(label16, new GridConstraints(16, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        Font label17Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label17.getFont());
        if (label17Font != null) label17.setFont(label17Font);
        label17.setText("日报导入助手(exe)路径:");
        panel2.add(label17, new GridConstraints(17, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        Font label18Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label18.getFont());
        if (label18Font != null) label18.setFont(label18Font);
        label18.setText("简报文件复制输入路径:");
        panel2.add(label18, new GridConstraints(9, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        Font label19Font = this.$$$getFont$$$("YouYuan", Font.BOLD, -1, label19.getFont());
        if (label19Font != null) label19.setFont(label19Font);
        label19.setText("自动更新时间:");
        panel2.add(label19, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel3, new GridConstraints(19, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSave = new JButton();
        Font buttonSaveFont = this.$$$getFont$$$("YouYuan", Font.BOLD, 16, buttonSave.getFont());
        if (buttonSaveFont != null) buttonSave.setFont(buttonSaveFont);
        buttonSave.setText("保存");
        panel3.add(buttonSave, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonRecover = new JButton();
        Font buttonRecoverFont = this.$$$getFont$$$("YouYuan", Font.BOLD, 16, buttonRecover.getFont());
        if (buttonRecoverFont != null) buttonRecover.setFont(buttonRecoverFont);
        buttonRecover.setText("恢复");
        panel3.add(buttonRecover, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldMySQLIp = new JTextField();
        mainPanel.add(textFieldMySQLIp, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldProgramReportImportAssistant = new JTextField();
        textFieldProgramReportImportAssistant.setText("");
        mainPanel.add(textFieldProgramReportImportAssistant, new GridConstraints(18, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldProgramCritical = new JTextField();
        mainPanel.add(textFieldProgramCritical, new GridConstraints(17, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldProgramBriefingSQLPath = new JTextField();
        mainPanel.add(textFieldProgramBriefingSQLPath, new GridConstraints(16, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldProgramBriefingAnalyse = new JTextField();
        mainPanel.add(textFieldProgramBriefingAnalyse, new GridConstraints(15, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldCopyTwspDestPath = new JTextField();
        mainPanel.add(textFieldCopyTwspDestPath, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldCopyTwspSrcPath = new JTextField();
        mainPanel.add(textFieldCopyTwspSrcPath, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldMySQLPassword = new JTextField();
        mainPanel.add(textFieldMySQLPassword, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldMySQLUser = new JTextField();
        mainPanel.add(textFieldMySQLUser, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldMySQLDatabase = new JTextField();
        mainPanel.add(textFieldMySQLDatabase, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldMySQLPort = new JTextField();
        mainPanel.add(textFieldMySQLPort, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldProgramTwspSQLPath = new JTextField();
        mainPanel.add(textFieldProgramTwspSQLPath, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldCopyTwspFileName = new JTextField();
        mainPanel.add(textFieldCopyTwspFileName, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldCopyBriefingSrcPath = new JTextField();
        mainPanel.add(textFieldCopyBriefingSrcPath, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldProgramTwspAnalyse = new JTextField();
        mainPanel.add(textFieldProgramTwspAnalyse, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldCopyBriefingFileNaem = new JTextField();
        mainPanel.add(textFieldCopyBriefingFileNaem, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldCopyBriefingDestPath = new JTextField();
        mainPanel.add(textFieldCopyBriefingDestPath, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldAutoImportTime = new JTextField();
        mainPanel.add(textFieldAutoImportTime, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonAutoImportOn);
        buttonGroup.add(radioButtonAutoImportOff);
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
