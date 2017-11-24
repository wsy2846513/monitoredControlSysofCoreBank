package team.sjfw.monitoringSystem.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wsy.tools.SafeProperties;
import team.sjfw.monitoringSystem.controller.GlobalProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    private MainForm thisObject;

    @Autowired
    private SettingForm settingForm;

    @Autowired
    public MainForm(GlobalProperties globalProperties) {

//        this.environment = e;
        frame = new JFrame("MainForm");
        this.propertiesFilePath = globalProperties.getPropertiesFilePath();

        this.refresh();

        buttonSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                settingForm.setMainForm(thisObject);
                settingForm.initializeAll();
            }
        });
        buttonStartImport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("start import !");
            }
        });
    }

    public boolean refresh() {
        try {
            SafeProperties properties = new SafeProperties();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesFilePath));
            properties.load(new InputStreamReader(inputStream, "utf-8"));
            inputStream.close();
            this.labelLatestDate.setText(properties.getProperty("latest.date"));
            if (properties.getProperty("autoImport.swtich").equals("on")){
                this.labelAutoSwitch.setForeground(Color.green);
                this.labelAutoSwitch.setText("已开启");
            }else if (properties.getProperty("autoImport.swtich").equals("off")){
                this.labelAutoSwitch.setForeground(Color.red);
                this.labelAutoSwitch.setText("已关闭");
            }
            this.labelAutoImportTime.setText(properties.getProperty("autoImport.time"));
            this.textFieldStartDate.setText(properties.getProperty("start.date"));
            this.textFieldEndDate.setText(properties.getProperty("end.date"));
            return true;

        } catch (Exception exception) {
//            是否需要日志处理？
            exception.printStackTrace();
            return false;
        }
    }

    public void initializeAll() {
        this.setThisObject(this);
        this.initializeFrame();
    }

    private void initializeFrame(){
        frame.setContentPane(this.mainPanel);

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
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setVisible(boolean flag){
        /**
         * @Author: wsy
         * @MethodName: setVisible
         * @Return: void
         * @Param: [flag]
         * @Description: Show the frame.
         * @Date: 17-11-24 上午12:35
         */
        frame.setVisible(flag);
    }

    private void setThisObject(MainForm thisObject) {
        this.thisObject = thisObject;
    }
}
