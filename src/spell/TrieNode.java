package spell;

import java.util.Arrays;
import java.util.Objects;

public class TrieNode implements INode{
    TrieNode[] alphaNodes = new TrieNode[26];
    int frequency = 0;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrieNode trieNode = (TrieNode) o;
        return frequency == trieNode.frequency &&
                Arrays.equals(alphaNodes, trieNode.alphaNodes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(frequency);
        result = 31 * result + Arrays.hashCode(alphaNodes);
        return result;
    }
}
