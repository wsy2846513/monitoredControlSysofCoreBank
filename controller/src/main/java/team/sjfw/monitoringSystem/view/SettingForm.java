package team.sjfw.monitoringSystem.view;

import javax.swing.*;
import java.awt.*;

public class SettingForm {
    private JRadioButton radioButtonAutoImportOn;
    private JRadioButton radioButtonAutoImportOff;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;

    public SettingForm() {

    }

    public void initialize() {
        JFrame frame = new JFrame("SettingForm");
        MainForm mainForm = new MainForm();
//        mainForm.latestDate.set
        frame.setContentPane(new SettingForm().mainPanel);
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
