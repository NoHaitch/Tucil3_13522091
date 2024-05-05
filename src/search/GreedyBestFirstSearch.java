package search;

import java.util.*;

import graph.*;
import pair.*;

public class GreedyBestFirstSearch {
    
    /**
     * Greedy Best First Search Nodes
     * <p>
     * Store word and heuristic value
     */
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
     * Greedy Best First Search
     */
    public static Pair<List<String>, Integer> findShortestPath(Graph graph, String source, String target) {
        // Queue of nodes sorted by heuristic
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getHeuristicValue));
        
        // Map to track visited nodes
        Set<String> visited = new HashSet<>();
        
        // Map to keep track of the parent node for each node in the shortest path
        Map<String, String> parent = new HashMap<>();

        // Add the source node to the priority queue
        pq.offer(new Node(source, heuristic(source, target)));

        // Node visited counter
        int nodeVisited = 0;

        // Greedy Best First Search
        while (!pq.isEmpty()) {
            // Get the node with the smallest heuristic
            Node currentNode = pq.poll();
            pq.clear();
            
            nodeVisited++;
            String currentWord = currentNode.getWord();
            if(visited.contains(currentWord)){
                break;
            } else{
                visited.add(currentWord);
            }

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

            // add neighboring node
            for (String neighbor : graph.getAdjacencyList().getOrDefault(currentWord, new ArrayList<>())) {
                // Update the parent node for the neighbor
                parent.put(neighbor, currentWord);

                // Add the neighbor to the priority queue
                pq.offer(new Node(neighbor, heuristic(neighbor, target)));                        
            }
        }

        // If the target not found
        return new Pair<>(Collections.emptyList(), nodeVisited);
    }
}
