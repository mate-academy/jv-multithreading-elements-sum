package core.basesyntax;

import java.util.concurrent.ForkJoinPool;
import org.junit.Assert;
import org.junit.Test;

public class MyTaskTest {
    private static final String MESSAGE = "Your task should return %d, but the result was %d";
    private MyTask task;

    @Test
    public void compute_ordinaryCase_ok() {
        task = new MyTask(0, 100);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Long expected = 4950L;
        Long actual = forkJoinPool.invoke(task);

        Assert.assertEquals(String.format(MESSAGE, expected, actual), expected, actual);
    }

    @Test
    public void compute_smallDifference_ok() {
        task = new MyTask(30, 36);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Long expected = 195L;
        Long actual = forkJoinPool.invoke(task);

        Assert.assertEquals(String.format(MESSAGE, expected, actual), expected, actual);
    }

    @Test
    public void compute_zeroDifference_ok() {
        task = new MyTask(47, 47);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Long expected = 0L;
        Long actual = forkJoinPool.invoke(task);

        Assert.assertEquals(String.format(MESSAGE, expected, actual), expected, actual);
    }

    @Test
    public void compute_reverseOrder_ok() {
        task = new MyTask(100, 0);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Long expected = 0L;
        Long actual = forkJoinPool.invoke(task);

        Assert.assertEquals(String.format(MESSAGE, expected, actual), expected, actual);
    }

    @Test
    public void compute_negativeNumbers_ok() {
        task = new MyTask(-121, -76);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Long expected = -4455L;
        Long actual = forkJoinPool.invoke(task);

        Assert.assertEquals(String.format(MESSAGE, expected, actual), expected, actual);
    }

    @Test
    public void





































































    compute_mixedNumbers_ok() {
        task = new MyTask(-60, 45);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Long expected = -840L;
        Long actual = forkJoinPool.invoke(task);

        Assert.assertEquals(String.format(MESSAGE, expected, actual), expected, actual);
    }
}
