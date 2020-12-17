package homework18.task01Factorial;

import java.util.concurrent.Callable;

//Реализовать подсчет факториала в отдельном классе с поддержкой отмены.
//Подробно выводить каждый шаг программы.
public class Factorial implements Callable<Long> {

    long number;

    public Factorial(long number) {
        this.number = number;
    }

    @Override
    public Long call() throws Exception {
        System.out.println("Start of calculation Factorial " + number);
        long fact = 1;
        if (number < 0)
            throw new Exception("Number must be positive");
        for (long count = number; count > 1; count--) {
            System.out.println(fact + " * " + count);
            fact *= count;
        }
        return fact;
    }
}
