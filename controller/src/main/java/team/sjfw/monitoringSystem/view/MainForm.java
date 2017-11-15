package team.sjfw.monitoringSystem.view;

import javax.swing.*;

public class MainForm {

    private JButton button1;
    private JPanel mainPanel;

    public void showFrame() {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
