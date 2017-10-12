import java.util.Iterator;

public class LinkedListStack<T> implements Stack<T> {

    private Node first;

    private class Node {
        T item;
        Node next;
    }

    @Override
    public void push(T t) {
        Node oldFirst = first;
        first = new Node();
        first.item = t;
        first.next = oldFirst;
    }

    @Override
    public T pop() {
        T item = first.item;
        first = first.next;
        return item;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {

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
        Stack<Integer> ints = new LinkedListStack<>();
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
