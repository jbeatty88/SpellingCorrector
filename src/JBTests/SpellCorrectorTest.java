package JBTests;

import org.junit.jupiter.api.Test;
import spell.SpellCorrector;

import static org.junit.jupiter.api.Assertions.*;

class SpellCorrectorTest {

    @Test
    void deletionDistance() {
        SpellCorrector corrector = new SpellCorrector();
        corrector.deletionDistance("bird", 1);
    }
}