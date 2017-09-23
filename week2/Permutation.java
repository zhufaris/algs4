import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main (String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int size = Integer.parseInt(args[0]);
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }

        for (int i = 0; i < size; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}