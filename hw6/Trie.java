import java.util.HashMap;

public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            TrieNode next;
            if (children.containsKey(c)) {
                next = children.get(c);
            } else {
                next = new TrieNode(c);
                children.put(c, next);
            }

            children = next.children;

            if (i == word.length() - 1) {
                next.isLeaf = true;
            }
        }
    }

    public boolean startsWith(String prefix) {
        if (searchNode(prefix) == null) {
            return false;
        }

        return true;
    }

    public TrieNode searchNode(String prefix) {
        HashMap<Character, TrieNode> children = root.children;
        TrieNode node = null;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (children.containsKey(c)) {
                node = children.get(c);
                children = node.children;
            } else {
                return null;
            }
        }

        return node;
    }

    private class TrieNode {
        private char c;
        HashMap<Character, TrieNode> children = new HashMap<>();
        boolean isLeaf;

        public TrieNode() {}

        public TrieNode(char c) {
            this.c = c;
        }
    }

}