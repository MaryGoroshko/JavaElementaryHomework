package homework20;

import homework20.singleton.Singleton;
import homework20.singleton.SingletonSynch;

import java.io.OutputStream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int MAX_COUNT = 50_000;
    private int counterInt = 0;
    private AtomicInteger counterAtomic = new AtomicInteger(0);

    public static void main(String[] args) {
//        new Main().count();
//        new Main().singleton();
        new Main().print();
    }

    /**
     * Создать 2000 одновременных задач, которые увеличивают целочисленный счетчик на 1.
     * Подтвердить проблему атомарности. Проверить ее решение с помощью volatile или Atomic классов.
     * Выполнить ожидание завершения задач с помощью CountDownLatch.
     */
    public void count() {

        ExecutorService executor = Executors.newFixedThreadPool(200);

        int expectedCount = (int) (Math.random() * MAX_COUNT + 2_000);

        CountDownLatch doneSignal = new CountDownLatch(expectedCount);

        final Runnable task = () -> {
            counterInt++;
            counterAtomic.getAndIncrement();
            doneSignal.countDown();
        };

        System.out.println("Expected: " + expectedCount);

        for (int i = 0; i < expectedCount; i++) {
            executor.submit(task);
        }

        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        System.out.println("Counter using int: " + counterInt);
        System.out.println("Counter using Atomic: " + counterAtomic);

//        Output:
//        Expected: 6980
//        Counter using int: 6979
//        Counter using Atomic: 6980
    }

    /**
     * Получить доступ к singleton-объекту с “ленивой” (lazy) инициализацией
     * из множества потоков с использованием барьера инициализации при помощи класса CountDownLatch.
     * Подтвердить проблему атомарности. Решить ее одним из известных способов.
     */
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
        executor.shutdown();

//        Output:
//        From getInstance() Int counter 1999
//        From getInstance() Atomic counter 2000
    }


    public void print() {

        HelloWorld helloWorld = new HelloWorld();
        CountDownLatch startSignal = new CountDownLatch(1);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        Runnable task = () -> {
            executor.execute(() -> {
                try {
                    startSignal.await();
                    helloWorld.printHello();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            executor.execute(() -> {
                try {
                    startSignal.await();
                    helloWorld.printWorld();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            startSignal.countDown();
        };

        ScheduledFuture<?> result = executor.scheduleAtFixedRate(
                task, 0, 10, TimeUnit.SECONDS);

        executor.schedule(() -> {
            result.cancel(true);
            executor.shutdown();
        }, 60, TimeUnit.SECONDS);

//      Output
//      World Hello Hello World Hello World Hello World Hello World Hello World Hello World
    }
}
