package homework18.task03Stopwatch;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;

//Реализовать секундомер
//(отдельный класс для задачи; поддержка отмены). Подробно выводить каждый шаг программы.
public class StopWatch implements Callable<Long> {

    private LocalDateTime start;

    @Override
    public Long call() {
        System.out.println("Start");
        start = LocalDateTime.now();
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println(getTime() + " seconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        return getTime();
    }

    //у меня не получилось выполнить эту задачу через nanotime, если сделать
    //long nanotime = System.nanoTime() - this.start;
    //то это отрабатывает некорретно и выводит результат: 1 sec, 2 sec, 0 sec, 1 sec
    //поэтому использовала потокобезопасные ChronoUnit и LocalDateTime.

    public long getTime() {
        return ChronoUnit.SECONDS.between(start, LocalDateTime.now());
    }
}
