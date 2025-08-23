package core.basesyntax;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        MyTask myTask = new MyTask(47, 47);
        Long result = forkJoinPool.invoke(myTask);
        System.out.println("Result = " + result);
    }
}
