import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import utils.AISuggester;
import utils.ElementComboLogic;
import utils.ProfileManager;
import utils.LeaderboardManager;

public class ElementCombinerGame extends JFrame {
    private DefaultListModel<String> discoveredModel = new DefaultListModel<>();
    private JTextArea suggestionArea = new JTextArea(15, 30);
    private JTextField combineField = new JTextField(20);
    private JComboBox<String> avatarBox = new JComboBox<>(new String[]{"🧙", "🧝", "🧛", "🤖", "👽"});
    private JLabel levelLabel = new JLabel("Level: 1");
    private String currentPlayer = "Player1";
    private Set<String> discovered = new HashSet<>(java.util.List.of("fire", "water", "earth", "air"));
    private Map<String, Set<String>> profiles = new HashMap<>();

    public ElementCombinerGame() {
        super("🌟 Element Combiner Game - Advanced Edition 🌟");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 600);
        setLayout(new BorderLayout());

        profiles.put(currentPlayer, discovered);

        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(new JLabel("Avatar:"));
        topPanel.add(avatarBox);
        topPanel.add(Box.createHorizontalStrut(30));
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(levelLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        // Left: Discovered Elements
        JPanel leftPanel = new JPanel(new BorderLayout());
        JList<String> discoveredList = new JList<>(discoveredModel);
        discoveredList.setFont(new Font("Serif", Font.BOLD, 16));
        discoveredModel.addAll(discovered);
        leftPanel.add(new JLabel("✅ Discovered Elements:"), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(discoveredList), BorderLayout.CENTER);
        centerPanel.add(leftPanel);

        // Right: AI Suggestion Area
        JPanel rightPanel = new JPanel(new BorderLayout());
        suggestionArea.setEditable(false);
        suggestionArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        suggestionArea.setBackground(new Color(240, 248, 255));
        suggestionArea.setLineWrap(true);
        rightPanel.add(new JLabel("🎯 Achievements & AI Suggestions:"), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(suggestionArea), BorderLayout.CENTER);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(224, 255, 255));
        bottomPanel.add(new JLabel("Combine (e.g., fire+air):"));
        bottomPanel.add(combineField);

        JButton combineBtn = new JButton("🔥 Combine");
        combineBtn.addActionListener(e -> {
            String input = combineField.getText().toLowerCase().trim();
            if (!input.contains("+")) {
                suggestionArea.append("❌ Use format like fire+air!\n");
                return;
            }
            String[] parts = input.split("\\+");
            List<String> elements = Arrays.asList(parts);
            String result = ElementComboLogic.getCombination(elements);
            if (result != null && !discovered.contains(result)) {
                discovered.add(result);
                discoveredModel.addElement(result);
                profiles.put(currentPlayer, discovered);
                suggestionArea.append("✨ New Element Discovered: " + result + "\n");
                int level = 1 + discovered.size() / 5;
                levelLabel.setText("Level: " + level);
                LeaderboardManager.updateLeaderboard(currentPlayer, discovered.size(), level);
            } else {
                suggestionArea.append("⚠️ Already discovered or invalid!\n");
            }
            combineField.setText("");
        });
        bottomPanel.add(combineBtn);

        JButton saveBtn = new JButton("💾 Save");
        saveBtn.addActionListener(e -> ProfileManager.saveProfile(currentPlayer, discovered));
        bottomPanel.add(saveBtn);

        JButton loadBtn = new JButton("📂 Load");
        loadBtn.addActionListener(e -> {
            discovered = ProfileManager.loadProfile(currentPlayer);
            discoveredModel.clear();
            discoveredModel.addAll(discovered);
            profiles.put(currentPlayer, discovered);
            suggestionArea.setText(AISuggester.getHint(discovered));
        });
        bottomPanel.add(loadBtn);

        JCheckBox autoHint = new JCheckBox("🎲 Auto Suggest");
        autoHint.addActionListener(e -> {
            if (autoHint.isSelected()) {
                suggestionArea.setText(AISuggester.getHint(discovered));
            }
        });
        bottomPanel.add(autoHint);

        JButton leaderboardBtn = new JButton("🏅 View Leaderboard");
        leaderboardBtn.addActionListener(e2 -> {
            Map<String, String> lb = LeaderboardManager.loadLeaderboard();
            StringBuilder msg = new StringBuilder("🏆 Leaderboard:\n\n");
            lb.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(
                    Integer.parseInt(e2.getValue().split(",")[0]),
                    Integer.parseInt(e1.getValue().split(",")[0])
                ))
                .forEach(entry -> {
                    String[] data = entry.getValue().split(",");
                    msg.append("👤 ").append(entry.getKey())
                       .append(" - Elements: ").append(data[0])
                       .append(", Level: ").append(data[1]).append("\n");
                });
            JOptionPane.showMessageDialog(this, msg.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
        });
        bottomPanel.add(leaderboardBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        suggestionArea.setText(AISuggester.getHint(discovered));
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElementCombinerGame());
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElementCombinerGame());
    }
}
