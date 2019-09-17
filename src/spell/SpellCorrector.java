package spell;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    Trie trie = new Trie();
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        try(Scanner sc = new Scanner(new FileReader(dictionaryFileName))) {
            while(sc.hasNext()) trie.add(sc.next());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        if(trie.find(inputWord).getValue() > 0) return inputWord.toLowerCase();
        return null;
    }

    public ArrayList<String> deletionDistance(String word, int editDist) {
        // deletion distance of 1 from “bird” are “ird”, “brd”, “bid”, and “bir”
        ArrayList<String> deletionWords = new ArrayList<>();
        String subStr1, subStr2, deletionWord;
        for(int i = 0; i < word.length(); i++) {
            subStr1 = word.substring(i);
//            subStr2 = word.substring(i, word.length());
            deletionWord = subStr1;
            deletionWords.add(deletionWord);
        }

        for(String s : deletionWords) System.out.println(s);
        return deletionWords;
    }

    public String[] transpositionDistance(int editDist) {
        return null;
    }

    public String[] alterationDistance(int editDist) {
        return null;
    }

    public String[] insertionDistance(int editDist) {
        return null;
    }
}
