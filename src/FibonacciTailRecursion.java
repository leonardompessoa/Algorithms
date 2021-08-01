import java.math.BigInteger;

public class FibonacciTailRecursion {

    public static void main(String[] args) {
        FibonacciTailRecursion fib = new FibonacciTailRecursion();
        System.out.println(fib.fibonacci(10));
    }

    public BigInteger fibonacci(long n) {
        return go(n, BigInteger.ZERO,BigInteger.ONE);
    }

    private BigInteger go(long n, BigInteger current, BigInteger next) {
        System.out.println(current);
        if(n == 0) return current;
        if (n == 1) return next;
        return go(n-1, next, current.add(next));
    }
}
