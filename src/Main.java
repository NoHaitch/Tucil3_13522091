import java.util.Scanner;

import dictionary.*;
import graph.*;
import search.*;

public class Main {
    private static String dictionaryPath = "../dictionary/dictionary.txt";

    public static void main(String[] args) {


        System.out.println("========================= Program Started =========================");
        Utils.printWordLadder();
        Dictionary fullDictionary = new Dictionary(dictionaryPath);

        if(fullDictionary.getWordAmount() == 0){
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


                Dictionary dictionary = new Dictionary(fullDictionary);
                Graph graph = new Graph(dictionary);

                if(menuInput.equals("1")){
                    dictionary.limitWordLength(source.length());
                    System.out.println("\nUniform Cost Search: ");
                    UniformCostSearch UCS = new UniformCostSearch(graph);
                    Utils.printResult(UCS.findShortestPath(source, target));
                    
                } else if(menuInput.equals("2")){
                    dictionary.limitWordLength(source.length());
                    System.out.println("\nGreedy Best First Search: ");
                    GreedyBestFirstSearch GBFS = new GreedyBestFirstSearch(graph);
                    Utils.printResult(GBFS.findShortestPath(source, target));

                } else {
                    dictionary.limitWordLength(source.length());
                    System.out.println("\nA* Search: ");
                    AStarSearch AStar = new AStarSearch(graph);
                    Utils.printResult(AStar.findShortestPath(source, target));
                }
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
