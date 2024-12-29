package quiz;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.json.JSONObject;

public class LeftPanel extends JPanel {

    public LeftPanel(String keyword) {
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");

        String htmlContent = loadAndFormatDocumentContent(keyword);
        editorPane.setText(htmlContent);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        add(scrollPane, BorderLayout.CENTER);
    }

    private String loadAndFormatDocumentContent(String keyword) {
        File file = new File("src/main/resources/database/wiki/" + keyword + ".json");
        if (!file.exists()) {
            return "<html><body><h1>Document Not Found</h1></body></html>";
        }

        try {
            String jsonString = Files.readString(file.toPath());
            JSONObject json = new JSONObject(jsonString);
            JSONObject pages = json.getJSONObject("query").getJSONObject("pages");

            Optional<String> firstKey = pages.keySet().stream().findFirst();
            if (firstKey.isPresent()) {
                JSONObject page = pages.getJSONObject(firstKey.get());
                String title = page.optString("title", "No Title");
                String pageId = page.optString("pageid", "Unknown ID");
                String extract = page.optString("extract", "No Content");

                // create HTML
                return String.format(
                        """
                        <html>
                        <body style='font-family:Arial, sans-serif; line-height:1.6;'>
                            <h1 style='color:#2e6eb8;'>%s</h1>
                            <p><strong>Page ID:</strong> %s</p>
                            <hr>
                            <div>%s</div>
                        </body>
                        </html>
                        """,
                        title, pageId, extract.replace("\n", "<br>")
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "<html><body><h1>Error Loading Document</h1></body></html>";
    }
}