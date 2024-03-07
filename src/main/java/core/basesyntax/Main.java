package core.basesyntax;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        MyTask task = new MyTask(0, 100);
        Long result = forkJoinPool.invoke(task);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Result = " + result);
    }
}
