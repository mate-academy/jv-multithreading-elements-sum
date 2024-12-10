package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= THRESHOLD) {
            return sumTheNumbers(startPoint, finishPoint);
        } else {
            int middlePoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middlePoint);
            MyTask right = new MyTask(middlePoint, finishPoint);
            left.fork();
            right.fork();

            Long leftResult = left.join();
            Long rightResult = right.join();

            return leftResult + rightResult;
        }
    }

    private Long sumTheNumbers(int startPoint, int finishPoint) {
        return LongStream.range(startPoint, finishPoint)
                .sum();
    }
}
