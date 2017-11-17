package team.sjfw.monitoringSystem.view;

import org.springframework.core.env.Environment;

import javax.swing.*;
import java.awt.*;

public class SettingForm {
    private JRadioButton radioButtonAutoImportOn;
    private JRadioButton radioButtonAutoImportOff;
    private JPanel mainPanel;
    private JTextField textFieldMySQLIp;
    private JTextField textField2;
    private JPanel textFiledPanel;
    private JTextField textFieldMySQLPort;
    private JTextField textFieldMySQLDatabase;
    private JTextField textFieldMySQLUser;
    private JTextField textFieldMySQLPassword;
    private JTextField textFieldCopyTwspSrcPath;
    private JButton 保存Button;
    private JButton 回复Button;

    Environment environment;

    public SettingForm(Environment e) {
        environment = e;

        this.refresh();
    }

    public void refresh(){
        textFieldMySQLIp.setText(environment.getProperty("MySQL.host"));
    }

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

        int screenWidth = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();
        int frameWidth = (int)frameSize.getWidth();
        int frameHeight = (int)frameSize.getHeight();

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
