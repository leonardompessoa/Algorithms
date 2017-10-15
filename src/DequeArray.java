import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeArray<Item> {

    private Item[] deque;
    private int head, tail;
    private int size;

    public DequeArray() {
        deque = (Item[]) new Object[1];
    }                        // construct an empty deque

    public boolean isEmpty() {
        return size == 0;
    }                 // is the deque empty?

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }

        if (head > 0) {
            deque[--head] = item;
        } else if (!isEmpty()) {
            Item[] copyArray = (Item[]) new Object[deque.length + 1];
            copyArray[head] = item;
            tail = 1;
            for (int i = 0; i < deque.length; i++) {
                if (deque[i] != null) {
                    copyArray[tail++] = deque[i];
                }
            }
            deque = copyArray;
        } else {
            deque[head] = item;
        }
        size++;
    }

    public void addLast(Item item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            deque[tail] = item;
        } else {
            if (deque.length == tail) {
                resize(2 * deque.length);
            }
            deque[tail++] = item;
        }
        size++;
    }          // add the item to the end

    public Item removeFirst() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = deque[head];
        deque[head] = null;
        size--;
        head++;
        if (tail > 0 && head % tail == tail / 2) {
            resize((deque.length / 2));
        }

        return item;
    }                // remove and return the item from the front

    public Item removeLast() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        if (tail > 0) {
            --tail;
        }
        Item item = deque[tail];
        deque[tail] = null;
        if (tail > 0 && head % tail == tail / 2) {
            resize((deque.length / 2));
        }
        size--;
        return item;
    }                 // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        int current = head;

        @Override
        public boolean hasNext() {
            return current < deque.length && deque[current] != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = deque[current++];
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }         // return an iterator over items in order from front to end

    private void resize(int capacity) {
        if (capacity == 0) {
            capacity = 1;
        }
        Item[] copyArray = (Item[]) new Object[capacity];
        head = 0;
        tail = 0;
        for (int i = 0; i < deque.length; i++) {
            if (deque[i] != null) {
                copyArray[tail++] = deque[i];
            }
        }
        deque = copyArray;
    }

    public static void main(String[] args) {

        DequeArray<Integer> dequeArray = new DequeArray<>();
        dequeArray.addFirst(1);
        dequeArray.removeFirst();
        dequeArray.addFirst(2);
        dequeArray.removeLast();
        dequeArray.addLast(3);
        dequeArray.removeFirst();
        dequeArray.addLast(4);
        dequeArray.removeLast();

        Iterator<Integer> dequeIterator = dequeArray.iterator();
        while (dequeIterator.hasNext()) {
            System.out.println(dequeIterator.next());
        }

    }

}
