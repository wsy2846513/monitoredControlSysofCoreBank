package team.sjfw.monitoringSystem.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import team.sjfw.monitoringSystem.controller.GlobalProperties;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Tittle: ProgressForm.java
 * @Author: wsy
 * @Class_name: ProgressForm
 * @Package: team.sjfw.monitoringSystem.view
 * @Description: This form show the progress bar during importing.
 * @Version: V1.0
 * @Date: 2017/12/25 22:27
 */

@Controller
public class ProgressForm implements Runnable {
    private JProgressBar progressBar;
    private JPanel mainPanel;
    private JFrame frame;
    private boolean exit;
    private int REFRESH_MILLISECOND;
    private int percentage;
    private boolean initializeFinishFlag = false;
    private String iconPath;

    private GlobalProperties globalProperties;

    @Autowired
    public ProgressForm(GlobalProperties inputGlobalProperties) {
        globalProperties = inputGlobalProperties;
        iconPath = globalProperties.getIconPath();
        initializeAll();
    }

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

    public boolean isInitializeFinished() {
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
        frame = new JFrame("");
        Image image = Toolkit.getDefaultToolkit().getImage(iconPath + "\\progressForm.png");
        frame.setIconImage(image);
        reLocation();
        frame.setContentPane(this.mainPanel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(false);

//        When push the exit button, it will not exit and show a message dialog.
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

    private void reLocation() {
        /**
         * @Author: wsy
         * @MethodName: reLocation
         * @Return: void
         * @Param: []
         * @Description: Set the frame location
         * @Date: 2017/12/23 21:15
         */

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 328;
        int frameHeight = 267;
        frame.setLocation((screenWidth - frameWidth) / 2,
                (screenHeight - frameHeight) / 2);
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
            frame.setVisible(true);
            setExit(false);
            reLocation();
            progressBar.setValue(0);
            this.initializeFinishFlag = true;
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 5, new Insets(0, 0, 0, 0), -1, -1));
        progressBar = new JProgressBar();
        mainPanel.add(progressBar, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("YouYuan", Font.BOLD, 26, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("当前处理进度");
        label1.setVerticalAlignment(0);
        mainPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 40), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 20), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 15), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(30, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(30, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(30, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(30, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(50, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(50, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
