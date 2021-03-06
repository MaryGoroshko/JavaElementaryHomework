package homework19;

import homework19.task01Point.*;
import homework19.task0203Fibonacci.FibonacciRunnable;
import homework19.task0203Fibonacci.FibonacciThread;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        new Main().task1();
//        new Main().task2();
//        new Main().task3();
//        new Main().task2a();
//        new Main().task3a();
    }

    public void task1() {
        Point point = new Point();
        PointSynchMethod synchMethod = new PointSynchMethod();
        PointSynchBlock1 synchBlock1 = new PointSynchBlock1();
        PointSynchBlock2 synchBlock2 = new PointSynchBlock2();
        PointSynchMethodX synchMethodX = new PointSynchMethodX();

        ExecutorService executor = Executors.newFixedThreadPool(5);
        Future<String> result = null;
        for (int i = 0; i < 2000; i++) {
            result = executor.submit(() -> {
                point.move(1, 1);
                synchMethod.move(1, 1);
                synchBlock1.move(1, 1);
                PointSynchBlock2.move(synchBlock2, 1, 1);
                PointSynchMethodX.move(synchMethodX, 1, 1);
                return "-";
            });
        }
        try {
            result.get(3, TimeUnit.SECONDS);
            System.out.println("Final result without synchronized  " + point.getX() + " : " + point.getY());
            System.out.println("Final result using synchronized method  " + synchMethod.getX() + " : " + synchMethod.getY());
            System.out.println("Final result using synchronized block #1  " + synchBlock1.getX() + " : " + synchBlock1.getY());
            System.out.println("Final result using synchronized block #2  " + synchBlock2.getX() + " : " + synchBlock2.getY());
            System.out.println("Final result using synchronized method X  " + synchMethodX.getX() + " : " + synchMethodX.getY());
        } catch (InterruptedException e) {
            System.out.println("Current thread has been interrupted/canceled");
            result.cancel(true);
            System.out.println("Point move has been cancelled");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.err.println("Internal exception: " + e.getMessage());
            e.printStackTrace();
        } catch (TimeoutException e) {
            result.cancel(true);
            System.out.println("Point move has timed out and canceled");
            System.out.println("Last result without synchronized  " + point.getX() + " : " + point.getY());
            System.out.println("Last result using synchronized method  " + synchMethod.getX() + " : " + synchMethod.getY());
            System.out.println("Last result using synchronized block #1  " + synchBlock1.getX() + " : " + synchBlock1.getY());
            System.out.println("Last result using synchronized block #2  " + synchBlock2.getX() + " : " + synchBlock2.getY());
            System.out.println("Last result using synchronized method X  " + synchMethodX.getX() + " : " + synchMethodX.getY());
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private void task2() {
        FibonacciThread thread = new FibonacciThread(15);
        thread.start();
        try {
            Thread.sleep(3000);
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("Current thread has been interrupted/cancelled");
            e.printStackTrace();
        }
    }

    //второй вариант решения:
    private void task2a() {
        FibonacciThread thread = new FibonacciThread(1525);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<?> result = executor.submit(thread);
        try {
            result.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Current thread has been interrupted/canceled. ");
            result.cancel(true);
        } catch (TimeoutException e) {
            result.cancel(true);
            System.out.println("Thread has timed out and cancelled");
        }
        executor.shutdown();
    }

    private void task3() {
        FibonacciRunnable task = new FibonacciRunnable(1555);
        Thread thread = new Thread(task);
        thread.start();
        try {
            Thread.sleep(3000);
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("Current thread has been interrupted/cancelled");
            e.printStackTrace();
        }
    }

    //второй вариант решения задачи прерывания потока и получения результата
    private void task3a() {
        FibonacciRunnable task = new FibonacciRunnable(1555);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<?> result = executor.submit(task);
        try {
            result.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Current thread has been interrupted/canceled.");
            result.cancel(true);
        } catch (TimeoutException e) {
            result.cancel(true);
            System.out.println("Thread has timed out and cancelled");
        }
        executor.shutdown();
    }
}
