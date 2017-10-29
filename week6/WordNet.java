import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;
import java.util.ArrayList;
import java.util.HashMap;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private Digraph graph;
    private ArrayList<String> synsetList;
    private HashMap<String, Bag<Integer>> synsetMap;
    private int[] outcount;
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        synsetList = new ArrayList<>();
        synsetMap = new HashMap<>();

        readInput(synsets);
        initGraph(hypernyms);
        if (graph == null) throw new IllegalArgumentException("Graph is null");
        sap = new SAP(graph);
        if (!isRootedDAG()) throw new IllegalArgumentException();
    }

    private void initGraph(String hypernyms) {
        graph = new Digraph(synsetList.size());
        outcount = new int[synsetList.size()];

        In hypernymsIn = new In(hypernyms);
        while (hypernymsIn.hasNextLine()) {
            String line = hypernymsIn.readLine();
            String[] elements = line.split(",");
            int from = Integer.parseInt(elements[0]);
            outcount[from] += elements.length - 1;

            for (int i = 1; i < elements.length; i++) {
                int to = Integer.parseInt(elements[i]);
                graph.addEdge(from, to);
            }
        }
        hypernymsIn.close();
    }

    private void readInput(String synsets) {

        In synsetIn = new In(synsets);
        while(synsetIn.hasNextLine()) {
            String line = synsetIn.readLine();
            String[] elements = line.split(",");
            synsetList.add(elements[1]);
            int id = Integer.parseInt(elements[0]);
            for (String syn : elements[1].split(" ")) {
                if (!synsetMap.containsKey(syn)) {
                    synsetMap.put(syn, new Bag<>());
                }
                synsetMap.get(syn).add(id);
            }
        }
        synsetIn.close();
    }

    private boolean isRootedDAG() {
        int rootnum = 0;
        for (int i = 0; i < outcount.length; i++) {
            if (outcount[i] == 0) rootnum++;
        }

        if (rootnum != 1) return false;
        if (new DirectedCycle(graph).hasCycle()) return false;
        return true;
    }


    public Iterable<String> nouns() {
        return synsetMap.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return synsetMap.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        Iterable<Integer> A = synsetMap.get(nounA);
        Iterable<Integer> B = synsetMap.get(nounB);

        return sap.length(A,B);
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        Iterable<Integer> A = synsetMap.get(nounA);
        Iterable<Integer> B = synsetMap.get(nounB);

        int ancestor = sap.ancestor(A, B);
        return synsetList.get(ancestor);

    }

    public static void main(String[] args) {
        WordNet wn = new WordNet("week6/wordnet/synsets.txt", "week6/wordnet/hypernyms.txt");
        StdOut.println(wn.nouns());
        StdOut.println(wn.sap("help", "Cochise"));
        StdOut.println(wn.distance("help", "Cochise"));
        StdOut.println(wn.isNoun("help"));
    }
}
