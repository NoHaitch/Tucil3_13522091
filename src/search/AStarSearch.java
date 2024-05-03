package search;

import java.util.*;

import graph.*;
import pair.*;

public class AStarSearch {
    /**
     * A* Nodes
     * <p>
     * Store word, fscore, and cost
     */
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

    
    /**
     * Calculate Heuristic Value
     */ 
    private static int heuristic(String word, String target) {
        int difference = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != target.charAt(i)) {
                difference++;
            }
        }
        return difference;
    }

    /**
     * A* algorithm
     */
    public static Pair<List<String>, Integer> findShortestPath(Graph graph, String source, String target) {
        // Queue of nodes sorted by their fscore
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getFScore));

        // Map to track visited nodes and their gScores
        Map<String, Integer> gScores = new HashMap<>();
        
        // Map to keep track of the parent node for each node in the shortest path
        Map<String, String> parent = new HashMap<>();

        // Initialize g-scores for all nodes
        for (String word : graph.getAdjacencyList().keySet()) {
            gScores.put(word, Integer.MAX_VALUE);
        }

        // Add the source node to the priority queue
        pq.offer(new Node(source, heuristic(source, target), 0));
        gScores.put(source, 0);

        // Node visited counter
        int nodeVisited = 0;

        // Perform A* Search
        while (!pq.isEmpty()) {
            // Dequeue the node with the lowest f-score
            Node currentNode = pq.poll();
            
            nodeVisited++;
            String currentWord = currentNode.getWord();
            int currentCost = currentNode.getCost();

            // Target found
            if (currentWord.equals(target)) {
                List<String> shortestPath = new ArrayList<>();
                
                // Reconstruct the result
                String word = target;
                while (word != null) {
                    shortestPath.add(0, word);
                    word = parent.get(word);
                }
                return new Pair<>(shortestPath, nodeVisited);
            }

            // Add neighboring node
            for (String neighbor : graph.getAdjacencyList().getOrDefault(currentWord, new ArrayList<>())) {
                // Calculate the tentative g-score for the neighbor
                int tentativeGScore = currentCost + 1;

                // If the tentative g-score is lower than the current g-score
                if (tentativeGScore < gScores.get(neighbor)) {
                    // Update the parent node for the neighbor
                    parent.put(neighbor, currentWord);
                    
                    // Update the g-score for the neighbor
                    gScores.put(neighbor, tentativeGScore);

                    // Calculate the f-score for the neighbor 
                    int fScore = tentativeGScore + heuristic(neighbor, target);

                    // Add the neighbor to the priority queue
                    pq.offer(new Node(neighbor, fScore, tentativeGScore));
                }
            }
        }

        // If the target not found
        return new Pair<>(Collections.emptyList(), nodeVisited);
    }
}
