package core.basesyntax;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        MyTask task = new MyTask(-60, 45);
        Long result = forkJoinPool.invoke(task);
        System.out.println("Result = " + result);

        long sum = 0;
        for (int i = -60; i < 45; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
