package team.sjfw.monitoringSystem.view;

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

    public MainForm() {

        buttonSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingForm settingForm = new SettingForm();
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
        MainForm mainForm = new MainForm();
//        mainForm.latestDate.set
        frame.setContentPane(new MainForm().mainPanel);
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
