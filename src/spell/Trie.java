package spell;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Trie implements ITrie {
    TrieNode rootNode = new TrieNode();
    int wordCount = 0, nodeCount = 1, aggregateWordFrequency = 0;
    Set<String> addedWords = new TreeSet<>();

    /*
    * int idx = letter - a
    * char c = index + a
    * */

    public Trie() { }

    @Override
    public void add(String word) {
        TrieNode curNode = rootNode;
        String lowerCaseWord = word.toLowerCase();
        int idx;
        for(char c : lowerCaseWord.toCharArray()) {
            idx = c - 'a';
            if(!curNode.hasNodeAt(idx)) {
                curNode.addNode(idx);
                this.nodeCount++;
            }
            curNode = curNode.getNodeAtIdx(idx);
        }
        curNode.incrementFrequency();
        this.aggregateWordFrequency++;
        if(curNode.getValue() == 1) this.wordCount++;
        this.addedWords.add(word);
    }

    @Override
    public INode find(String word) {
        TrieNode curNode = rootNode;
        int idx;
        String lowerCaseWord = word.toLowerCase();
        for(char c : lowerCaseWord.toCharArray()) {
            idx = c - 'a';
            if(!curNode.hasNodeAt(idx)) return null;
            curNode = curNode.getNodeAtIdx(idx);
        }
        if(curNode.getValue() > 0) return curNode;
        return null;
    }

    @Override
    public int getWordCount() { return this.wordCount; }

    @Override
    public int getNodeCount() { return this.nodeCount; }

    public TrieNode getRootNode() { return this.rootNode; }

    @Override
    public String toString() {
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toStringHelper(currWord, output, rootNode);

        return output.toString();
    }

    private static void toStringHelper(StringBuilder currWord, StringBuilder output, TrieNode rootNode) {
        if(rootNode.getValue() != 0) output.append(currWord.toString()).append('\n');
        for(int i = 0; i < 26; i++) {
            if(rootNode.hasNodeAt(i)) {
                currWord.append(rootNode.getChar(i));
                toStringHelper(currWord, output, rootNode.getNodeAtIdx(i));
                // remove childs letter from curWord
                currWord.setLength(currWord.length() - 1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        // Quick test
        if (this == o) return true;
        // If the object is null or not even an item of this type
        if (o == null || getClass() != o.getClass()) return false;
        // Test for identical values
        Trie trie = (Trie) o;
        return this.wordCount == trie.wordCount &&
                this.nodeCount == trie.nodeCount &&
                equalsHelper(rootNode, trie.rootNode);
    }

    private boolean equalsHelper(TrieNode rootNode1, TrieNode rootNode2) {
        for(int i = 0; i < 26; i++) {
            if(rootNode1.hasNodeAt(i) && rootNode2.hasNodeAt(i)) {
                if(!rootNode1.equals(rootNode2)) return false;
                equalsHelper(rootNode1.getNodeAtIdx(i), rootNode2.getNodeAtIdx(i));
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.wordCount, this.nodeCount, this.toString());
    }
}
