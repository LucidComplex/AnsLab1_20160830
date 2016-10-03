import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tan on 9/27/16.
 */
public class Translator {
    Trie trie;
    public Translator() {
        trie = new Trie();
        trie.insert(new Protein("A"), "gct", "gcc", "gca", "gcg");
        trie.insert(new Protein("R"), "cgt", "cgc", "cga", "cgg", "aga", "agg");
        trie.insert(new Protein("N"), "aat", "aac");
        trie.insert(new Protein("D"), "gat", "gac");
        trie.insert(new Protein("C"), "tgt", "tgc");
        trie.insert(new Protein("E"), "gaa", "gag");
        trie.insert(new Protein("Q"), "caa", "cag");
        trie.insert(new Protein("G"), "ggt", "ggc", "gga", "ggg");
        trie.insert(new Protein("H"), "cat", "cac");
        trie.insert(new Protein("I"), "att", "atc", "ata");
        trie.insert(new Protein("L"), "tta", "ttg", "ctt", "ctc", "cta", "ctg");
        trie.insert(new Protein("K"), "aaa", "aag");
        trie.insert(new Protein("M"), "atg");
        trie.insert(new Protein("F"), "ttt", "ttc");
        trie.insert(new Protein("P"), "cct", "ccc", "cca", "ccg");
        trie.insert(new Protein("S"), "tct", "tcc", "tca", "tcg", "agt", "agc");
        trie.insert(new Protein("_"), "taa", "tag", "tga");
        trie.insert(new Protein("T"), "act", "acc", "aca", "acg");
        trie.insert(new Protein("Y"), "tat", "tac");
        trie.insert(new Protein("V"), "gtt", "gtc", "gta", "gtg");
    }

    public List<Protein> translate(Sequence s) throws StringIndexOutOfBoundsException {
        String text = s.getData();
        List<Protein> proteins = new ArrayList<>();
        for (int i = 0; i < text.length(); i += 3) {
            proteins.add(trie.get(text.substring(i, i + 3)));
        }
        return proteins;
    }
}
