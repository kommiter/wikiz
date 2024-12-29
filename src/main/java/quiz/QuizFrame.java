package quiz;

import javax.swing.*;
import java.awt.*;

public class QuizFrame {

    public QuizFrame(String keyword) {
        JFrame frame = new JFrame("Quiz Generator - " + keyword);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.8);
        int height = (int) (screenSize.height * 0.8);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // create splitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation((int) (frame.getWidth() * 0.65));
        splitPane.setResizeWeight(0.65);

        // left
        LeftPanel leftPanel = new LeftPanel(keyword);
        splitPane.setLeftComponent(leftPanel);

        // right
        RightPanel rightPanel = new RightPanel(keyword);
        splitPane.setRightComponent(rightPanel);

        // add frame
        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new QuizFrame("example"); // for test
    }
}