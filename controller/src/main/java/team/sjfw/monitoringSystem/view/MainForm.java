package team.sjfw.monitoringSystem.view;


import org.springframework.core.env.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {

    private JPanel mainPanel;
    private JLabel latestDate;
    private JButton buttonStartImport;
    private JButton buttonSet;
    private JTextField textFieldEndDate;
    private JTextField textFieldStartDate;
    private Environment environment;

    public MainForm(Environment e) {

        this.environment = e;
        System.out.println("mainform : " + environment.getProperty("MySQL.host"));

        buttonSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingForm settingForm = new SettingForm(environment);
                settingForm.initialize();
            }
        });
        buttonStartImport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("start import !");
            }
        });
    }

    public void initialize() {
        MenuBar menuBar = new MenuBar();
        MenuItem setting = new MenuItem("设置");
        Menu menu = new Menu("菜单");

        menu.add(setting);
        menuBar.add(menu);

        JFrame frame = new JFrame("MainForm");
        MainForm mainForm = new MainForm(environment);
//        mainForm.latestDate.set
//        frame.setContentPane(mainForm.mainPanel);
        frame.setContentPane(this.mainPanel);
//        frame.setContentPane(new MainForm().mainPanel);
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
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        frame.setMenuBar(menuBar);


    }
}
