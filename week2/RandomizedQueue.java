import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
    private int N;
    private Item[] rq;

    public RandomizedQueue() {
        N = 0;
        rq = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    private boolean isFull() {
        return N == rq.length;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isFull()) resize(2*rq.length);
        rq[N++] = item;
    } 

    private void resize(int n) {
        Item[] copy = (Item[]) new Object[n];
        for (int i = 0; i < N; i++)
            copy[i] = rq[i];
        rq = copy;
    }

    private int randomIndex() {
        if (N < 1) throw new NoSuchElementException();
        return StdRandom.uniform(N);
    }

    public Item dequeue() {
        int index = randomIndex();
        Item temp = rq[index];
        rq[index] = rq[--N];
        rq[N] = null;
        if (N == 4 * rq.length) resize(rq.length / 2);
        return temp;
    }

    public Item sample() {
        int index = randomIndex();
        return rq[index];
    }

    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int index = -1;
        public boolean hasNext() {
            return (index + 1) < N;
        }

        public Item next() {
            index++;
            if (index >= N) throw new NoSuchElementException();
            return rq[index];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main (String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");
        rq.enqueue("e");

        for (String s: rq) {
            System.out.println(s);
        }

        rq.enqueue("e");
        rq.enqueue("f");
        rq.enqueue("g");
        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");
        rq.enqueue("e");
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.size());
        for (String s: rq) {
            System.out.println(s);
        }
    }
}