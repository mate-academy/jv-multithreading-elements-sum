package core.basesyntax;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        RecursiveTask<Long> task = new MyTask(-121, -76);
        Long result = forkJoinPool.invoke(task);
        System.out.println("Result = " + result);
    }
}
