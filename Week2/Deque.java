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

        }

        public Item next() {
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
        if (old == null) {
            first = last;
        }
        else {
            old.next = last; 
        }
        N++;
    }

    public Item removeFirst() {
        if (first == null) throw new NoSuchElementException();
        Item item = first.item;
        Node oldFirst = first;
        first = first.next;
        oldFirst = null;
        if (first == null) last = null;
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
        else{
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
        d.addLast("a");
        d.addFirst("z");
        d.addLast("b");
        d.addLast("c");
        
        for(String s : d) {
            System.out.println(s);
        }
        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());

    }

}