package homework20;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Deadlock {

    public Object lock1 = new Object();
    public Object lock2 = new Object();

    public static void main(String args[]) {
        new Deadlock().runAll();
    }

    private void runAll() {
        DeadLock1 deadLock1 = new DeadLock1();
        DeadLock2 deadLock2 = new DeadLock2();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(deadLock1);
        executor.submit(deadLock2);
    }

    private class DeadLock1 implements Runnable {
        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("DeadLock1: Start running");
                synchronized (lock2) {
                    System.out.println("DeadLock1: Continue running");
                }
            }
        }
    }

    private class DeadLock2 implements Runnable {
        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("DeadLock2: Start running");
                synchronized (lock1) {
                    System.out.println("DeadLock2: Continue running");
                }
            }
        }
    }

//    Output:
//    DeadLock1: Start running
//    DeadLock2: Start running
}
