package homework19.task01Point;

public class PointSynchMethodX {

    private int x;
    private int y;

    public static synchronized void move(PointSynchMethodX synchMethodX, int dx, int dy) {
        synchMethodX.x += dx;
        synchMethodX.y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
