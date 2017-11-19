package team.sjfw.monitoringSystem.view;

import org.springframework.core.env.Environment;
import pers.wsy.tools.SafeProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

public class SettingForm {
    private JRadioButton radioButtonAutoImportOn;
    private JRadioButton radioButtonAutoImportOff;
    private JPanel mainPanel;
    private JTextField textFieldMySQLIp;
    private JTextField textFieldProgramReportImportAssistant;
    private JPanel textFiledPanel;
    private JTextField textFieldMySQLPort;
    private JTextField textFieldMySQLDatabase;
    private JTextField textFieldMySQLUser;
    private JTextField textFieldMySQLPassword;
    private JTextField textFieldCopyTwspSrcPath;
    private JButton 保存Button;
    private JButton 恢复Button;
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

    Environment environment;
    String propertiesFilePath = ".\\src\\main\\resources\\environment.properties";
    //    String propertiesFilePath = this.getClass().getClassLoader().getResourceAsStream("environment.properties");
    String propertiesFileName = "environment.properties";

    public SettingForm(Environment env) {
        environment = env;

        refresh();
        恢复Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        保存Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkInput()) {
                    saveSettings();
                }
            }
        });
    }

    private boolean checkInput() {
        return true;
    }

    //    private void saveSettings(){
//
//        Properties properties = new Properties();
//        try {
//            System.out.println("save Settings");
//            FileInputStream testIn = new FileInputStream(".\\src\\main\\resources\\environment.properties");
//            System.out.println("111111111111");
//
////            加载配置文件
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(".\\src\\main\\resources\\environment.properties"));
//            properties.load(inputStream);
//            inputStream.close();
//
//            System.out.println(properties.getProperty("MySQL.host"));
//
////            保存新配置到文件中
//            FileOutputStream fileOutputStream = new FileOutputStream(".\\src\\main\\resources\\en001.properties");//true表示追加打开
//            properties.setProperty("MySQL.host",textFieldMySQLIp.getText());
//
//
//            properties.store(fileOutputStream,"test-1");
//            fileOutputStream.close();
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
    private void saveSettings() {

        SafeProperties properties = new SafeProperties();

        try {
//            加载配置文件
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(inputStream);
            inputStream.close();

            System.out.println("last value : " + properties.getProperty("MySQL.host"));
            System.out.println("new value : " + textFieldMySQLIp.getText());

//            保存新配置到文件中
            FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
            properties.setProperty("MySQL.host", textFieldMySQLIp.getText());


            properties.store(fileOutputStream, "");
            fileOutputStream.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void refresh() {
        /**
         * @Author: wsy
         * @MethodName: refresh
         * @Return: void
         * @Param: []
         * @Description: Set initial values from environment.properties.
         * @Date: 2017/11/19 14:38
         */
        SafeProperties properties = new SafeProperties();
//        Properties properties = new SafeProperties();
        try {

            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
//            properties.load(inputStream);
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
            textFieldProgramReportImportAssistant.setText(properties.getProperty("program.reportImportAssistant"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
//
//    public void refresh() {
//        /**
//         * @Author: wsy
//         * @MethodName: refresh
//         * @Return: void
//         * @Param: []
//         * @Description:  Set initial values from environment.properties.
//         * @Date: 2017/11/19 14:38
//         */
//
//        if (environment.getProperty("autoImport.swtich").equals("on")) {
//            radioButtonAutoImportOn.setSelected(true);
//        } else if (environment.getProperty("autoImport.swtich").equals("off")) {
//            radioButtonAutoImportOff.setSelected(true);
//        }
//        textFieldAutoImportTime.setText(environment.getProperty("autoImport.time"));
//        textFieldMySQLIp.setText(environment.getProperty("MySQL.host"));
//        textFieldMySQLPort.setText(environment.getProperty("MySQL.port"));
//        textFieldMySQLDatabase.setText(environment.getProperty("MySQL.database"));
//        textFieldMySQLUser.setText(environment.getProperty("MySQL.user"));
//        textFieldMySQLPassword.setText(environment.getProperty("MySQL.password"));
//        textFieldCopyTwspSrcPath.setText(environment.getProperty("copy.twsp.srcPath"));
//        textFieldCopyTwspDestPath.setText(environment.getProperty("copy.twsp.destPath"));
//        textFieldCopyTwspFileName.setText(environment.getProperty("copy.twsp.fileName"));
//        textFieldCopyBriefingSrcPath.setText(environment.getProperty("copy.briefing.srcPath"));
//        textFieldCopyBriefingDestPath.setText(environment.getProperty("copy.briefing.destPath"));
//        textFieldCopyBriefingFileNaem.setText(environment.getProperty("copy.briefing.fileName"));
//        textFieldProgramTwspAnalyse.setText(environment.getProperty("program.twsp.analysePath"));
//        textFieldProgramTwspSQLPath.setText(environment.getProperty("program.twsp.sqlPath"));
//        textFieldProgramBriefingAnalyse.setText(environment.getProperty("program.briefing.analysePath"));
//        textFieldProgramBriefingSQLPath.setText(environment.getProperty("program.briefing.sqlPath"));
//        textFieldProgramCritical.setText(environment.getProperty("program.critical.path"));
//        textFieldProgramReportImportAssistant.setText(environment.getProperty("program.reportImportAssistant"));
//
//    }

    public void initialize() {
//        this.refresh();
        JFrame frame = new JFrame("SettingForm");
//        SettingForm settingForm = new SettingForm();
//        settingForm.refresh();
//        settingForm.testScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        mainForm.latestDate.set
//        frame.setContentPane(new SettingForm().mainPanel);
        frame.setContentPane(this.mainPanel);
//        frame.setBounds(0,0,500,500);

        Dimension frameSize = frame.getSize();
//        Dimension frameSize = mainForm.mainPanel.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = (int) frameSize.getWidth();
        int frameHeight = (int) frameSize.getHeight();

        System.out.println("SW=" + screenWidth);
        System.out.println("SH=" + screenHeight);
        System.out.println("FW=" + frameWidth);
        System.out.println("FH=" + frameHeight);
        frame.setLocation((screenWidth - frameWidth) / 3,
                (screenHeight - frameHeight) / 3);
//        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
