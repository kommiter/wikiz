package hook;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GPTApiManager {
    private static final String API_KEY_FILE = "database/APIKEY.txt";
    private static String apiKey;

    // Load API key from resources

    public static void saveApiKey(String newApiKey) {
        try {
            // Save API Key to file
            File file = new File("src/main/resources/" + API_KEY_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(newApiKey);
            }

            apiKey = newApiKey.trim(); // Update the in-memory API Key
            System.out.println("API Key successfully saved.");
        } catch (IOException e) {
            System.err.println("Error saving API Key: " + e.getMessage());
        }
    }


    public static void loadApiKey() {
        try {
            // read API KEY in resources directory
            InputStream inputStream = new FileInputStream(new File("src/main/resources/" + API_KEY_FILE));
            if (inputStream == null) {
                throw new FileNotFoundException("API Key file not found: " + API_KEY_FILE);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            apiKey = reader.readLine(); // read API KEY in first line
            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new IOException("API Key is empty or invalid.");
            }
            System.out.println("API Key successfully loaded.");
        } catch (IOException e) {
            System.err.println("Error loading API Key: " + e.getMessage());
            apiKey = "";
        }
    }


    // Get API Key
    public static String getApiKey() {
        return apiKey;
    }

    // Call GPT API
    public static String callGPTApi(String prompt, int maxTokens, String type) {
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("API Key is not set. Cannot make API call.");
            return "{\"error\": \"API Key is missing\"}";
        }

        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // create JSON object
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("max_tokens", maxTokens);
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject()
                    .put("role", "user")
                    .put("content", prompt));
            requestBody.put("messages", messages);

            // sending request body
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.toString().getBytes("utf-8"));
            }

            // handling response
            int statusCode = connection.getResponseCode();
            InputStream inputStream = (statusCode >= 200 && statusCode < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }

            // print log
            System.out.println("API Response: " + response.toString());
            return response.toString();
        } catch (IOException e) {
            System.err.println("Error in API call: " + e.getMessage());
            return "{\"error\": \"API call failed due to IOException\"}";
        }
    }
}