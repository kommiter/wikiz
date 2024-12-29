package hook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsManager {
    private static final String SETTINGS_FILE = "src/database/gpt_settings.txt";
    private static final Map<String, String> settings = new HashMap<>();

    // Load settings from file
    public static void loadSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue; // Skip comments or empty lines
                String[] keyValue = line.split("=", 2);
                if (keyValue.length == 2) {
                    settings.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading settings: " + e.getMessage());
        }
    }

    // Save settings to file
    public static void saveSettings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SETTINGS_FILE))) {
            for (Map.Entry<String, String> entry : settings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    // Get a setting value
    public static String getSetting(String key, String defaultValue) {
        return settings.getOrDefault(key, defaultValue);
    }

    // Set a setting value
    public static void setSetting(String key, String value) {
        settings.put(key, value);
    }
}