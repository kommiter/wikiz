package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import static constant.Color.MID_COLOR;

public class RightPanel extends JPanel {
    public RightPanel() {
        // RightPanel.java Properties
        this.setBounds(400, 0, 220, 420);
        this.setLayout(null);
        this.setBackground(java.awt.Color.white);

        // label0
        JLabel label0 = new JLabel("Login Guide");
        label0.setFont(new Font("Arial", Font.BOLD, 14));
        label0.setForeground(MID_COLOR);
        label0.setHorizontalAlignment(SwingConstants.LEFT);
        label0.setBounds(15, 60, 100, 20);
        this.add(label0);

        // imageLabel - wikipedia
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/asset/image/wikipedia.png"));
        Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(65, 90, 100, 100);
        this.add(imageLabel);

        // label1
        JLabel label1 = new JLabel("<html>Welcome to <strong>Wikiz</strong>, Quiz Generator based on Wikipedia!</html>");
        label1.setFont(new Font("Arial", Font.PLAIN, 12));
        label1.setForeground(MID_COLOR);
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setBounds(15, 170, 200, 100);
        this.add(label1);

        // label2
        JLabel label2 = new JLabel("<html>1. Get API KEY in <a href=\"https://platform.openai.com/\" target=\"_blank\">OpenAI Platform</a>  </html>");
        label2.setFont(new Font("Arial", Font.PLAIN, 12));
        label2.setForeground(MID_COLOR);
        label2.setHorizontalAlignment(SwingConstants.LEFT);
        label2.setBounds(15, 200, 200, 100);
        this.add(label2);

        // label2 - link(openai)
        label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://platform.openai.com"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // label3
        JLabel label3 = new JLabel("<html>2. Paste API KEY in input field</html>");
        label3.setFont(new Font("Arial", Font.PLAIN, 12));
        label3.setForeground(MID_COLOR);
        label3.setHorizontalAlignment(SwingConstants.LEFT);
        label3.setBounds(15, 220, 200, 100);
        this.add(label3);

        // label4
        JLabel label4 = new JLabel("<html>3. Test Validation and click Login</html>");
        label4.setFont(new Font("Arial", Font.PLAIN, 12));
        label4.setForeground(MID_COLOR);
        label4.setHorizontalAlignment(SwingConstants.LEFT);
        label4.setBounds(15, 240, 200, 100);
        this.add(label4);
    }
}