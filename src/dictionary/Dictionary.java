package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Dictionary filled with English Words.
 * <p>
 * Word are made only of alphabets without any special char
 */
public class Dictionary {

    /**
     * Hash Set containing all the english words
     */
    private HashSet<String> words;

    /**
     * 
     * @param filename
     */
    public Dictionary(String filename) {
        words = new HashSet<>();
        loadWords(filename);
    }

    /**
     * Load the english dictionary from text file
     * 
     * @param filename name of file containing english dictionary
     */
    private void loadWords(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * check if the word is in the english dictionary
     * 
     * @param word
     * @return true if word is an english word
     */
    public boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }
}
