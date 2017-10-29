import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordnet;
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        if (nouns == null) throw new IllegalArgumentException();
        int[] distance = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            for (int j = i; j < nouns.length; j++) {
                int dist = wordnet.distance(nouns[i], nouns[j]);
                distance[i] += dist;
                if (i != j) {
                    distance[j] += dist;
                }
            }
        }

        int maxDist = 0;
        int maxIndex = 0;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] > maxDist) {
                maxDist = distance[i];
                maxIndex = i;
            }
        }
        return nouns[maxIndex];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
