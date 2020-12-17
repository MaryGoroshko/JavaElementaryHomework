package homework18.task04Streams;

import java.util.concurrent.*;

public class MainStreams {

    public static void main(String[] args) {

        FileStreams task = new FileStreams();
        ExecutorService executor = Executors.newCachedThreadPool();
        final Future<?> result = executor.submit(task);
        try {
            result.get(1, TimeUnit.MILLISECONDS);
            System.out.println("File successfully copied");
        } catch (InterruptedException e) {
            System.out.println("Current thread was interrupted/cancelled");
            result.cancel(true);
            System.out.println("Copy file has been cancelled");
        } catch (ExecutionException e) {
            System.err.println("Internal exception: " + e.getMessage());
        } catch (TimeoutException e) {
            result.cancel(true);
            System.out.println("Copy file has timed out and cancelled");
        }
        executor.shutdown();
    }
}
