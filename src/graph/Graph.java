package graph;

import dictionary.*;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Make a Graph
 * <p>
 * Vertices: words
 * <p>
 * Edge: words that has a 1 letter difference
 */
public class Graph {
    private HashMap<String, ArrayList<String>> adjacencyList;

    public Graph(Dictionary dictionary) {
        this.adjacencyList = new HashMap<>();
        buildGraph(dictionary.getWords());
    }

    private void buildGraph(HashSet<String> words) {
        // Initialize the adjacency list
        for (String word : words) {
            adjacencyList.put(word, new ArrayList<>());
        }

        // Iterate through the words to find connections
        for (String word : words) {
            char[] chars = word.toCharArray();
            for (int i = 0; i < word.length(); i++) {
                char originalChar = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c != originalChar) {
                        chars[i] = c;
                        String newWord = String.valueOf(chars);
                        if (words.contains(newWord)) {
                            adjacencyList.get(word).add(newWord);
                        }
                    }
                }
                // Reset the character back to original for next iteration
                chars[i] = originalChar;
            }
        }
    }

    public HashMap<String, ArrayList<String>> getAdjacencyList() {
        return adjacencyList;
    }

    public void printGraph() {
        for (Map.Entry<String, ArrayList<String>> entry : adjacencyList.entrySet()) {
            String word = entry.getKey();
            ArrayList<String> neighbors = entry.getValue();
            System.out.print(word + ": ");
            for (String neighbor : neighbors) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
}
