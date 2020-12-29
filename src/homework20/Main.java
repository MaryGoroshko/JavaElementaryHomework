package homework20;

import homework20.singleton.Singleton;
import homework20.singleton.SingletonSynch;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int MAX_COUNT = 50_000;
    //    public int counter = 0;
    private AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
//        new Main().run();
//        new Main().singleton();
        new Main().print();
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

    public void singleton() {
        CountDownLatch doneSignal = new CountDownLatch(2_000);
        ExecutorService executor = Executors.newFixedThreadPool(2_000);

        final Runnable task = () -> {
            Singleton.getInstance();
            SingletonSynch.getInstance();
            doneSignal.countDown();
        };

        for (int i = 0; i < 2_000; i++) {
            executor.submit(task);
        }

        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Output:
//        Int counter: 1998
//        Atomic counter: 2000
    }

    public void print() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        Runnable task = () -> {
            CountDownLatch startSignal = new CountDownLatch(1);
            executor.execute(() -> {
                try {
                    startSignal.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("Hello ");
            });

            executor.execute(() -> {
                try {
                    startSignal.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("World ");
            });
            startSignal.countDown();
        };

        ScheduledFuture<?> result = executor.scheduleAtFixedRate(
                task, 5, 5, TimeUnit.SECONDS);

        executor.schedule(() -> {
            result.cancel(true);
            executor.shutdown();
        }, 30, TimeUnit.SECONDS);
    }
}
