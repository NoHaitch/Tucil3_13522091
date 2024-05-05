import java.util.List;
import javax.swing.*;
import java.awt.*;

import dictionary.*;
import graph.*;
import search.*;
import pair.*;

public class Main {
    private static String dictionaryPath = "../dictionary/dictionary.txt";
    private static Dictionary fullDictionary = new Dictionary(dictionaryPath);
    private static int FRAME_HEIGHT_MENU = 360;
    private static int FRAME_WIDTH_MENU = 600;
    private static int FRAME_HEIGHT_RESULT = 400;
    private static int FRAME_WIDTH_RESULT = 500;

    public static void main(String[] args) {
        // Test.testGBFS();
        // Test.testUCS();
        // Test.testAstar();
        
        System.out.println("========================= Program Started =========================");

        if (fullDictionary.getWords().isEmpty()) {
            System.err.println("Dictionary not found");
            return;
        }

        // Create and show the Swing UI
        SwingUtilities.invokeLater(() -> createAndShowGUI());

    }

    /**
     * Perform the Search
     */
    private static void performSearch(String algorithm, String source, String target) {
        // Check if source or target are empty
        if (source.trim().isEmpty() || target.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Source and Target words cannot be empty.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if source or target contain spaces
        if (source.contains(" ") || target.contains(" ")) {
            JOptionPane.showMessageDialog(null, "Word can not contain space (\" \")", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        source = source.toLowerCase();
        target = target.toLowerCase();

        // Check if source and target are valid
        if (source.length() != target.length()) {
            JOptionPane.showMessageDialog(null, "Both Words must be the same length.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!fullDictionary.isValidWord(source)) {
            JOptionPane.showMessageDialog(null, source + " is not in the dictionary.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!fullDictionary.isValidWord(target)) {
            JOptionPane.showMessageDialog(null, target + " is not in the dictionary.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(source.equals(target)){
            JOptionPane.showMessageDialog(null, "Word must be different.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create a local dictionary
        Dictionary dictionary = new Dictionary(fullDictionary);

        // Only get words with source length
        dictionary.limitWordLength(source.length());

        // Create a graph from the dictionary
        Graph graph = new Graph(dictionary);

        long startTime, endTime; 
        long memoryBefore, memoryAfter;   
        Pair<List<String>, Integer> pairOutput;
        
        if (algorithm.equals("UCS")) {
            // Uniform Cost Search
            Runtime runtime = Runtime.getRuntime();
            memoryBefore = runtime.freeMemory();
            startTime = System.currentTimeMillis();

            pairOutput = UniformCostSearch.findShortestPath(graph, source, target);
            
            endTime = System.currentTimeMillis();
            memoryAfter = runtime.freeMemory();
            
        } else if (algorithm.equals("GBFS")) {
            // Greedy Best First Search
            Runtime runtime = Runtime.getRuntime();
            memoryBefore = runtime.freeMemory();
            startTime = System.currentTimeMillis();
            
            pairOutput = GreedyBestFirstSearch.findShortestPath(graph, source, target);
            
            endTime = System.currentTimeMillis();
            memoryAfter = runtime.freeMemory();
            
        } else { // AS
            // A* Search
            Runtime runtime = Runtime.getRuntime();
            memoryBefore = runtime.freeMemory();
            startTime = System.currentTimeMillis();
            
            pairOutput = AStarSearch.findShortestPath(graph, source, target);
            
            endTime = System.currentTimeMillis();
            memoryAfter = runtime.freeMemory();
            
        }

        List<String> result = pairOutput.getFirst();
        int nodeVisited = pairOutput.getSecond();
        
        // Calculate time taken
        long timeTaken = endTime - startTime;
        long memoryUsed = memoryBefore - memoryAfter;
        if(result.size() != 0){
            displayResult(result, timeTaken, nodeVisited, memoryUsed, algorithm);
        } else{
            displayResult(timeTaken, nodeVisited, memoryUsed, algorithm);
        }
    }

    /**
     * Create Main Menu Window
     */
    private static void createAndShowGUI() {
        // Create window
        JFrame frame = new JFrame("Word Ladder Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH_MENU, FRAME_HEIGHT_MENU); // Set frame size

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        // Create a title label
        JLabel titleLabel = new JLabel("Word Ladder Solver");
        Font titleFont = new Font("FigTree", Font.BOLD, 24);
        titleLabel.setFont(titleFont);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);

        // Create a subtitle label
        JLabel subtitleLabel = new JLabel("made by Francisco");
        Font subtitleFont = new Font("FigTree", Font.PLAIN, 16);
        subtitleLabel.setFont(subtitleFont);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(subtitleLabel);

        // Add some vertical space
        headerPanel.add(Box.createVerticalStrut(50));

        // Add header panel to the main panel
        panel.add(headerPanel);

        // Create a content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Set BoxLayout with Y_AXIS alignment

        // Create a panel for source content
        JPanel sourcePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel sourceLabel = new JLabel("Source: ");
        sourceLabel.setFont(subtitleFont);
        JTextField sourceTextField = new JTextField(15);
        sourceTextField.setFont(subtitleFont);
        sourcePanel.add(sourceLabel);
        sourcePanel.add(sourceTextField);

        // Create a panel for target content
        JPanel targetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel targetLabel = new JLabel("Target: ");
        targetLabel.setFont(subtitleFont);
        JTextField targetTextField = new JTextField(15);
        targetTextField.setFont(subtitleFont);
        targetPanel.add(targetLabel);
        targetPanel.add(targetTextField);

        // Add both text box to the main panel
        contentPanel.add(sourcePanel);
        contentPanel.add(targetPanel);
        panel.add(contentPanel);

        // Create a panel for Choosing an algorithm
        JPanel algorithmPanel = new JPanel();
        algorithmPanel.setLayout(new BoxLayout(algorithmPanel, BoxLayout.Y_AXIS));

        JLabel algorithmLabel = new JLabel("Choose Algorithm");
        Font algorithmFont = new Font("FigTree", Font.PLAIN, 16);
        algorithmLabel.setFont(algorithmFont);
        algorithmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        algorithmPanel.add(algorithmLabel);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();

        // UCS Button
        JButton buttonUCS = new JButton("Uniform Cost Search");
        buttonUCS.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(buttonUCS);

        // Greedy Best First Search Button
        JButton buttonGBFS = new JButton("Greedy Best First Search");
        buttonGBFS.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(buttonGBFS);

        // A*
        JButton buttonAS = new JButton("A*");
        buttonAS.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(buttonAS);

        // Add action listeners to the buttons
        buttonUCS.addActionListener(e -> performSearch("UCS", sourceTextField.getText(), targetTextField.getText()));
        buttonGBFS.addActionListener(e -> performSearch("GBFS", sourceTextField.getText(), targetTextField.getText()));
        buttonAS.addActionListener(e -> performSearch("AS", sourceTextField.getText(), targetTextField.getText()));

        // Add algorithm to main panel
        algorithmPanel.add(buttonPanel);
        panel.add(algorithmPanel);

        // Add panel to the frame
        frame.add(panel);

        // Display the window
        frame.setVisible(true);
    }

    /**
     * Display Result Window
     */
    private static void displayResult(List<String>result, long timeTaken, int nodeVisited, long memoryUsed, String algorithm) {
        // Create and configure the result window
        JFrame resultFrame = new JFrame("Result");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(FRAME_WIDTH_RESULT, FRAME_HEIGHT_RESULT);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - resultFrame.getWidth()) / 2;
        int y = (screenSize.height - resultFrame.getHeight()) / 2;
        resultFrame.setLocation(x, y);

        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        // Create a title label
        JLabel titleLabel = new JLabel("Result");
        Font titleFont = new Font("FigTree", Font.BOLD, 20);
        titleLabel.setFont(titleFont);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);

        // Create a subtitle label
        JLabel subtitleLabel;
        if (algorithm.equals("UCS")) {
            subtitleLabel = new JLabel("Using Uniform Cost Search");
        } else if (algorithm.equals("GBFS")) {
            subtitleLabel = new JLabel("Using Greedy Best First Search");
        } else {
            subtitleLabel = new JLabel("Using A* algorithm");
        }
        Font subtitleFont = new Font("FigTree", Font.PLAIN, 16);
        subtitleLabel.setFont(subtitleFont);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(subtitleLabel);

        // Add some vertical space
        headerPanel.add(Box.createVerticalStrut(24));

        // Add header panel to the main panel
        panel.add(headerPanel);

        // Create a panel for main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel timeLabel = new JLabel("Time taken: " + timeTaken + " ms");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(timeLabel);

        JLabel visitedLabel = new JLabel("Node visited: " + nodeVisited);
        visitedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        visitedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(visitedLabel);
        
        JLabel memoryLabel = new JLabel("Approximate Memory usage: " + (memoryUsed/1000) +" kb");
        memoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        memoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(memoryLabel);

        contentPanel.add(Box.createVerticalStrut(15));

        // Create Panel for list or string result
        JPanel resultTextPanel = new JPanel(new BorderLayout());

        // Create a list model to hold the result
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < result.size(); i++) {
            listModel.addElement((i + 1) + ". " + result.get(i));
        }

        // Create JList with the list model
        JList<String> resultList = new JList<>(listModel);
        resultList.setFont(new Font("Arial", Font.PLAIN, 16));

        // Wrap the resultList in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(resultList);
        scrollPane.setPreferredSize(new Dimension(300, scrollPane.getPreferredSize().height)); // Set preferred width

        // Wrap the scrollPane in another panel with FlowLayout
        JPanel resultListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultListPanel.add(scrollPane); // Add the scroll pane to the panel

        // Add the resultListPanel to the resultTextPanel
        resultTextPanel.add(resultListPanel, BorderLayout.CENTER);

        // Add the resultTextPanel to the contentPanel
        contentPanel.add(resultTextPanel);

        panel.add(contentPanel);

        // Add result panel to the frame and display
        resultFrame.add(panel);
        resultFrame.setVisible(true);
    }

    /**
     * Display Result Window for solution not found
     */
    private static void displayResult(long timeTaken, int nodeVisited, long memoryUsed, String algorithm) {
        // Create and configure the result window
        JFrame resultFrame = new JFrame("Result");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(FRAME_WIDTH_RESULT, FRAME_HEIGHT_RESULT);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - resultFrame.getWidth()) / 2;
        int y = (screenSize.height - resultFrame.getHeight()) / 2;
        resultFrame.setLocation(x, y);

        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        // Create a title label
        JLabel titleLabel = new JLabel("Result");
        Font titleFont = new Font("FigTree", Font.BOLD, 20);
        titleLabel.setFont(titleFont);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);

        // Create a subtitle label
        JLabel subtitleLabel;
        if (algorithm.equals("UCS")) {
            subtitleLabel = new JLabel("Using Uniform Cost Search");
        } else if (algorithm.equals("GBFS")) {
            subtitleLabel = new JLabel("Using Greedy Best First Search");
        } else {
            subtitleLabel = new JLabel("Using A* algorithm");
        }
        Font subtitleFont = new Font("FigTree", Font.PLAIN, 16);
        subtitleLabel.setFont(subtitleFont);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(subtitleLabel);

        // Add some vertical space
        headerPanel.add(Box.createVerticalStrut(24));

        // Add header panel to the main panel
        panel.add(headerPanel);

        // Create a panel for main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel timeLabel = new JLabel("Time taken: " + timeTaken + " ms");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(timeLabel);

        JLabel visitedLabel = new JLabel("Node visited: " + nodeVisited);
        visitedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        visitedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(visitedLabel);

        JLabel memoryLabel = new JLabel("Approximate Memory usage: " + (memoryUsed/1000) +" kb");
        memoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        memoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(memoryLabel);

        contentPanel.add(Box.createVerticalStrut(15));

        // Create Panel for list or string result
        JLabel noSolutionLabel = new JLabel("No Solution Found");
        noSolutionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        noSolutionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the resultTextPanel to the contentPanel
        contentPanel.add(noSolutionLabel);

        panel.add(contentPanel);

        // Add result panel to the frame and display
        resultFrame.add(panel);
        resultFrame.setVisible(true);
    }
}
