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
        if (finishPoint - startPoint <= 10) {
            return LongStream.range(startPoint, finishPoint)
                    .sum();
        }
        int middle = (startPoint + finishPoint) / 2;
        return new MyTask(startPoint, middle).fork().join()
                + new MyTask(middle, finishPoint).fork().join();
    }
}
