package core.basesyntax;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int[] ints = madeArray(startPoint, finishPoint);
        if (ints.length < 10) {
            return (long) Arrays.stream(ints).sum();
        }
        MyTask first = new MyTask(startPoint, ((startPoint + finishPoint) / 2));
        MyTask second = new MyTask(((startPoint + finishPoint) / 2), finishPoint);
        first.fork();
        second.fork();
        return first.join() + second.join();
    }

    private int[] madeArray(int startPoint, int finishPoint) {
        int[] array = new int[Math.abs(finishPoint - startPoint)];
        int counter = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            array[counter++] = i;
        }
        return array;
    }
}
