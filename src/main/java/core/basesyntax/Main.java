package core.basesyntax;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        MyTask task = new MyTask(-121, -76);
        Long result = forkJoinPool.invoke(task);
        System.out.println("Result = " + result);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }

    }
}
