package search;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RightPanel extends JPanel {

    private JTextField searchField;
    private JList<SearchResultItem> resultList;
    private DefaultListModel<SearchResultItem> listModel;
    private JButton readDocsButton;
    private int selectedIndex = -1; // current index
    private Timer debounceTimer;

    public RightPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // upper
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = new JLabel("Search in Local");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchLabel.setHorizontalAlignment(SwingConstants.LEFT);
        searchLabel.setPreferredSize(new Dimension(0, 50));
        topPanel.add(searchLabel, BorderLayout.NORTH);

        searchField = new JTextField("");
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(0, 50));
        searchField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        topPanel.add(searchField, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // center
        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultList.setCellRenderer(new CustomListCellRenderer());

        resultList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int newIndex = resultList.getSelectedIndex();
                if (newIndex != selectedIndex) {
                    selectedIndex = newIndex;
                    repaintList();
                    readDocsButton.setEnabled(true);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(resultList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // down
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        readDocsButton = new JButton("Read Docs");
        readDocsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        readDocsButton.setPreferredSize(new Dimension(0, 50));
        readDocsButton.setEnabled(false);
        bottomPanel.add(readDocsButton, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        readDocsButton.addActionListener(e -> openSelectedDoc());

        // add DocumentListener
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                handleTextChange();
            }
        });
    }

    private void handleTextChange() {
        if (debounceTimer != null) {
            debounceTimer.cancel();
        }
        debounceTimer = new Timer();
        debounceTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                performSearch(searchField.getText().trim());
            }
        }, 500);

        // Reset
        selectedIndex = -1;
        repaintList();
    }

    private void performSearch(String keyword) {
        listModel.clear();
        selectedIndex = -1;
        readDocsButton.setEnabled(false);

        if (keyword.isEmpty()) {
            displayAllFiles(); // no search keyword
            return;
        }

        List<File> files = loadFilesFromDatabase();
        for (File file : files) {
            String fileName = file.getName().replace(".json", "");
            if (fileName.contains(keyword)) {
                String title = fileName;
                String description = "/src/main/resources/database/wiki/"+title;
                String imagePath = "src/asset/default_image.png"; // set default image
                listModel.addElement(new SearchResultItem(imagePath, title, description));
            }
        }

        if (listModel.isEmpty()) {
            listModel.addElement(new SearchResultItem(null, "No results found.", ""));
        }
    }

    private void displayAllFiles() {
        List<File> files = loadFilesFromDatabase();
        for (File file : files) {
            String fileName = file.getName().replace(".json", "");
            String title = fileName;
            String description = "/src/main/resources/database/wiki/"+title;
            String imagePath = "src/asset/default_image.png"; // 기본 이미지 설정
            listModel.addElement(new SearchResultItem(imagePath, title, description));
        }
    }

    private List<File> loadFilesFromDatabase() {
        File directory = new File("src/main/resources/database/wiki/");
        List<File> files = new ArrayList<>();
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.getName().endsWith(".json")) {
                    files.add(file);
                }
            }
        }
        return files;
    }

    private void repaintList() {
        resultList.repaint();
    }

    private void openSelectedDoc() {
        if (selectedIndex == -1 || resultList.getSelectedValue() == null) {
            return;
        }

        String selectedTitle = resultList.getSelectedValue().getTitle();

        SwingUtilities.invokeLater(() -> new quiz.QuizFrame(selectedTitle));
    }

    private static class SearchResultItem {
        private final String imagePath;
        private final String title;
        private final String description;

        public SearchResultItem(String imagePath, String title, String description) {
            this.imagePath = imagePath;
            this.title = title;
            this.description = description;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

    private class CustomListCellRenderer extends JPanel implements ListCellRenderer<SearchResultItem> {
        private final JLabel imageLabel = new JLabel();
        private final JLabel titleLabel = new JLabel();
        private final JLabel descriptionLabel = new JLabel();

        public CustomListCellRenderer() {
            setLayout(new BorderLayout(10, 10));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, constant.Color.SUB_COLOR)); // add list divider

            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.setBackground(Color.WHITE);
            textPanel.add(titleLabel);
            textPanel.add(descriptionLabel);

            add(imageLabel, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);

            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends SearchResultItem> list, SearchResultItem value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value.getImagePath() != null) {
                imageLabel.setIcon(new ImageIcon(new ImageIcon(value.getImagePath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            } else {
                imageLabel.setIcon(null);
            }

            titleLabel.setText(value.getTitle());
            descriptionLabel.setText(value.getDescription());

            if (index == selectedIndex) {
                titleLabel.setForeground(constant.Color.MAIN_COLOR);
                descriptionLabel.setForeground(constant.Color.MAIN_COLOR);
            } else {
                titleLabel.setForeground(Color.BLACK);
                descriptionLabel.setForeground(Color.GRAY);
            }

            return this;
        }
    }
}