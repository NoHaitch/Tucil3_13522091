import java.util.Scanner;

import dictionary.*;

public class Main {
    private static String dictionaryPath = "../dictionary/dictionary.txt";

    public static void main(String[] args) {
        Utils.printWordLadder();
        System.out.println("========================= Program Started =========================");
        Dictionary dictionary = new Dictionary(dictionaryPath);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Starting Word: ");
        String source = scanner.nextLine();

        System.out.print("Enter Target Word: ");
        String target = scanner.nextLine();

        scanner.close();

        if(source.length() != target.length()){
            Utils.printRed("Both Words must be the same length.");
        }

        if(dictionary.isValidWord(source)){
            Utils.printGreen(source + " is in the dictionary.");
        } else{
            Utils.printRed(source + " is not in the dictionary.");
        }
         
        if(dictionary.isValidWord(target)){
            Utils.printGreen(target + " is in the dictionary.");
        } else{
            Utils.printRed(target + " is not in the dictionary.");
        }



        System.out.println("========================= Program Exited =========================");
    }  
}
