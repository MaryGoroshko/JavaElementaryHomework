package homework20;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int MAX_COUNT = 50_000;
    //    public int counter = 0;
    private AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        new Main().run();
    }

    //Выполнить ожидание завершения задач с помощью CountDownLatch.
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(200);

        int expectedCount = (int) (Math.random() * MAX_COUNT + 2_000);

        CountDownLatch doneSignal = new CountDownLatch(expectedCount);

        final Runnable task = () -> {
//            synchronized (Main.this) {
//                counter++;
//            }
            counter.getAndIncrement();
            doneSignal.countDown();
        };

        System.out.println("Expected: " + expectedCount);

        for (int i = 0; i < expectedCount; i++) {
            executor.submit(task);
        }

        try {
//            Thread.sleep(1_000);
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Actual: " + counter);
    }
}
