package JBTests;

import org.junit.jupiter.api.Test;
import spell.Trie;
import spell.TrieNode;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {
    Trie trie = new Trie();

    @Test
    void addSingleWord() {
        trie.add("hello");
        int idxH = 'h' - 'a', idxE = 'e' - 'a', idxL = 'l'- 'a', idxO = 'o' - 'a';

        assertTrue(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).hasNodeAt(idxO));
        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getNodeAtIdx(idxO).getValue(), 1);
    }

    @Test
    void addSingleCapitalLetterWord() {
        trie.add("Josh");
        assertTrue(trie.getRootNode().getNodeAtIdx('j'-'a').getNodeAtIdx('o' - 'a').getNodeAtIdx('s' - 'a').hasNodeAt('h' - 'a'));
        assertEquals(trie.getRootNode().getNodeAtIdx('j'-'a').getNodeAtIdx('o' - 'a').getNodeAtIdx('s' - 'a').getNodeAtIdx('h' - 'a').getValue(), 1);
    }

    @Test
    void addSingleAllCapitalLetterWord() {
        trie.add("ATOM");
        assertTrue(trie.getRootNode().getNodeAtIdx('a'-'a').getNodeAtIdx('t' - 'a').getNodeAtIdx('o' - 'a').hasNodeAt('m' - 'a'));
        assertEquals(trie.getRootNode().getNodeAtIdx('a'-'a').getNodeAtIdx('t' - 'a').getNodeAtIdx('o' - 'a').getNodeAtIdx('m' - 'a').getValue(), 1);
    }

    @Test
    void addSingleWordTwice() {
        trie.add("hello");
        int idxH = 'h' - 'a', idxE = 'e' - 'a', idxL = 'l'- 'a', idxO = 'o' - 'a';

        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getNodeAtIdx(idxO).getValue(), 1);
        trie.add("hello");
        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getNodeAtIdx(idxO).getValue(), 2);

    }

    @Test
    void addTwoSimilarWords() {
        trie.add("hello");
        trie.add("hell");
        int idxH = 'h' - 'a', idxE = 'e' - 'a', idxL = 'l'- 'a', idxO = 'o' - 'a';

        assertTrue(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).hasNodeAt(idxO));
        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getNodeAtIdx(idxO).getValue(), 1);
        assertTrue(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).hasNodeAt(idxL));
        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getValue(), 1);
    }

    @Test
    void addTwoSimilarWordsTwice() {
        trie.add("hello");
        trie.add("hell");
        int idxH = 'h' - 'a', idxE = 'e' - 'a', idxL = 'l'- 'a', idxO = 'o' - 'a';

        assertTrue(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).hasNodeAt(idxO));
        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getNodeAtIdx(idxO).getValue(), 1);
        assertTrue(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).hasNodeAt(idxL));
        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getValue(), 1);

        trie.add("hello");
        trie.add("hell");
        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getValue(), 2);
        assertEquals(trie.getRootNode().getNodeAtIdx(idxH).getNodeAtIdx(idxE).getNodeAtIdx(idxL).getNodeAtIdx(idxL).getNodeAtIdx(idxO).getValue(), 2);
    }

    @Test
    void addMultipleWords() {
        String[] wordList = {"a", "apple", "Natthaya", "Thai", "Joshua", "a", "a", "BYU", "APPLe"};
        for(String word : wordList) {
            trie.add(word);
        }

        assertTrue(trie.getRootNode().hasNodeAt('a' - 'a'));
        assertEquals(trie.getRootNode().getNodeAtIdx('a' - 'a').getValue(), 3);
        assertTrue(trie.getRootNode().getNodeAtIdx('a' - 'a').getNodeAtIdx('p' - 'a').getNodeAtIdx('p' - 'a').getNodeAtIdx('l' - 'a').hasNodeAt('e' - 'a'));
        assertEquals(trie.getRootNode().getNodeAtIdx('a' - 'a').getNodeAtIdx('p' - 'a').getNodeAtIdx('p' - 'a').getNodeAtIdx('l' - 'a').getNodeAtIdx('e' - 'a').getValue(), 2);

    }

    @Test
    void findSingleWord() {
        trie.add("hello");
        TrieNode expectedNode = trie.getRootNode().getNodeAtIdx('h' - 'a'). getNodeAtIdx('e' - 'a').getNodeAtIdx('l' - 'a').getNodeAtIdx('l' - 'a').getNodeAtIdx('o' - 'a');
        assertEquals(trie.find("hello"), expectedNode);
    }

    @Test
    void findMultipleWords() {
        String[] wordList = {"hi", "Hello", "josh"};
        for(String s : wordList) trie.add(s);
        assertEquals(trie.find("hi"), trie.getRootNode().getNodeAtIdx('h' - 'a').getNodeAtIdx('i' - 'a'));
        assertEquals(trie.find("Hello"), trie.getRootNode().getNodeAtIdx('h' - 'a'). getNodeAtIdx('e' - 'a').getNodeAtIdx('l' - 'a').getNodeAtIdx('l' - 'a').getNodeAtIdx('o' - 'a'));
        assertEquals(trie.find("josh"), trie.getRootNode().getNodeAtIdx('j' - 'a'). getNodeAtIdx('o' - 'a').getNodeAtIdx('s' - 'a').getNodeAtIdx('h' - 'a'));
    }

    @Test
    void useFindToGetFrequencies() {
        trie.add("Hello");
        int wc = trie.find("Hello").getValue();
        assertEquals(wc, 1);
        trie.add("hello");
        wc = trie.find("hello").getValue();
        assertEquals(wc, 2);
        trie.add("hello"); trie.add("HELLO"); trie.add("HeLlO"); trie.add("hello");
        wc = trie.find("hello").getValue();
        assertEquals(wc, 6);
    }

    @Test
    void getWordCountForSingleWord() {
        trie.add("wazzup");
        assertEquals(trie.getWordCount(), 1);
    }

    @Test
    void getWordCountMultipleWordsNoDuplicates() {
        String[] wordList = {"a", "hello", "bob", "snake", "zoo", "elk", "hi"};
        for(String s : wordList) trie.add(s);
        assertEquals(7, trie.getWordCount());
    }

    @Test
    void getWordCountMultipleWordsDuplicates() {
        String[] wordList = {"a", "hello", "bob", "snake", "zoo", "elk", "hello"};
        for(String s : wordList) trie.add(s);
        assertEquals(6, trie.getWordCount());
    }

    @Test
    void getNodeCountSingleWord() {
        trie.add("Hello");
        assertEquals(5, trie.getNodeCount());
    }
}