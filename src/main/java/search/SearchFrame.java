package search;

import javax.swing.*;
import java.awt.*;

public class SearchFrame {

    public SearchFrame() {
        // create JFrame
        JFrame frame = new JFrame("Wikiz - Wikipedia Search and Archive");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // screen size 80%
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.8);
        int height = (int) (screenSize.height * 0.8);
        frame.setSize(width, height);
        frame.setBackground(Color.white);
        frame.setLocationRelativeTo(null); // center

        // create JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBackground(Color.white);

        // LeftPanel
        LeftPanel leftPanel = new LeftPanel();
        leftPanel.setBackground(Color.white);
        splitPane.setLeftComponent(leftPanel);

        // RightPanel
        RightPanel rightPanel = new RightPanel();
        rightPanel.setBackground(Color.white);
        splitPane.setRightComponent(rightPanel);

        // left 65%, right 35%
        splitPane.setDividerLocation((int) (width * 0.65));
        splitPane.setResizeWeight(0.65);

        // add JSplitPane
        frame.add(splitPane);

        // visible frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SearchFrame();
    }
}