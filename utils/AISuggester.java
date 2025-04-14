package utils;

import java.util.*;

public class AISuggester {

    private static final Map<List<String>, String> suggestions = Map.of(
        List.of("fire", "air"), "smoke",
        List.of("earth", "water"), "mud",
        List.of("fire", "water"), "steam"
    );

    public static String getHint(Set<String> discovered) {
        for (Map.Entry<List<String>, String> entry : suggestions.entrySet()) {
            if (discovered.containsAll(entry.getKey()) && !discovered.contains(entry.getValue())) {
                return "🧠 Try combining: " + String.join(" + ", entry.getKey());
            }
        }
        return "🧠 You've discovered many elements! Try random combos!";
    }
}
