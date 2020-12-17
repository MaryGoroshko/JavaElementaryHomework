package homework18.task01Factorial;

import java.util.concurrent.*;

public class MainFactorial {

    public static void main(String[] args) {

        Factorial task = new Factorial(5);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Long> result = executor.submit(task);
        try {
            long factorial = result.get(1, TimeUnit.NANOSECONDS);
            System.out.println("Factorial: " + factorial);
        } catch (InterruptedException e) {
            System.out.println("Current thread was interrupted/cancelled");
            result.cancel(true);
            System.out.println("Factorial has been cancelled");
        } catch (ExecutionException e) {
            System.err.println("Internal factorial exception: " + e.getMessage());
        } catch (TimeoutException e) {
            result.cancel(true);
            System.out.println("Factorial has timed out and cancelled");
        }
        executor.shutdown();
    }
}
