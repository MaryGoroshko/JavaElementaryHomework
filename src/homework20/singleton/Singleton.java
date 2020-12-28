package homework20.singleton;

public class Singleton {

    private static Singleton instance;
    private static int counterInt;

    public Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        System.out.println("From getInstance() Int counter " + ++counterInt);
        return instance;
    }
}
