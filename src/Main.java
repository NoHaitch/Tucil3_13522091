import java.util.Scanner;

import dictionary.*;
import graph.*;
import search.*;

public class Main {
    private static String dictionaryPath = "./dictionary/dictionary.txt";

    public static void main(String[] args) {


        System.out.println("========================= Program Started =========================");
        Utils.printWordLadder();
        // Load english dictionary
        Dictionary fullDictionary = new Dictionary(dictionaryPath);

        if(fullDictionary.getWordAmount() == 0){
            System.err.println("Dictionary not found!");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        
        while(true){
            System.err.println("===== Menu: =====");
            System.err.println("1. Uniform Cost Search");
            System.err.println("2. Greedy Best First Search");
            System.err.println("3. A*");
            System.err.println("4. Exit");
            System.err.print("Input the number: ");
            String menuInput = scanner.nextLine();

            if (menuInput.equals("1") || menuInput.equals("2") || menuInput.equals("3")) {
                System.out.print("\nEnter Starting Word: ");
                String source = scanner.nextLine();
                source = source.toLowerCase();
                
                System.out.print("Enter Target Word: ");
                String target = scanner.nextLine();
                target = target.toLowerCase();
                
                if(source.length() != target.length()){
                    Utils.printRed("Both Words must be the same length.\n");
                    continue;
                }
                
                if(!fullDictionary.isValidWord(source)){
                    Utils.printRed(source + " is not in the dictionary.\n");
                    continue;
                }
                
                if(!fullDictionary.isValidWord(target)){
                    Utils.printRed(target + " is not in the dictionary.\n");
                    continue;
                }

                // Create a local dictionary
                Dictionary dictionary = new Dictionary(fullDictionary);
                
                // Only get words with source length
                dictionary.limitWordLength(source.length());
                
                // Create a graph from the dictionary
                Graph graph = new Graph(dictionary);

                long startTime;

                if(menuInput.equals("1")){
                    System.out.println("\nUniform Cost Search: ");

                    startTime = System.currentTimeMillis();
                    UniformCostSearch UCS = new UniformCostSearch(graph);
                    Utils.printResult(UCS.findShortestPath(source, target));
                    
                } else if(menuInput.equals("2")){
                    System.out.println("\nGreedy Best First Search: ");
                    
                    startTime = System.currentTimeMillis();
                    GreedyBestFirstSearch GBFS = new GreedyBestFirstSearch(graph);
                    Utils.printResult(GBFS.findShortestPath(source, target));

                } else {
                    System.out.println("\nA* Search: ");

                    startTime = System.currentTimeMillis();
                    AStarSearch AStar = new AStarSearch(graph);
                    Utils.printResult(AStar.findShortestPath(source, target));
                }
                
                long endTime = System.currentTimeMillis();
                long timeTaken = endTime - startTime;
                System.out.println("Time taken: " + timeTaken + " milliseconds");

            } else if(menuInput.equals("4")){
                break;

            } else{
                Utils.printRed("Error: Invalid Input.\n");
            }
        }

        scanner.close();
        System.out.println("\n========================= Program Exited =========================");
    }  
}
