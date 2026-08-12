package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            int center = (startPoint + finishPoint) / 2;
            RecursiveTask<Long> first = new MyTask(startPoint, center);
            RecursiveTask<Long> second = new MyTask(center, finishPoint);
            return first.fork().join() + second.fork().join();
        } else {
            return LongStream.range(startPoint, finishPoint)
                    .sum();
        }
    }
}
