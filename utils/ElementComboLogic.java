package utils;

import java.io.*;
import java.util.*;

public class ElementComboLogic {

    private static final String FILE = "combinations.txt";

    private static final Map<List<String>, String> comboMap = new HashMap<>();

    static {
        loadCombinations();
    }

    private static void loadCombinations() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Format: fire+water=steam
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String[] elements = parts[0].split("\\+");
                    List<String> key = Arrays.asList(elements[0].trim(), elements[1].trim());
                    Collections.sort(key);
                    comboMap.put(key, parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCombination(List<String> elements) {
        if (elements.size() < 2) return null;
        List<String> key = new ArrayList<>(elements.subList(0, 2));
        Collections.sort(key);
        return comboMap.getOrDefault(key, null);
    }
}
