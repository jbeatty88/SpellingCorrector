package JBTests;

import org.junit.jupiter.api.Test;
import spell.TrieNode;

import static org.junit.jupiter.api.Assertions.*;

class TrieNodeTest {
    TrieNode n = new TrieNode();
    @Test
    void getValue() { assertEquals(n.getValue(), 0); }

    @Test
    void getAlphabet() { assertEquals(n.getAlphabet(), n.getAlphabet()); }

    @Test
    void insertNodeIntoAlphabetArray() {
        n.getAlphabet()[0] = new TrieNode();
        assertEquals(n.getAlphabet()[0].getValue(), 0);
    }

    @Test
    void getNodeAtIdx() { assertEquals(n.getNodeAtIdx(0), n.getAlphabet()[0]); }

    @Test
    void setWordCount() {
        n.setFrequency(8);
        assertEquals(n.getValue(), 8);
    }

    @Test
    void incrementWordCount() {
        n.incrementFrequency();
        assertEquals(n.getValue(), 1);
    }

    @Test
    void addNode() {
        n.addNode(5);
        assertEquals(n.getAlphabet()[5].getValue(), 0 );
    }

}