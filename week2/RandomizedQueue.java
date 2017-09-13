import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
    private int N;
    private int capacity;
    private Item[] rq;

    public RandomizedQueue() {
        N = 0;
        capacity = 2;
        rq = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    private boolean isFull() {
        return N == capacity;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isFull()) resize(2*capacity);
        rq[N++] = item;
    } 

    private void resize(int n) {
        Item[] copy = (Item[]) new Object[n];
        for (int i = 0; i < N; i++)
            copy[i] = rq[i];
        rq = copy;
        capacity = n;
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
        if (capacity > 4*N) resize(capacity / 2);
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
        System.out.println(rq.dequeue());
        rq.enqueue("f");
        rq.enqueue("g");
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.size());
        for (String s: rq) {
            System.out.println(s);
        }
    }
}