package spell;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Trie implements ITrie {
    public TrieNode rootNode = new TrieNode();
    public int wordCount = 0, nodeCount = 1, aggregateWordFrequency = 0;
    public Set<String> addedWords = new TreeSet<>();

    /*
    * int idx = letter - a
    * char c = index + a
    * */

    public Trie() { }

    @Override
    public void add(String word) {
        // Keep track of where we are at in the Trie
        TrieNode curNode = rootNode;
        String lowerCaseWord = word.toLowerCase();
        int idx;
        for(char c : lowerCaseWord.toCharArray()) {
            idx = c - 'a';
            // If the node doesn't exist in the Trie
            if(!curNode.hasNodeAt(idx)) {
                // Create and add the node
                curNode.addNode(idx);
                // Increment node count
                this.nodeCount++;
            }
            // Move forward through the Trie according to the word being added
            curNode = curNode.getNodeAtIdx(idx);
        }
        // Increment frequency each time add is called (even works for dup adds)
        curNode.incrementFrequency();
        // Keep track of all word frequencies together (helps with comparing Tries)
        this.aggregateWordFrequency++;
        // If this is a new word, increment the word count
        if(curNode.getValue() == 1) this.wordCount++;
        // Keep track of added words for debugging purposes as well as comparing Tries
        this.addedWords.add(word);
    }

    @Override
    public INode find(String word) {
        // Keep track of where you are at in the Trie
        TrieNode curNode = rootNode;
        int idx;
        String lowerCaseWord = word.toLowerCase();
        for(char c : lowerCaseWord.toCharArray()) {
            idx = c - 'a';
            // If the node doesn't exists, the word for sure isn't in the Trie -> NULL
            if(!curNode.hasNodeAt(idx)) return null;
            // If the node does exists, go to that node until the last node
            curNode = curNode.getNodeAtIdx(idx);
        }
        // If the last node has a frequency then it's a word and was found
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
        // The word we are looking at
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        // Helper will help make this recursive
        toStringHelper(currWord, output, rootNode);

        return output.toString();
    }

    private static void toStringHelper(StringBuilder currWord, StringBuilder output, TrieNode rootNode) {
        if(rootNode.getValue() != 0) output.append(currWord.toString()).append('\n');
        for(int i = 0; i < 26; i++) {
            if(rootNode.hasNodeAt(i)) {
                currWord.append(rootNode.getChar(i));
                // Recursively go through the Trie
                toStringHelper(currWord, output, rootNode.getNodeAtIdx(i));
                // remove childs letter from curWord as we backtrack
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
                // Recursively compare two nodes of separate Tries
                equalsHelper(rootNode1.getNodeAtIdx(i), rootNode2.getNodeAtIdx(i));
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        // This isn't recursive, but I don't know exactly how you would make this recursive.
        // wordCount, nodeCount, and even toString aren't necessarily unique to any one Trie
        // What could be more unique is specific frequencies at every node which we do check
        // in the equals for nodes that we use in our Trie equals. Equals should never show two Tries
        // as the same since we compare each node down to the frequency.
        return Objects.hash(this.wordCount, this.nodeCount, this.toString());
    }
}
