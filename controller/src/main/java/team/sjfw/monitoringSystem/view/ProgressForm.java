package team.sjfw.monitoringSystem.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import team.sjfw.monitoringSystem.controller.GlobalProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

@Controller
public class ProgressForm implements Runnable {
    private JProgressBar progressBar;
    private JPanel mainPanel;
    private JFrame frame;
    private Semaphore openProgressForm;
    private boolean exit;
    private int REFRESH_MILLISECOND;

    private int percentage;

    @Autowired
    private GlobalProperties globalProperties;

    private void refresh() {
        percentage = globalProperties.getPercentage();
        progressBar.setValue(percentage);
        if (percentage >= 100) {
            stopShowProgress();
        }
    }
    private void stopShowProgress() {
        exit = true;
        JOptionPane.showMessageDialog(null, "已完成!");
        frame.setVisible(false);
    }

    public void initializeAll() {
        this.initializePanel();
        this.initializeForm();
        this.refresh();
    }

    private void initializePanel() {
        exit = false;
        openProgressForm = globalProperties.getOpenProgressForm();
        REFRESH_MILLISECOND = globalProperties.getProgressFormRefreshMillisecond();
        progressBar.setStringPainted(true);
        progressBar.setBorderPainted(true);
    }

    private void initializeForm() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        frame = new JFrame("ProgressForm");
        frame.setLocation(screenWidth / 3, screenHeight / 3);
        frame.setContentPane(this.mainPanel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null, "请等待数据梳理结束！");
            }
        });

    }

    public synchronized void setExit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void run() {
        try {
            initializeAll();
            while (!exit) {
                Thread.sleep(REFRESH_MILLISECOND);
                refresh();
            }
        } catch (Exception exception) {
//            是否需要日志？
            exception.printStackTrace();
        }
    }
}
