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
        trie.insert(new Protein("Alanine"), "gct", "gcc", "gca", "gcg");
        trie.insert(new Protein("Arginine"), "cgt", "cgc", "cga", "cgg", "aga", "agg");
        trie.insert(new Protein("Asparagine"), "aat", "aac");
        trie.insert(new Protein("Aspartic Acid"), "gat", "gac");
        trie.insert(new Protein("Cysteine"), "tgt", "tgc");
        trie.insert(new Protein("Glutamic Acid"), "gaa", "gag");
        trie.insert(new Protein("Glutamine"), "caa", "cag");
        trie.insert(new Protein("Glycine"), "ggt", "ggc", "gga", "ggg");
        trie.insert(new Protein("Histidine"), "cat", "cac");
        trie.insert(new Protein("Isoleucine"), "att", "atc", "ata");
        trie.insert(new Protein("Leucine"), "tta", "ttg", "ctt", "ctc", "cta", "ctg");
        trie.insert(new Protein("Lysine"), "aaa", "aag");
        trie.insert(new Protein("Methionine"), "atg");
        trie.insert(new Protein("Phenylanine"), "ttt", "ttc");
        trie.insert(new Protein("Proline"), "cct", "ccc", "cca", "ccg");
        trie.insert(new Protein("Serine"), "tct", "tcc", "tca", "tcg", "agt", "agc");
        trie.insert(new Protein("Stop"), "taa", "tag", "tga");
        trie.insert(new Protein("Threonine"), "act", "acc", "aca", "acg");
        trie.insert(new Protein("Tyrosine"), "tat", "tac");
        trie.insert(new Protein("Valine"), "gtt", "gtc", "gta", "gtg");
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
