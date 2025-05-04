package core.basesyntax;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        MyTask task = new MyTask(0, 100);
        Long result = forkJoinPool.invoke(task);
        System.out.println("Result = " + result);
        System.out.println(LongStream.range(0, 100).sum());
    }
}
