import java.util.Iterator;

public class ArrayStack<T> implements Stack<T> {

    private T[] stack;
    private int N;

    public ArrayStack() {
        stack = (T[]) new Object[1];
    }

    @Override
    public void push(T t) {
        if (stack.length == N) {
            resize(2 * stack.length);
        }
        stack[N++] = t;
    }

    @Override
    public T pop() {
        T item = stack[--N];
        stack[N] = null;
        if (N > 0 && N == stack.length / 4) {
            resize(stack.length / 2);
        }
        return item;

    }

    private void resize(int capacity) {
        T[] copyArray = (T[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copyArray[i] = stack[i];
        }
        stack = copyArray;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {

        int index = N;

        @Override
        public boolean hasNext() {
            return index > 0;
        }

        @Override
        public T next() {
            return stack[--index];
        }
    }


    public static void main(String[] args) {
        Stack<Integer> ints = new ArrayStack<>();
        ints.push(1);
        ints.push(2);
        ints.push(3);
        ints.push(4);
        ints.push(5);
        ints.push(6);

        Iterator<Integer> iterator = ints.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


    }

}

