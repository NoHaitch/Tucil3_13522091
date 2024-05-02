package search;

import java.util.*;

import graph.*;
import pair.*;

public class GreedyBestFirstSearch {

    public static Pair<List<String>, Integer> findShortestPath(Graph graph, String source, String target) {
        // Create a priority queue to store nodes to be explored, ordered by their heuristic value
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getHeuristicValue));

        // Create a map to keep track of visited nodes
        Set<String> visited = new HashSet<>();
        // Create a map to keep track of the parent node for each node in the shortest path
        Map<String, String> parent = new HashMap<>();

        // Add the source node to the priority queue with its heuristic value
        pq.offer(new Node(source, heuristic(source, target)));

        int nodeVisited = 0; // Counter to keep track of visited nodes

        // Perform Greedy Best First Search
        while (!pq.isEmpty()) {
            // Dequeue the node with the lowest heuristic value
            Node currentNode = pq.poll();
            nodeVisited++; // Increment visited node count
            String currentWord = currentNode.getWord();

            // If the target word is reached, reconstruct and return the shortest path
            if (currentWord.equals(target)) {
                List<String> shortestPath = new ArrayList<>();
                String word = target;
                while (word != null) {
                    shortestPath.add(0, word);
                    word = parent.get(word);
                }
                return new Pair<>(shortestPath, nodeVisited);
            }

            // Mark the current node as visited
            visited.add(currentWord);

            // Explore neighbors of the current node
            for (String neighbor : graph.getAdjacencyList().getOrDefault(currentWord, new ArrayList<>())) {
                // If the neighbor has not been visited
                if (!visited.contains(neighbor)) {
                    // Update the parent node for the neighbor
                    parent.put(neighbor, currentWord);
                    // Add the neighbor to the priority queue with its heuristic value
                    pq.offer(new Node(neighbor, heuristic(neighbor, target)));
                }
            }
        }

        // If the target word cannot be reached from the source word
        return new Pair<>(Collections.emptyList(), nodeVisited);
    }

    // Heuristic function (e.g., Hamming distance, Levenshtein distance, etc.)
    private static int heuristic(String word, String target) {
        // Example: Hamming distance heuristic
        int distance = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != target.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    // Node class representing a word and its heuristic value
    private static class Node {
        private String word;
        private int heuristicValue;

        public Node(String word, int heuristicValue) {
            this.word = word;
            this.heuristicValue = heuristicValue;
        }

        public String getWord() {
            return word;
        }

        public int getHeuristicValue() {
            return heuristicValue;
        }
    }
}
