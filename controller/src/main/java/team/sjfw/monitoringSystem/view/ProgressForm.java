package team.sjfw.monitoringSystem.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import team.sjfw.monitoringSystem.controller.GlobalProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Controller
public class ProgressForm implements Runnable {
    private JProgressBar progressBar;
    private JPanel mainPanel;
    private JFrame frame;
    private boolean exit;
    private int REFRESH_MILLISECOND;
    private int percentage;
    private boolean initializeFinishFlag = false;

    @Autowired
    private GlobalProperties globalProperties;

    private void refresh() {
        /**
         * @Author: wsy
         * @MethodName: refresh
         * @Return: void
         * @Param: []
         * @Description: Refresh the progress bar.
         *              When the percentage in global properties is 100, exit and show message.
         * @Date: 17-12-18 下午8:12
         */
        percentage = globalProperties.getPercentage();
        progressBar.setValue(percentage);
        if (percentage >= 100) {
            stopShowProgress();
        }
    }

    private void stopShowProgress() {
        /**
         * @Author: wsy
         * @MethodName: stopShowProgress
         * @Return: void
         * @Param: []
         * @Description: Exit and show message.
         * @Date: 17-12-18 下午8:18
         */

        JOptionPane.showMessageDialog(null, "已完成!");
        setExit(true);
    }

    public boolean isInitializeFinished(){
        /**
         * @Author: wsy
         * @MethodName: isInitializeFinished
         * @Return: boolean
         * @Param: []
         * @Description: Return true if initialization has finished, otherwise return false.
         * @Date: 17-12-19 下午7:01
         */
        return initializeFinishFlag;
    }

    public void initializeAll() {
        /**
         * @Author: wsy
         * @MethodName: initializeAll
         * @Return: void
         * @Param: []
         * @Description: Initialize all factors.
         * @Date: 17-12-18 下午8:18
         */
        this.initializePanel();
        this.initializeForm();
        this.refresh();
        this.initializeFinishFlag = true;
    }

    private void initializePanel() {
        setExit(false);
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
                JOptionPane.showMessageDialog(null, "请等待数据处理结束！");
            }
        });

    }

    public synchronized void setExit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void run() {
        /**
         * @Author: wsy
         * @MethodName: run
         * @Return: void
         * @Param: []
         * @Description: Initialize the progress form and show the progress bar.
         * @Date: 17-12-19 下午7:03
         */
        try {
            initializeAll();
            while (!exit) {
                Thread.sleep(REFRESH_MILLISECOND);
                refresh();
            }
        } catch (Exception exception) {
            globalProperties.setErrorOccured(true);
            globalProperties.setErrorMessage(exception.toString(), exception);
        } finally {
            this.initializeFinishFlag = false;
            frame.dispose();
        }
    }
}
