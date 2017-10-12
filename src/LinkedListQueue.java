import java.util.Iterator;

public class LinkedListQueue<T> implements Queue<T> {

    private Node first;
    private Node last;

    private class Node {
        T item;
        Node next;
    }

    @Override
    public void enqueue(T item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }

    }

    @Override
    public T dequeue() {
        T item = first.item;
        first = first.next;
        if(isEmpty()) {
            last = null;
        }
        return item;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T> {

        Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Queue<Integer> ints = new LinkedListQueue<>();
        ints.enqueue(1);
        ints.enqueue(2);
        ints.enqueue(3);
        ints.enqueue(4);
        ints.enqueue(5);
        ints.enqueue(6);

        Iterator<Integer> iterator = ints.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
