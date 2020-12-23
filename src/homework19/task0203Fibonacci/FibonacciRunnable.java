package homework19.task0203Fibonacci;

public class FibonacciRunnable implements Runnable {

    int number;
    int num1 = 0;
    int num2 = 1;

    public FibonacciRunnable(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Start of calculation Fibonacci " + number);
            if (number == 0 || number == 1) {
                System.out.println(number);
            }
            int fibonacciSum = 0;
            for (int i = 0; i < number; i++) {
                fibonacciSum = num1 + num2;
                System.out.println("Sum= " + num1 + " + " + num2);
                num1 = num2;
                num2 = fibonacciSum;
            }
            System.out.println(fibonacciSum);
        }
    }
}
