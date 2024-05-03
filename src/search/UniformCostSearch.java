package search;

import java.util.*;

import graph.*;
import pair.*;

public class UniformCostSearch {
    /**
     * Uniform Cost Search Nodes
     * <p>
     * Store word and cost
     */
    private static class Node {
        private String word;
        private int cost;

        public Node(String word, int cost) {
            this.word = word;
            this.cost = cost;
        }

        public String getWord() {
            return word;
        }

        public int getCost() {
            return cost;
        }
    }

    /**
     * Uniform Cost Search
     */
    public static Pair<List<String>, Integer> findShortestPath(Graph graph, String source, String target) {
        // Queue of nodes sorted by their cost
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));

        // Map to track visited nodes and the smallest cost
        Map<String, Integer> visited = new HashMap<>();

        // Map to keep track of the parent node for each node in the shortest path
        Map<String, String> parent = new HashMap<>();

        // Add the source node to the priority queue with cost 0
        pq.offer(new Node(source, 0));

        // Node visited counter
        int nodeVisited = 0;

        // Uniform Cost Search
        while (!pq.isEmpty()) {
            // Get the node with the lowest cost
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

            // Add node to visited
            visited.put(currentWord, currentCost);

            // Add neighboringing node
            for (String neighbor : graph.getAdjacencyList().getOrDefault(currentWord, new ArrayList<>())) {
                int neighborCost = currentCost + 1; 

                if (!visited.containsKey(neighbor) || neighborCost < visited.get(neighbor)) {
                    // Update the cost of the neighbor
                    visited.put(neighbor, neighborCost);

                    // Update the parent node for the neighbor
                    parent.put(neighbor, currentWord);
                    
                    // Add the neighbor to the priority queue
                    pq.offer(new Node(neighbor, neighborCost));
                }
            }
        }

        // If target is not found
        return new Pair<>(Collections.emptyList(), nodeVisited);
    }
}
