package team.sjfw.monitoringSystem.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wsy.tools.SafeProperties;
import team.sjfw.monitoringSystem.controller.GlobalProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.Semaphore;

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
    private Semaphore refreshProperties;
    private MainForm mainForm;

    @Autowired
    public SettingForm(GlobalProperties globalProperties) {
        frame = new JFrame("SettingForm");
        propertiesFilePath = globalProperties.getPropertiesFilePath();
        refreshProperties = globalProperties.getRefreshProperties();
        refresh();

        buttonRecover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (refresh()) {
                    JOptionPane.showMessageDialog(null, "已恢复为上次保存的参数！");
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkInput()) {
                    if (saveSettings()) {
                        JOptionPane.showMessageDialog(null, "保存成功！");
                        refreshProperties.release();
                    }
                }
            }
        });
    }

    public void setMainForm(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    private boolean checkInput() {
        return true;
    }

    private boolean saveSettings() {
        try {
//            Load the properties.
            SafeProperties properties = new SafeProperties();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(inputStream);
            inputStream.close();

//            Set new properties values.
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

//            Write the properties.
            FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
            properties.store(fileOutputStream, null);
            fileOutputStream.close();
//            throw new Exception();
            return true;
        } catch (Exception exception) {
//            是否需要日志处理?
            exception.printStackTrace();
            return false;
        }
    }

    public boolean refresh() {
        /**
         * @Author: wsy
         * @MethodName: refresh
         * @Return: void
         * @Param: []
         * @Description: Set initial values from environment.properties.
         * @Date: 2017/11/19 14:38
         */
        try {
//            Load the properties.
            SafeProperties properties = new SafeProperties();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(new InputStreamReader(inputStream, "utf-8"));
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
            return true;
        } catch (Exception exception) {
//            是否需要日志处理？
            exception.printStackTrace();
            return false;
        }


    }

    public void initializeAll() {
        this.refresh();
        this.initializeFrame();
    }

    private void initializeFrame() {
        frame.setContentPane(this.mainPanel);

        Dimension frameSize = frame.getSize();
//        Dimension frameSize = mainForm.mainPanel.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
//        int frameWidth = (int) frameSize.getWidth();
//        int frameHeight = (int) frameSize.getHeight();

//        System.out.println("SW=" + screenWidth);
//        System.out.println("SH=" + screenHeight);
//        System.out.println("FW=" + frameWidth);
//        System.out.println("FH=" + frameHeight);
//        frame.setLocation((screenWidth - frameWidth) / 3,
//                (screenHeight - frameHeight) / 4);
        frame.setLocation(screenWidth / 3, screenHeight / 4);
        frame.setResizable(false);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        编写替代setDefaultCloseOperation的方法,从而实现关闭配置窗口时显示主窗口
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                mainForm.refresh();
                mainForm.setFrameVisible(true);
            }
        });
        frame.pack();
        frame.setVisible(true);

    }
}
