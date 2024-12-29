package login;

import search.SearchFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame {
    public static void main(String[] args) {
        // LoginFrame
        JFrame frame = new JFrame("Wikiz - Quiz Generator based on Wikipedia");
        ImageIcon logoIcon = new ImageIcon("src/asset/image/wikipedia.png");
        frame.setIconImage(logoIcon.getImage());
        frame.setSize(620, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setBackground(Color.white);
        frame.setLocationRelativeTo(null);

        // LeftPanel
        LeftPanel leftPanel = new LeftPanel();
        frame.add(leftPanel);

        // LeftPanel - LoginFrame
        leftPanel.loginButton.addActionListener(e -> {
            System.out.println("hello");
            JOptionPane.showMessageDialog(frame, "Login successful!");
            // Open SearchFrame
            SwingUtilities.invokeLater(SearchFrame::new); // Open new frame

            // Dispose LoginFrame
            frame.dispose();
        });

        // RightPanel.java
        RightPanel rightPanel = new RightPanel();
        frame.add(rightPanel);

        // Vertical divider
        JPanel verticalDivider = new JPanel();
        verticalDivider.setBounds(400, 0, 1, 420);
        verticalDivider.setBackground(Color.BLACK);
        verticalDivider.setOpaque(true);
        frame.add(verticalDivider);

        // show Frame
        frame.setVisible(true);
    }
}