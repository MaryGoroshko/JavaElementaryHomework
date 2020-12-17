package homework18.task02Fibonacci;

import java.util.concurrent.*;

public class MainFibonacci {

    public static void main(String[] args) {

        FibonacciNumbers task = new FibonacciNumbers(10);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Integer> result = executor.submit(task);
        try {
            int fib = result.get(1, TimeUnit.SECONDS);
            System.out.println("Fibonacci: " + fib);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Current thread was interrupted/cancelled. " +
                    "Last saved numbers: " + task.num1 + ", " + task.num2);
            result.cancel(true);
            System.out.println("Fibonacci has been cancelled");
        } catch (TimeoutException e) {
            result.cancel(true);
            System.out.println("Fibonacci has timed out and cancelled");
        }
        executor.shutdown();
    }
}
