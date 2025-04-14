package utils;

import java.io.*;
import java.util.*;

public class LeaderboardManager {
    private static final String FILE = "leaderboard.txt";

    public static void updateLeaderboard(String player, int discoveredCount, int level) {
        Map<String, String> leaderboard = loadLeaderboard();

        leaderboard.put(player, discoveredCount + "," + level);
        saveLeaderboard(leaderboard);
    }

    public static Map<String, String> loadLeaderboard() {
        Map<String, String> leaderboard = new HashMap<>();
        File file = new File(FILE);
        if (!file.exists()) return leaderboard;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                if (split.length == 2) {
                    leaderboard.put(split[0], split[1]); // name: elementsCount,level
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }

    private static void saveLeaderboard(Map<String, String> leaderboard) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (Map.Entry<String, String> entry : leaderboard.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
