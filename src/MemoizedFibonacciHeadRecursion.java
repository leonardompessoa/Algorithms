import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MemoizedFibonacciHeadRecursion {

    private Map<Long, BigInteger> memo = new HashMap<>();

    public static void main(String[] args) {

        MemoizedFibonacciHeadRecursion fib = new MemoizedFibonacciHeadRecursion();
        System.out.println(fib.fibonacci(10));
    }

    public BigInteger fibonacci(long n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        BigInteger number = memo.get(n);
        if(number != null) {
            return number;
        }
        return fibonacci(n - 1).add(fibonacci(n - 2));
    }

}
