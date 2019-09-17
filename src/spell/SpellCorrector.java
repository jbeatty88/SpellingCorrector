package spell;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {
    public Trie trie = new Trie();
    char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        try(Scanner sc = new Scanner(new FileReader(dictionaryFileName))) {
            while(sc.hasNext()) trie.add(sc.next());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        Set<String> matches = new TreeSet<>();
        String word = inputWord.toLowerCase();
        if(trie.find(word) != null) return word;
        else {
            for(String s : suggestedWords(word)) {
                if(trie.find(s) != null) matches.add(s);
            }
            System.out.println(matches.size());
        }
        return " ";
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
