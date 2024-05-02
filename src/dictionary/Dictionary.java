package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

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
     * CTOR 
     * 
     * @param filename
     */
    public Dictionary(String filename) {
        words = new HashSet<>();
        loadWords(filename);
    }

    /**
     * CCTOR 
     * 
     * @param other
     */
    public Dictionary(Dictionary other) {
        this.words = new HashSet<>(other.words);
    }

    public int getWordAmount() {
        return words.size();
    }

    public HashSet<String> getWords(){
        return words;
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
            //error
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

    /**
     * Limit the word in the dictionary to a certain length
     * <p>
     * All other words is removed
     * 
     * @param length
     */
    public void limitWordLength(int length) {
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            if (word.length() != length) {
                iterator.remove();
            }
        }
    }

    /**
     * Debuging
     * <p>
     * Print words in dictionary using foreach until amount is printed
     * 
     * @param amount of words
     */
    public void printWord(int amount) {
        int count = 0;
        for (String x : words) {
            System.out.println(x);
            count++;
            if (count > amount) {
                break;
            }
        }
    }

}
