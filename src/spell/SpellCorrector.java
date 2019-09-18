package spell;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SpellCorrector implements ISpellCorrector {
    public Trie trie = new Trie();
    char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        try(Scanner sc = new Scanner(new FileReader(dictionaryFileName))) { while(sc.hasNext()) trie.add(sc.next()); }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        TreeSet<String> matches = new TreeSet<>();
        // only use lowercase
        String word = inputWord.toLowerCase();
        // If we the word is found in the Trie and also has a frequency (is an accepted word) then we found it
        if(trie.find(word) != null) {
            if(trie.find(word).getValue() > 0) return word;
        }
        // If we did't find the word, we need to get the words of editDistance 1
        else {
            // Travers through every word generated to be edit Distance 1
            for(String s : suggestedWords(word)) {
                // If any of the editDistance 1 words are in the trie, add it to the matches set (no duplicates, sorted)
                if(trie.find(s) != null) {
                    if(trie.find(s).getValue() > 0) matches.add(s);
                }
            }
        }
        // If there were no matches of editDistance 1
        if(matches.size() == 0) {
            // get editDistance 2
            TreeSet<String> editDist2 = new TreeSet<>();
            for(String s : suggestedWords(word)) editDist2.addAll(suggestedWords(s));
            for(String str : editDist2) {
                if(trie.find(str) != null) {
                    if(trie.find(str).getValue() > 0) matches.add(str);
                }
            }
        }
        // conditionally choose the best word from those words that are in the Trie
        return getSuggestedWord(matches);
    }

    public String getSuggestedWord(TreeSet<String> suggestedWordSet) {
        Map<String, Integer> wordNodeMap = new HashMap<>();
        if(suggestedWordSet.size() == 1) return suggestedWordSet.first();
        else if(suggestedWordSet.size() > 1) {
            // Map the words to their frequencies
            for(String s : suggestedWordSet) wordNodeMap.put(s, trie.find(s).getValue());
            // Find the maximum frequency in the set of words
            int max = Collections.max(wordNodeMap.values());
            // Set to store the word with highest frequency and alphabetize if there are more than one words with same frequency
            TreeSet<String> maxFreqWord = new TreeSet<>();
            // Traverse through each map entry
            for(Map.Entry<String, Integer> entry : wordNodeMap.entrySet()) {
                if(entry.getValue() == max) maxFreqWord.add(entry.getKey());
            }
            // Return either the highest freq word, or alphabetized word if more than one word
            return maxFreqWord.first();
        }
        else {
            // return null if there is no word
            return null;
        }
    }

    public ArrayList<String> deletionDistance(String word) {
        // deletion distance of 1 from “bird” are “ird”, “brd”, “bid”, and “bir”
        ArrayList<String> deletionWords = new ArrayList<>();
        Set<String> generateStrings = new TreeSet<>();
        String frontSubStr, backSubStr, frontBack;
        for(int i = 0; i < word.length(); i++) {
            frontSubStr = word.substring(0, i);
            backSubStr = "";
            if(i + 1 < word.length()) backSubStr = word.substring(i + 1);
            frontBack = frontSubStr + backSubStr;
            generateStrings.add(frontSubStr); generateStrings.add(backSubStr); generateStrings.add(frontBack);
        }
        for(String s : generateStrings) if(s.length() == word.length() - 1) deletionWords.add(s);

        return deletionWords;
    }

    public ArrayList<String> transpositionDistance(String word) {
        ArrayList<String> transpositionWords = new ArrayList<>();
        char[] wordCharArray = word.toCharArray();
        char curLetter, nextLetter;
        for(int i = 0; i < wordCharArray.length; i++) {
            StringBuilder sb = new StringBuilder();
            curLetter = wordCharArray[i];
            if(i + 1 < wordCharArray.length) {
                nextLetter = wordCharArray[i + 1];
                wordCharArray[i] = nextLetter;
                wordCharArray[i+1] = curLetter;
                for(char c : wordCharArray) sb.append(c);
                transpositionWords.add(sb.toString());
            }
            wordCharArray = word.toCharArray();
        }
        return transpositionWords;
    }

    public ArrayList<String> alterationDistance(String word) {
        char[] wordCharArray = word.toCharArray();
        ArrayList<String> alterationWords = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            for(char c : alphabet) {
                StringBuilder sb = new StringBuilder();
                wordCharArray[i] = c;
                for(char ch : wordCharArray) sb.append(ch);
                alterationWords.add(sb.toString());
            }
            wordCharArray = word.toCharArray();
        }
        return alterationWords;
    }

    public ArrayList<String> insertionDistance(String word) {
        ArrayList<String> insertionWords = new ArrayList<>();

        for(int i = 0; i < word.length() + 1; i++) {
            for(char letter : alphabet) {
                StringBuilder sb = new StringBuilder(word);
                sb.insert(i, letter);
                insertionWords.add(sb.toString());
            }
        }
        return insertionWords;
    }

    public Set<String> suggestedWords(String word) {
        ArrayList<String> deletionWords = deletionDistance(word);
        ArrayList<String> insertionWords = insertionDistance(word);
        ArrayList<String> alterationWords = alterationDistance(word);
        ArrayList<String> transpositionWords = transpositionDistance(word);
        Set<String> suggestedWordSet = new TreeSet<>();

        suggestedWordSet.addAll(deletionWords);
        suggestedWordSet.addAll(insertionWords);
        suggestedWordSet.addAll(alterationWords);
        suggestedWordSet.addAll(transpositionWords);
        return suggestedWordSet;
    }
}
