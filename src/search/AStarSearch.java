package search;

import graph.*;
import java.util.*;

public class AStarSearch {
    private Graph graph;

    public AStarSearch(Graph graph) {
        this.graph = graph;
    }

    public List<String> findShortestPath(String source, String target) {
        // Create a priority queue to store nodes to be explored, ordered by their f-score
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getFScore));

        // Create a map to keep track of visited nodes and their g-scores
        Map<String, Integer> gScores = new HashMap<>();
        // Create a map to keep track of the parent node for each node in the shortest path
        Map<String, String> parent = new HashMap<>();

        // Initialize g-scores for all nodes to infinity
        for (String word : graph.getAdjacencyList().keySet()) {
            gScores.put(word, Integer.MAX_VALUE);
        }

        // Add the source node to the priority queue with f-score = heuristic value
        pq.offer(new Node(source, heuristic(source, target), 0));
        gScores.put(source, 0);

        // Perform A* Search
        while (!pq.isEmpty()) {
            // Dequeue the node with the lowest f-score
            Node currentNode = pq.poll();
            String currentWord = currentNode.getWord();
            int currentCost = currentNode.getCost();

            // If the target word is reached, reconstruct and return the shortest path
            if (currentWord.equals(target)) {
                List<String> shortestPath = new ArrayList<>();
                String word = target;
                while (word != null) {
                    shortestPath.add(0, word);
                    word = parent.get(word);
                }
                return shortestPath;
            }

            // Explore neighbors of the current node
            for (String neighbor : graph.getAdjacencyList().getOrDefault(currentWord, new ArrayList<>())) {
                // Calculate the tentative g-score for the neighbor
                int tentativeGScore = currentCost + 1; // Assuming each transformation has a cost of 1

                // If the tentative g-score is lower than the current g-score
                if (tentativeGScore < gScores.get(neighbor)) {
                    // Update the parent node for the neighbor
                    parent.put(neighbor, currentWord);
                    // Update the g-score for the neighbor
                    gScores.put(neighbor, tentativeGScore);
                    // Calculate the f-score for the neighbor
                    int fScore = tentativeGScore + heuristic(neighbor, target);
                    // Add the neighbor to the priority queue with its f-score
                    pq.offer(new Node(neighbor, fScore, tentativeGScore));
                }
            }
        }

        // If the target word cannot be reached from the source word
        return Collections.emptyList();
    }

    // Heuristic function (e.g., Hamming distance, Levenshtein distance, etc.)
    private int heuristic(String word, String target) {
        // Example: Hamming distance heuristic
        int distance = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != target.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    // Node class representing a word and its f-score
    private static class Node {
        private String word;
        private int fScore;
        private int cost;

        public Node(String word, int fScore, int cost) {
            this.word = word;
            this.fScore = fScore;
            this.cost = cost;
        }

        public String getWord() {
            return word;
        }

        public int getFScore() {
            return fScore;
        }

        public int getCost() {
            return cost;
        }
    }
}
