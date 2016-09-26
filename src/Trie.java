import java.util.List;

/**
 * Created by tan on 9/27/16.
 */
public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(Protein p, String... keys) {
        for (String key : keys) {
            TrieNode t = traverse(key);
            t.element = p;
        }
    }

    public Protein get(String key) {
        TrieNode t = traverse(key);
        return t.element;
    }

    private TrieNode traverse(String key) {
        TrieNode t = root;
        for (char c : key.toCharArray()) {
            if (t.children == null) {
                t.children = new TrieNode[4];
                for (int i = 0; i < 4; i++) {
                    t.children[i] = new TrieNode();
                }
            }
            t = t.getNode(c);
        }
        return t;
    }
}

class TrieNode {
    protected Protein element;
    protected TrieNode[] children;

    public TrieNode getNode(char c) {
        switch (c) {
            case 'a':
                return children[0];
            case 'c':
                return children[1];
            case 't':
                return children[2];
            case 'g':
                return children[3];
        }
        return null;
    }
}

