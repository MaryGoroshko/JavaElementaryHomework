package homework18.task02Fibonacci;

import java.util.concurrent.Callable;

//Реализовать подсчет чисел Фибоначчи с сохранением последней пары чисел в полях
//класса задачи (отдельный класс для задачи; поддержка отмены).
//Подробно выводить каждый шаг программы.
public class FibonacciNumbers implements Callable<Integer> {

    int number;
    int num1 = 0;
    int num2 = 1;

    public FibonacciNumbers(int number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Start of calculation Fibonacci " + number);
        if (number == 0 || number == 1) {
            return number;
        }

        int fibonacciSum = 0;
        for (int i = 0; i < number; i++) {
            fibonacciSum = num1 + num2;
            System.out.println("Sum= " + num1 + " + " + num2);
            num1 = num2;
            num2 = fibonacciSum;
        }
        return fibonacciSum;
    }
}
