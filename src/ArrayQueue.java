import java.util.Iterator;

public class ArrayQueue<T> implements Queue<T> {

    private T[] queue;
    private int head, tail;

    public ArrayQueue() {
        queue = (T[]) new Object[1];
    }

    @Override
    public void enqueue(T item) {
        queue[tail++] = item;
        if (queue.length == tail) {
            resize(2 * queue.length);
        }
    }

    @Override
    public T dequeue() {
        T item = queue[head];
        queue[head] = null;
        head++;
        if (tail > 0 && head % tail == tail / 2) {
            resize((queue.length / 2));
        }
        return item;
    }


    private void resize(int capacity) {
        T[] copyArray = (T[]) new Object[capacity];
        head = 0;
        tail = 0;
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] != null) {
                copyArray[tail++] = queue[i];
            }
        }
        queue = copyArray;
    }

    @Override
    public boolean isEmpty() {
        return  queue.length == 0|| queue[head] == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T> {

        int current = head;

        @Override
        public boolean hasNext() {
            return current < queue.length && queue[current] != null;
        }

        @Override
        public T next() {
            T item = queue[current++];
            return item;
        }
    }

    public static void main(String[] args) {
        Queue<Integer> ints = new ArrayQueue<>();
        ints.enqueue(1);
        ints.enqueue(2);
        ints.enqueue(3);
        ints.enqueue(4);
        ints.enqueue(5);
        ints.enqueue(6);
        ints.enqueue(7);


        Iterator<Integer> iterator = ints.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        while (!ints.isEmpty()) {
            System.out.println(ints.dequeue());
        }

    }

}
