package spell;

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
}
