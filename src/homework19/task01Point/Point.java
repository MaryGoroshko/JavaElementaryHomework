package homework19.task01Point;

public class Point {

    private int x;
    private int y;

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

