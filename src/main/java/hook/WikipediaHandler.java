package hook;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WikipediaHandler {

    private static final String DATABASE_DIR = "src/main/resources/database/wiki/";


    // save in json file
    public static void fetchSave(String title) {
        try {
            // api call url (eng)
            String apiUrl = String.format(
                    "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext&format=json&titles=%s",
                    title.replace(" ", "%20")
            );

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            // save in file
            saveDoc(title, response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // saving document content in json file
    private static void saveDoc(String title, String jsonResponse) {
        File file = new File(DATABASE_DIR + title + ".json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<SearchResult> fetchSearchResults(String keyword) {
        try {
            // Wikipedia Search API 호출 URL
            String apiUrl = String.format(
                    "https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=%s&format=json",
                    keyword.replace(" ", "%20")
            );

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP Error: " + statusCode);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            // return result
            JSONObject json = new JSONObject(response.toString());
            JSONArray searchArray = json.getJSONObject("query").getJSONArray("search");

            List<SearchResult> results = new ArrayList<>();
            for (int i = 0; i < searchArray.length(); i++) {
                JSONObject result = searchArray.getJSONObject(i);
                String title = result.getString("title");
                String snippet = result.getString("snippet").replaceAll("<[^>]+>", ""); // removing html tag
                results.add(new SearchResult(title, snippet));
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch search results from Wikipedia.");
        }
    }

    // object
    public static class SearchResult {
        private final String title;
        private final String description;

        public SearchResult(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}