import java.util.List;
import dictionary.*;
import graph.*;
import search.*;
import pair.*;

/**
 * For Testing the search algorithm
 */
public class Test {
    private static String dictionaryPath = "../dictionary/dictionary.txt";
    private static Dictionary fullDictionary = new Dictionary(dictionaryPath);

    public static void testAstar() {
        System.err.println("CHECKING all 4 letter words combinantion of searching");
        fullDictionary.limitWordLength(4);
        Graph graph = new Graph(fullDictionary);
        int sample = 0;
        int maxPath = 0;
        int minPath = Integer.MAX_VALUE;
        long sumPath = 0;
        int maxVisited = 0;
        int minVisited = Integer.MAX_VALUE;
        long sumVisited = 0;
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;
        long sumTime = 0;
        long maxMemory = 0;
        long minMemory = Long.MAX_VALUE;
        long sumMemory = 0;

        for(String source: fullDictionary.getWords()){
            if(sample == 100000){
                break;
            }
            for(String target: fullDictionary.getWords()){
                if(sample == 100000){
                    break;
                }
                sample++;
                Runtime runtime = Runtime.getRuntime();
                long memoryBefore = runtime.freeMemory();
                long startTime = System.currentTimeMillis();
                
                Pair<List<String>, Integer> result = AStarSearch.findShortestPath(graph, source, target);
                
                long endTime = System.currentTimeMillis();
                long memoryAfter = runtime.freeMemory();

                long timeTaken = endTime - startTime;
                if(timeTaken > 12){
                    System.out.println(source + " - " + target);
                    System.out.println(timeTaken + " ms ");
                }
                if(timeTaken > maxTime){
                    maxTime = timeTaken;
                }
                if(timeTaken < minTime){
                    minTime = timeTaken;
                }
                sumTime += timeTaken;
                
                List<String> path = result.getFirst();
                int pathSize = path.size();
                if(pathSize > maxPath){
                    maxPath = pathSize;
                }
                if(pathSize < minPath){
                    minPath = pathSize;
                }
                sumPath += pathSize;
                
                int visited = result.getSecond();
                if(visited > maxVisited){
                    maxVisited = visited;
                }
                if(visited < minVisited){
                    minVisited = visited;
                }
                sumVisited += visited;
                                
                long memoryUsed = memoryBefore - memoryAfter;
                if(memoryUsed > maxMemory){
                    maxMemory = memoryUsed;
                }
                if(memoryUsed < minMemory){
                    minMemory = memoryUsed;
                }
                sumMemory += memoryUsed;
                
            }
        }

        System.out.println("Sample: " + sample + "\n");
        System.out.println("A Star Search: ");
        System.out.println("> max Path: " + maxPath);
        System.out.println("> min Path: " + minPath);
        System.out.println("> avg Path: " + (sumPath/sample));
        System.out.println("> total Time: " + sumTime);
        System.out.println("> max Time: " + maxTime);
        System.out.println("> min Time: " + minTime);
        System.out.println("> avg Time: " + (sumTime/sample));
        System.out.println("> max Visited: " + maxVisited);
        System.out.println("> min Visited: " + minVisited);
        System.out.println("> avg Visited: " + (sumVisited/sample));
        System.out.println("> max Memeory used: " + (maxMemory/1000) + " kb");
        System.out.println("> min Memory used: " + (minMemory/1000) + " kb");
        System.out.println("> avg Memory used: " + (sumMemory/sample/1000) + " kb");
    }

    public static void testGBFS() {
        System.err.println("CHECKING all 4 letter words combinantion of searching");
        fullDictionary.limitWordLength(4);
        Graph graph = new Graph(fullDictionary);
        int sample = 0;
        int maxPath = 0;
        int minPath = Integer.MAX_VALUE;
        long sumPath = 0;
        int maxVisited = 0;
        int minVisited = Integer.MAX_VALUE;
        long sumVisited = 0;
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;
        long sumTime = 0;
        long maxMemory = 0;
        long minMemory = Long.MAX_VALUE;
        long sumMemory = 0;

        for(String source: fullDictionary.getWords()){
            if(sample == 100000){
                break;
            }
            for(String target: fullDictionary.getWords()){
                if(sample == 100000){
                    break;
                }
                sample++;
                Runtime runtime = Runtime.getRuntime();
                long memoryBefore = runtime.freeMemory();
                long startTime = System.currentTimeMillis();
                
                Pair<List<String>, Integer> result = GreedyBestFirstSearch.findShortestPath(graph, source, target);
                
                long endTime = System.currentTimeMillis();
                long memoryAfter = runtime.freeMemory();

                long timeTaken = endTime - startTime;
                if(timeTaken > 12){
                    System.out.println(source + " - " + target);
                    System.out.println(timeTaken + " ms ");
                }
                if(timeTaken > maxTime){
                    maxTime = timeTaken;
                }
                if(timeTaken < minTime){
                    minTime = timeTaken;
                }
                sumTime += timeTaken;
                
                List<String> path = result.getFirst();
                int pathSize = path.size();
                if(pathSize > maxPath){
                    maxPath = pathSize;
                }
                if(pathSize < minPath){
                    minPath = pathSize;
                }
                sumPath += pathSize;
                
                int visited = result.getSecond();
                if(visited > maxVisited){
                    maxVisited = visited;
                }
                if(visited < minVisited){
                    minVisited = visited;
                }
                sumVisited += visited;
                                
                long memoryUsed = memoryBefore - memoryAfter;
                if(memoryUsed > maxMemory){
                    maxMemory = memoryUsed;
                }
                if(memoryUsed < minMemory){
                    minMemory = memoryUsed;
                }
                sumMemory += memoryUsed;
                
            }
        }

        System.out.println("Sample: " + sample + "\n");
        System.out.println("Greedy Best First Search: ");
        System.out.println("> max Path: " + maxPath);
        System.out.println("> min Path: " + minPath);
        System.out.println("> avg Path: " + (sumPath/sample));
        System.out.println("> total Time: " + sumTime);
        System.out.println("> max Time: " + maxTime);
        System.out.println("> min Time: " + minTime);
        System.out.println("> avg Time: " + (sumTime/sample));
        System.out.println("> max Visited: " + maxVisited);
        System.out.println("> min Visited: " + minVisited);
        System.out.println("> avg Visited: " + (sumVisited/sample));
        System.out.println("> max Memeory used: " + (maxMemory/1000) + " kb");
        System.out.println("> min Memory used: " + (minMemory/1000) + " kb");
        System.out.println("> avg Memory used: " + (sumMemory/sample/1000) + " kb");
    }

    public static void testUCS() {
        System.err.println("CHECKING all 4 letter words combinantion of searching");
        fullDictionary.limitWordLength(4);
        Graph graph = new Graph(fullDictionary);
        int sample = 0;
        int maxPath = 0;
        int minPath = Integer.MAX_VALUE;
        long sumPath = 0;
        int maxVisited = 0;
        int minVisited = Integer.MAX_VALUE;
        long sumVisited = 0;
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;
        long sumTime = 0;
        long maxMemory = 0;
        long minMemory = Long.MAX_VALUE;
        long sumMemory = 0;

        for(String source: fullDictionary.getWords()){
            if(sample == 100000){
                break;
            }
            for(String target: fullDictionary.getWords()){
                if(sample == 100000){
                    break;
                }
                sample++;
                Runtime runtime = Runtime.getRuntime();
                long memoryBefore = runtime.freeMemory();
                long startTime = System.currentTimeMillis();
                
                Pair<List<String>, Integer> result = UniformCostSearch.findShortestPath(graph, source, target);
                
                long endTime = System.currentTimeMillis();
                long memoryAfter = runtime.freeMemory();

                long timeTaken = endTime - startTime;
                if(timeTaken > 12){
                    System.out.println(source + " - " + target);
                    System.out.println(timeTaken + " ms ");
                }
                if(timeTaken > maxTime){
                    maxTime = timeTaken;
                }
                if(timeTaken < minTime){
                    minTime = timeTaken;
                }
                sumTime += timeTaken;
                
                List<String> path = result.getFirst();
                int pathSize = path.size();
                if(pathSize > maxPath){
                    maxPath = pathSize;
                }
                if(pathSize < minPath){
                    minPath = pathSize;
                }
                sumPath += pathSize;
                
                int visited = result.getSecond();
                if(visited > maxVisited){
                    maxVisited = visited;
                }
                if(visited < minVisited){
                    minVisited = visited;
                }
                sumVisited += visited;
                                
                long memoryUsed = memoryBefore - memoryAfter;
                if(memoryUsed > maxMemory){
                    maxMemory = memoryUsed;
                }
                if(memoryUsed < minMemory){
                    minMemory = memoryUsed;
                }
                sumMemory += memoryUsed;
                
            }
        }

        System.out.println("Sample: " + sample + "\n");
        System.out.println("Unifrom Cost Search: ");
        System.out.println("> max Path: " + maxPath);
        System.out.println("> min Path: " + minPath);
        System.out.println("> avg Path: " + (sumPath/sample));
        System.out.println("> total Time: " + sumTime);
        System.out.println("> max Time: " + maxTime);
        System.out.println("> min Time: " + minTime);
        System.out.println("> avg Time: " + (sumTime/sample));
        System.out.println("> max Visited: " + maxVisited);
        System.out.println("> min Visited: " + minVisited);
        System.out.println("> avg Visited: " + (sumVisited/sample));
        System.out.println("> max Memeory used: " + (maxMemory/1000) + " kb");
        System.out.println("> min Memory used: " + (minMemory/1000) + " kb");
        System.out.println("> avg Memory used: " + (sumMemory/sample/1000) + " kb");
    }
}
