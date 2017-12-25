package team.sjfw.monitoringSystem.view;

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
    public SettingForm(GlobalProperties inputGlobalProperties,Inspector inputInspector) {
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
        }catch (Exception exception) {
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
        }catch (Exception exception) {
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

    public void refresh()throws Exception {
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

    public void show() throws Exception{
        this.refresh();
        this.reLocation();
        this.frame.setVisible(true);
    }
}
