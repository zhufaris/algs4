import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node pre = null;
        Node next = null;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;            
            return item;
        }
    }

    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = old;
        if (old == null)  last = first;
        else old.pre = first;
        N++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node old = last;
        last = new Node();
        last.item = item;
        last.pre = old;
        if (old == null) first = last;
        else old.next = last; 
        N++;
    }

    public Item removeFirst() {
        if (first == null) throw new NoSuchElementException();
        Item item = first.item;
        if (first.next == null) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.pre = null;
        }
        N--;
        return item;
    }

    public Item removeLast() {
        if (last ==  null) throw new NoSuchElementException();
        Item item = last.item;
        if (last.pre == null){
            last = null;
            first = null;
        }
        else {
            last = last.pre;
            last.next = null;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main (String[] args) {
        Deque<String> d = new Deque<>();
        System.out.println(d.isEmpty());
        System.out.println(d.size());
        d.addLast("a");
        d.addFirst("z");
        d.addLast("b");
        d.addLast("c");
        d.addFirst("e");

        System.out.println(d.size());
        
        for(String s : d) {
            System.out.println(s);
        }
        System.out.println(d.removeFirst());
        System.out.println(d.size());
        System.out.println(d.removeFirst());
        System.out.println(d.size());
        System.out.println(d.removeFirst());
        System.out.println(d.isEmpty());
        System.out.println(d.size());
        System.out.println(d.removeLast());
        d.addLast("g");
        System.out.println(d.N);
        for(String s : d) {
            System.out.println(s);
        }
    }
}