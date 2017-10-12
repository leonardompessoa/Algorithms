public interface Stack<T> extends Iterable<T> {
    void push(T t);

    T pop();
}
