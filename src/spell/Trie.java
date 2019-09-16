package spell;

public class Trie implements ITrie {
    TrieNode rootNode = new TrieNode();
    int wordCount, nodeCount;

    /*
    * int idx = letter - a
    * char c = index + a
    * */

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
        if(curNode.getValue() == 1) this.wordCount++;
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
}
