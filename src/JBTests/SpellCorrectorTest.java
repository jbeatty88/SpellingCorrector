package JBTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import spell.SpellCorrector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

        String suggestion = corrector.suggestSimilarWord("floabt");
        assertEquals("float", suggestion);
    }

    @Test
    void testSuggestAgain() throws IOException {
        corrector.useDictionary("notsobig.txt");

        String suggestion = corrector.suggestSimilarWord("sar");

        assertEquals("war", suggestion);

    }
}