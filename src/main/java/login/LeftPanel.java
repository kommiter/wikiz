package login;

import hook.GPTApiManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

import static constant.Color.MAIN_COLOR;
import static constant.Color.SUB_COLOR;

public class LeftPanel extends JPanel {
    private JLabel logoLabel = new JLabel("Wikiz");
    private JLabel APIKEY_Label = new JLabel("GPT API KEY");
    private JTextField APIKEY_Field = new JTextField();
    private JCheckBox APIKEY_CheckBox = new JCheckBox();
    private JLabel APIKEY_CheckBoxLabel = new JLabel("Validation Test");
    private JLabel APIKEY_ErrorMessage = new JLabel();
    public JButton loginButton = new JButton("Login");

    public LeftPanel() {
        // Load API Key
        GPTApiManager.loadApiKey();

        // LeftPanel Properties
        this.setBounds(0, 0, 399, 420);
        this.setLayout(null);
        this.setBackground(java.awt.Color.white);

        // logoLabel
        logoLabel.setFont(new Font("Serif", Font.BOLD, 36));
        logoLabel.setForeground(MAIN_COLOR);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBounds(50, 50, 300, 50); // Centered in left panel

        // APIKEY_Label
        APIKEY_Label.setFont(new Font("Arial", Font.PLAIN, 14));
        APIKEY_Label.setForeground(java.awt.Color.BLACK);
        APIKEY_Label.setHorizontalAlignment(SwingConstants.LEFT);
        APIKEY_Label.setBounds(50, 120, 300, 50);

        // APIKEY_Field
        APIKEY_Field.setText(GPTApiManager.getApiKey());
        APIKEY_Field.setBounds(50, 160, 300, 30);
        APIKEY_Field.setBorder(new LineBorder(SUB_COLOR));
        APIKEY_Field.setForeground(java.awt.Color.BLACK);
        APIKEY_Field.setBackground(java.awt.Color.WHITE);

        // APIKEY_Field - detect editing
        APIKEY_Field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}

            private void handleTextChange() {
                APIKEY_ErrorMessage.setForeground(Color.RED);
                String apiKey = APIKEY_Field.getText().trim();

                APIKEY_CheckBox.setSelected(false);
                loginButton.setEnabled(false);

                if (apiKey.isEmpty()) {
                    APIKEY_ErrorMessage.setText("API KEY cannot be blank");
                    return;
                }

                // Validate API Key format
                if (apiKey.matches("^[a-zA-Z0-9-_]*$")) {
                    APIKEY_ErrorMessage.setText(""); // Clear error message if valid
                    GPTApiManager.saveApiKey(apiKey); // Save the API Key
                } else {
                    APIKEY_ErrorMessage.setText("API KEY must contain only English letters, numbers, or '-'");
                }
            }

        });

        // APIKEY_Checkbox
        APIKEY_CheckBox.setBounds(50, 200, 20, 20);
        APIKEY_CheckBox.setBorder(new LineBorder(SUB_COLOR));

        // APIKEY_CheckboxLabel
        APIKEY_CheckBoxLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        APIKEY_CheckBoxLabel.setBounds(80, 200, 200, 20);

        // APIKEY_ErrorMessage
        APIKEY_ErrorMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        APIKEY_ErrorMessage.setBounds(50, 230, 300, 20);
        APIKEY_ErrorMessage.setForeground(java.awt.Color.RED);

        // APIKEY_CheckBox - Detect Clicking
        APIKEY_CheckBox.addActionListener(e -> {
            String apiKey = GPTApiManager.getApiKey();
            try {
                String response = GPTApiManager.callGPTApi("say no", 10, "example");
                if ("".equals(response)) {
                    APIKEY_CheckBox.setSelected(false);
                    APIKEY_ErrorMessage.setText("API Key validation failed.");
                } else {
                    System.out.println(response);
                    APIKEY_CheckBox.setSelected(true);
                    loginButton.setEnabled(true);
                    APIKEY_ErrorMessage.setText("API Key validated successfully.");
                    APIKEY_ErrorMessage.setForeground(java.awt.Color.green);
                }
            } catch (Exception ex) {
                APIKEY_CheckBox.setSelected(false);
                APIKEY_ErrorMessage.setText("Error connecting to API: " + ex.getMessage());
            }
        });

        // Login button
        loginButton.setBounds(50, 260, 300, 40);
        loginButton.setEnabled(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(java.awt.Color.white);
        loginButton.setForeground(java.awt.Color.BLACK);
        loginButton.setFocusPainted(false);

        // Layout
        this.add(logoLabel);
        this.add(APIKEY_Label);
        this.add(APIKEY_Field);
        this.add(APIKEY_CheckBox);
        this.add(APIKEY_CheckBoxLabel);
        this.add(APIKEY_ErrorMessage);
        this.add(loginButton);
    }
}