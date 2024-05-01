import java.util.List;

public class Utils {
    /**
     * Print text in Red color
     * 
     * @param text
     */
    public static void printRed(String text) {
        System.out.println("\u001B[31m" + text + "\u001B[0m");
    }

    /**
     * Print text in Green Color
     * 
     * @param text The text to print in green.
     */
    public static void printGreen(String text) {
        System.out.println("\u001B[32m" + text + "\u001B[0m");
    }

    /**
     * Print Word Ladder using ASCII art in blue
     */
    public static void printWordLadder() {
        String wordLadderAscii =
            "\n /$$      /$$                           /$$       /$$                       /$$       /$$                    \n" +
            "| $$  /$ | $$                          | $$      | $$                      | $$      | $$                    \n" +
            "| $$ /$$$| $$  /$$$$$$   /$$$$$$   /$$$$$$$      | $$        /$$$$$$   /$$$$$$$  /$$$$$$$  /$$$$$$   /$$$$$$ \n" +
            "| $$/$$ $$ $$ /$$__  $$ /$$__  $$ /$$__  $$      | $$       |____  $$ /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$\n" +
            "| $$$$_  $$$$| $$  \\ $$| $$  \\__/| $$  | $$      | $$        /$$$$$$$| $$  | $$| $$  | $$| $$$$$$$$| $$  \\__/\n" +
            "| $$$/ \\  $$$| $$  | $$| $$      | $$  | $$      | $$       /$$__  $$| $$  | $$| $$  | $$| $$_____/| $$      \n" +
            "| $$/   \\  $$|  $$$$$$/| $$      |  $$$$$$$      | $$$$$$$$|  $$$$$$$|  $$$$$$$|  $$$$$$$|  $$$$$$$| $$      \n" +
            "|__/     \\__/ \\______/ |__/       \\_______/      |________/ \\_______/ \\_______/ \\_______/ \\_______/|__/      \n";

        System.out.println("\u001B[34m" + wordLadderAscii + "\u001B[0m");
    }

    public static void printResult(List<String> result){
        if(result.isEmpty()){
            printRed("No solution found.\n");
            return;
        }

        System.out.println("Result: ");
        for (int i = 0; i < result.size(); i++) {
            System.out.println((i + 1) + ". " + result.get(i).toUpperCase());
        }  
        System.out.println();
    }
}
