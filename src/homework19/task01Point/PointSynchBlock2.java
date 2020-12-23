package homework19.task01Point;

public class PointSynchBlock2 {

    private static final Object lock = new Object();
    private int x;
    private int y;

    public static void move(PointSynchBlock2 synchBlock2, int dx, int dy) {
        synchronized (lock) {
            synchBlock2.x += dx;
            synchBlock2.y += dy;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
