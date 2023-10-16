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
        if (finishPoint - startPoint > 10) {
            int middle = (startPoint + finishPoint) / 2;

            MyTask first = new MyTask(startPoint, middle);
            MyTask second = new MyTask(middle, finishPoint);

            first.fork();
            second.fork();

            return first.join() + second.join();
        }

        return LongStream.range(startPoint, finishPoint).sum();
    }
}
