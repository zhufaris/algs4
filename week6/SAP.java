import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {

    private static final int INF = Integer.MAX_VALUE;
    private final Digraph graph;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        graph = new Digraph(G);
    }

    public int length(int v, int w) {
        verifyBound(v);
        verifyBound(w);
        BreadthFirstDirectedPaths vp = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wp = new BreadthFirstDirectedPaths(graph, w);

        return getMinPath(vp, wp, true);
    }

    public int ancestor(int v, int w) {
        verifyBound(v);
        verifyBound(w);
        BreadthFirstDirectedPaths vp = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wp = new BreadthFirstDirectedPaths(graph, w);

        return getMinPath(vp, wp, false);
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        verifyBound(v);
        verifyBound(w);
        BreadthFirstDirectedPaths vp = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wp = new BreadthFirstDirectedPaths(graph, w);

        return getMinPath(vp, wp, true);
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        verifyBound(v);
        verifyBound(w);
        BreadthFirstDirectedPaths vp = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wp = new BreadthFirstDirectedPaths(graph, w);

        return getMinPath(vp, wp, false);
    }

    private int getMinPath(BreadthFirstDirectedPaths vp, BreadthFirstDirectedPaths wp, boolean retLength) {
        int min = INF;
        int ancestor = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (vp.hasPathTo(i) && wp.hasPathTo(i)) {
                int vlen = vp.distTo(i);
                int wlen = wp.distTo(i);
                if (vlen + wlen < min) {
                    min = vlen + wlen;
                    ancestor = i;
                }
            }
        }
        if (retLength) return (min < INF)? min: -1;
        else return ancestor;
    }

    private void verifyBound(int i) {
        if (i < 0 || i >= graph.V()) throw new IllegalArgumentException();
    }

    private void verifyBound(Iterable<Integer> v) {
        for (Integer i: v) verifyBound(i);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        StdOut.printf("Starting");
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
