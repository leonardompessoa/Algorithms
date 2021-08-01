public class FibonacciHeadRecursion {

    public static void main(String[] args) {

        FibonacciHeadRecursion fib = new FibonacciHeadRecursion();
        System.out.println(fib.fibonacci(10));
    }

    public long fibonacci(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

}
