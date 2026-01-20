package core.basesyntax;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        long l = System.nanoTime();
        System.out.println("Start " + l);
        MyTask task = new MyTask(0, 1000000000);
        Long result = forkJoinPool.invoke(task);
        System.out.println("Result = " + result + "\nTime = " + (System.nanoTime() - l));
    }
}
