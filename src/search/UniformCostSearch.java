package search;

import graph.*;
import java.util.*;

public class UniformCostSearch {
    private Graph graph;

    public UniformCostSearch(Graph graph) {
        this.graph = graph;
    }

    public List<String> findShortestPath(String source, String target) {
        // Create a priority queue to store nodes to be explored, ordered by their cost
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));

        // Create a map to keep track of visited nodes and their costs
        Map<String, Integer> visited = new HashMap<>();
        // Create a map to keep track of the parent node for each node in the shortest path
        Map<String, String> parent = new HashMap<>();

        // Add the source node to the priority queue with cost 0
        pq.offer(new Node(source, 0));

        // Perform Uniform Cost Search
        while (!pq.isEmpty()) {
            // Dequeue the node with the lowest cost
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

            // Mark the current node as visited
            visited.put(currentWord, currentCost);

            // Explore neighbors of the current node
            for (String neighbor : graph.getAdjacencyList().getOrDefault(currentWord, new ArrayList<>())) {
                // Calculate the cost of reaching the neighbor
                int neighborCost = currentCost + 1; // Assuming each transformation has a cost of 1

                // If the neighbor has not been visited or the new cost is lower than the previous cost
                if (!visited.containsKey(neighbor) || neighborCost < visited.get(neighbor)) {
                    // Update the cost of the neighbor
                    visited.put(neighbor, neighborCost);
                    // Update the parent node for the neighbor
                    parent.put(neighbor, currentWord);
                    // Add the neighbor to the priority queue with its cost
                    pq.offer(new Node(neighbor, neighborCost));
                }
            }
        }

        // If the target word cannot be reached from the source word
        return Collections.emptyList();
    }

    // Node class representing a word and its cost
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
}
