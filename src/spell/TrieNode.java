package spell;

import java.util.Arrays;
import java.util.Objects;

public class TrieNode implements INode{
    public TrieNode[] alphaNodes = new TrieNode[26];
    public int frequency = 0;

    public TrieNode() { }

    @Override
    public int getValue() { return this.frequency; }

    public TrieNode[] getAlphabet() { return this.alphaNodes; }

    public TrieNode getNodeAtIdx(int idx) { return this.alphaNodes[idx]; }

    public void addNode(int idx) { if(this.getAlphabet()[idx] == null) this.getAlphabet()[idx] = new TrieNode(); }

    public void setFrequency(int frequency) { this.frequency = frequency; }

    public void incrementFrequency() { this.frequency++; }

    public boolean hasNodeAt(int idx) { return this.alphaNodes[idx] != null; }

    public char getChar(int idx) { return (char) (idx + 'a'); }

    @Override
    public boolean equals(Object o) {
        // Compare the nodes all the way down to the frequency
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrieNode trieNode = (TrieNode) o;
        return frequency == trieNode.frequency &&
                Arrays.equals(alphaNodes, trieNode.alphaNodes);
    }

    @Override
    public int hashCode() {
        // INTELLIJ generated this. Assuming it is efficient
        int result = Objects.hash(frequency);
        result = 31 * result + Arrays.hashCode(alphaNodes);
        return result;
    }
}
