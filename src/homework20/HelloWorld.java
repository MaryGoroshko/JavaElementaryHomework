package homework20;

public class HelloWorld implements Runnable{

//    private final CountDownLatch startSignal;
//
//    public HelloWorld(CountDownLatch startSignal) {
//        this.startSignal = startSignal;
//    }

    public void printHello() {
            System.out.print("Hello ");
    }

    public void printWorld() {
            System.out.print("World\n");
    }

    @Override
    public void run() {
        new HelloWorld().printHello();
        new HelloWorld().printWorld();
    }
}
