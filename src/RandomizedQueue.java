import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private Integer[] dequeueSelected;
    private int tail;
    private int size;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        dequeueSelected = new Integer[1];
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        queue[tail++] = item;
        size++;
        if (queue.length == tail) {
            resize(2 * queue.length);
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int random = getRandom();
        Item item = queue[random];
        queue[random] = null;
        if (size <= queue.length / 4) {
            resize(queue.length / 2);
        }
        size--;
        return item;
    }

    private int getRandom() {
        int random = StdRandom.uniform(tail);
        if (dequeueSelected[random] == null) {
            dequeueSelected[random] = random;
        } else {
            random = getRandom();
        }
        return random;
    }

    private void resize(int capacity) {
        Item[] copyArray = (Item[]) new Object[capacity];
        dequeueSelected = new Integer[capacity];
        tail = 0;
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] != null) {
                copyArray[tail++] = queue[i];
            }
        }
        queue = copyArray;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int random = StdRandom.uniform(tail);
        Item item = queue[random];
        if (item == null) {
            item = sample();
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {

        int count = size;
        Integer[] selected;

        public RandomQueueIterator() {
            resize(size);
            selected = new Integer[size];
        }

        private int getIteratorRandom() {
            int random = StdRandom.uniform(size);
            if (selected[random] == null) {
                selected[random] = random;
            } else {
                random = getIteratorRandom();
            }
            return random;
        }

        @Override
        public boolean hasNext() {
            return count != 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = queue[getIteratorRandom()];
            count--;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

}
