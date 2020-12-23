package homework19;

import homework19.task01Point.Point;
import homework19.task01Point.PointSynchMethod;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        new Main().task1();
    }

    public void task1() {
        Point point = new Point();
        PointSynchMethod synchMethod = new PointSynchMethod();

        ExecutorService executor = Executors.newFixedThreadPool(5);
        Future<String> result = null;
        for (int i = 0; i < 2000; i++) {
            result = executor.submit(() -> {
                point.move(1, 1);
                synchMethod.move(1,1);
                return "-";
            });
        }
        try {
            result.get(3, TimeUnit.SECONDS);
            System.out.println("Final result without synchronized  " + point.getX() + " : " + point.getY());
            System.out.println("Final result using synchronized method  " + synchMethod.getX() + " : " + synchMethod.getY());
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
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
