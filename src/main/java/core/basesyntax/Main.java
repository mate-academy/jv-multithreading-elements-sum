package core.basesyntax;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        RecursiveTask<Long> myTask = new MyTask(0, 100);
        Long result = forkJoinPool.invoke(myTask);
        System.out.println("Result = " + result);
    }
}
