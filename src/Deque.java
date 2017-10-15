import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node before;
    }

    public Deque() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldhead = head;
        head = new Node();
        head.item = item;
        head.next = oldhead;
        head.before = null;
        if (isEmpty()) {
            tail = head;
        } else {
            if(oldhead != null) {
                oldhead.next = tail;
                oldhead.before = head;
            }
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldlast = tail;
        tail = new Node();
        tail.item = item;
        tail.next = null;
        tail.before = oldlast;
        if (isEmpty()) {
            head = tail;
        } else {
            if(oldlast != null) {
                oldlast.next = tail;
            }
        }

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = head.item;
        head = head.next;
        if(head != null) {
            head.before = null;
        } else {
            tail = null;
        }
        size--;
        return item;
    }                // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = tail.item;
        tail = tail.before;
        if(tail != null) {
            tail.next = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

}
