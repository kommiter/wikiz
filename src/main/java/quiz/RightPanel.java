package quiz;

import javax.swing.*;
import java.awt.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class RightPanel extends JPanel {

    private JTextArea quizTextArea;
    private JButton generateQuizButton;
    private ButtonGroup optionsGroup;
    private JPanel optionsPanel;
    private JLabel feedbackLabel;

    public RightPanel(String documentContent) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Upper panel to display the question
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBackground(Color.WHITE);
        quizTextArea = new JTextArea("");
        quizTextArea.setEditable(false); // Read-only
        quizTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        quizTextArea.setLineWrap(true);
        quizTextArea.setWrapStyleWord(true);
        quizTextArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Add border
        questionPanel.add(new JScrollPane(quizTextArea), BorderLayout.CENTER);

        // Panel for options
        optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Label to display feedback for correct/incorrect answers
        feedbackLabel = new JLabel("", JLabel.LEFT);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setForeground(Color.BLUE);

        // Button to generate a quiz
        generateQuizButton = new JButton("Generate Quiz");
        generateQuizButton.setFont(new Font("Arial", Font.BOLD, 16));
        generateQuizButton.setPreferredSize(new Dimension(0, 50));
        generateQuizButton.addActionListener(e -> generateQuiz(documentContent));

        // Combine panels into a vertical layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Add panels to the content panel
        contentPanel.add(questionPanel);
        contentPanel.add(Box.createVerticalStrut(10)); // Add spacing
        contentPanel.add(optionsPanel);
        contentPanel.add(Box.createVerticalStrut(10)); // Add spacing
        contentPanel.add(feedbackLabel);
        add(contentPanel, BorderLayout.CENTER);

        // Add the button at the bottom
        add(generateQuizButton, BorderLayout.SOUTH);
    }

    private void generateQuiz(String documentContent) {
        try {
            // GPT API request to generate a quiz
            String prompt = String.format(
                    "Create a 4-option multiple-choice quiz from the following content:\n\n%s\n\n" +
                            "Format as:\n" +
                            "{ \"question\": \"...\", \"options\": [\"A\", \"B\", \"C\", \"D\"], \"answer\": \"...\" }",
                    documentContent
            );

            String response = hook.GPTApiManager.callGPTApi(prompt, 200, "quiz");

            // Process the JSON response
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.has("choices")) {
                JSONArray choices = jsonResponse.getJSONArray("choices");
                if (choices.length() > 0) {
                    String completion = choices.getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");

                    // Extract and process quiz content
                    processQuizContent(completion);
                    return;
                }
            }

            // If no valid data is found in the response
            quizTextArea.setText("Error: No quiz data found in the response.");
        } catch (Exception ex) {
            ex.printStackTrace();
            quizTextArea.setText("Error generating quiz. Please try again.");
        }
    }

    private void processQuizContent(String content) {
        try {
            // Extract JSON data from the response
            String jsonData = extractJson(content);
            JSONObject quizData = new JSONObject(jsonData);

            // Extract question, options, and the correct answer
            String question = quizData.getString("question");
            JSONArray options = quizData.getJSONArray("options");
            String correctAnswer = quizData.getString("answer");

            // Update the UI with the quiz data
            updateQuizUI(question, options, correctAnswer);
        } catch (Exception e) {
            e.printStackTrace();
            quizTextArea.setText("Error processing quiz content. Please try again.");
        }
    }

    private String extractJson(String content) {
        // Detect the start and end of the JSON
        int jsonStart = content.indexOf("{");
        int jsonEnd = content.lastIndexOf("}");
        if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
            return content.substring(jsonStart, jsonEnd + 1).trim();
        } else {
            throw new RuntimeException("No valid JSON found in the response content.");
        }
    }

    private void updateQuizUI(String question, JSONArray options, String correctAnswer) {
        // Set the question text
        quizTextArea.setText(question);

        // Clear existing options
        optionsPanel.removeAll();
        optionsGroup = new ButtonGroup();

        // Add new options
        for (int i = 0; i < options.length(); i++) {
            String optionText = options.getString(i);
            JRadioButton optionButton = new JRadioButton(optionText);
            optionButton.setFont(new Font("Arial", Font.PLAIN, 14));
            optionButton.setBackground(Color.WHITE); // Set background color
            optionsGroup.add(optionButton);

            // Event to check the selected answer
            optionButton.addActionListener(e -> {
                if (optionText.equals(correctAnswer)) {
                    feedbackLabel.setText("Correct! 🎉");
                    feedbackLabel.setForeground(Color.GREEN);
                } else {
                    feedbackLabel.setText("Wrong! The correct answer is: " + correctAnswer);
                    feedbackLabel.setForeground(Color.RED);
                }
            });

            optionsPanel.add(optionButton);
        }

        // Update the layout
        revalidate();
        repaint();
    }
}