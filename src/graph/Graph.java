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

    /**
     * Create a Graph from a pool of words with the same length
     */
    private void buildGraph(HashSet<String> words) {
        for (String word : words) {
            adjacencyList.put(word, new ArrayList<>());
        }

        for (String word : words) {
            // convert word into array of letters
            char[] chars = word.toCharArray();
            for (int i = 0; i < word.length(); i++) {
                char originalChar = chars[i];

                // try changing the char into all alphabet
                for (char c = 'a'; c <= 'z'; c++) {
                    // if char changed is not the original char
                    if (c != originalChar) {
                        chars[i] = c;
                        String newWord = String.valueOf(chars);

                        // if the newly built word is a valid word in the dictionary
                        if (words.contains(newWord)) {
                            adjacencyList.get(word).add(newWord);
                        }
                    }
                }
                // Reset the character
                chars[i] = originalChar;
            }
        }
    }

    public HashMap<String, ArrayList<String>> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Debuging
     * <p>
     * Print every node and its connections
     */
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
