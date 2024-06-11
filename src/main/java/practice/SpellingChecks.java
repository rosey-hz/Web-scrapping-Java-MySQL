package practice;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SpellingChecks {
    public static void main(String[] args) {
        try {
            // Load the dictionary
            File dictFile = new File("path/to/dictionary/english.0");
            SpellDictionaryHashMap dictionary = new SpellDictionaryHashMap(dictFile);
            SpellChecker spellChecker = new SpellChecker(dictionary);

            // Get user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a word to check its spelling: ");
            String word = scanner.nextLine();

            // Check if the word is spelled correctly
            if (spellChecker.isCorrect(word)) {
                System.out.println("The spelling of '" + word + "' is correct.");
            } else {
                // Get suggestions for the correct spelling
                List<String> suggestions = spellChecker.getSuggestions(word, 1);
                if (suggestions.isEmpty()) {
                    System.out.println("The spelling of '" + word + "' is incorrect and no suggestions were found.");
                } else {
                    System.out.println("The spelling of '" + word + "' is incorrect. Did you mean '" + suggestions.get(0) + "'?");
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }
}


