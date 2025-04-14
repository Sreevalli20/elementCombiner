package utils;

import java.io.*;
import java.util.*;

public class ProfileManager {

    private static final String FILE = "users.txt";

    public static void saveProfile(String name, Set<String> elements) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writer.write(name + ":" + String.join(",", elements));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> loadProfile(String name) {
        Set<String> elements = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(name + ":")) {
                    String[] parts = line.split(":");
                    if (parts.length > 1) {
                        elements.addAll(Arrays.asList(parts[1].split(",")));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }
}
