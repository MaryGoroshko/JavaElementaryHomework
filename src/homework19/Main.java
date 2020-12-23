package homework19;

import homework19.task01Point.*;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        new Main().task1();
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
                synchMethod.move(1,1);
                synchBlock1.move(1,1);
                PointSynchBlock2.move(synchBlock2,1,1);
                PointSynchMethodX.move(synchMethodX,1,1);
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
}
