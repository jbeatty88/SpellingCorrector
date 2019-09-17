package JBTests;

import org.junit.jupiter.api.Test;
import spell.SpellCorrector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

class SpellCorrectorTest {
    SpellCorrector corrector = new SpellCorrector();

    @Test
    void deletionDistance() {
        corrector.deletionDistance("bird");
    }

    @Test
    void transpositionDistance() {
        corrector.transpositionDistance("house");
    }

    @Test
    void alterationDistance() {
        corrector.alterationDistance("top");
    }

    @Test
    void insertionDistance() {
        corrector.insertionDistance("askg");
    }

    @Test
    void testSuggest() throws IOException {
        corrector.useDictionary("words.txt");
//        System.out.println(corrector.trie.toString());
//        Set<String> wordSet = corrector.suggestedWords("floabt");
//        for(String s : wordSet) System.out.println(s);

        String suggestion = corrector.suggestSimilarWord("floabt");
//        System.out.printf("Suggested word: %s", suggestion);
    }
}