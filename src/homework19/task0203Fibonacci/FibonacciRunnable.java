package homework19.task0203Fibonacci;

import java.math.BigInteger;

public class FibonacciRunnable implements Runnable {

    private int number;

    public FibonacciRunnable(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Start of calculation Fibonacci " + number);
            if (number <= 1) System.out.println(BigInteger.valueOf(number));

            BigInteger previous = BigInteger.ZERO, next = BigInteger.ONE, sum;

            for (int i = 2; i <= number; i++) {

                sum = previous;
                previous = next;
                next = sum.add(previous);
                System.out.println("Sum= " + sum + " + " + previous);
            }

            System.out.println("Result: " + next);
        }
    }
}
