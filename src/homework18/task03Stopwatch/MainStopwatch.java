package homework18.task03Stopwatch;

import java.util.concurrent.*;

public class MainStopwatch {

    public static void main(String[] args) {

        StopWatch task = new StopWatch();
        ExecutorService executor = Executors.newCachedThreadPool();
        final Future<Long> result = executor.submit(task);
        try {
            long stopWatch = result.get(10,TimeUnit.SECONDS);
            System.out.println("Elapsed time: " + stopWatch + " sec");
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Current thread was interrupted/cancelled. ");
            result.cancel(true);
            System.out.println("Stopwatch has been cancelled");
        } catch (TimeoutException e) {
            result.cancel(true);
            System.out.println("Stopwatch has timed out and cancelled");
        }
        executor.shutdown();
    }
}
