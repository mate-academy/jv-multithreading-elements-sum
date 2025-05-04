package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final List<Long> longs;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        longs = new ArrayList<>();
        for (long i = startPoint; i < finishPoint; i++) {
            longs.add(i);
        }
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= 10) {
            return calculateSum();
        } else {
            int middle = (finishPoint + startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);
            left.fork();
            right.fork();
            long leftResult = left.join();
            long rightResult = right.join();
            return leftResult + rightResult;
        }
    }

    private Long calculateSum() {
        return longs
                .stream()
                .mapToLong(Long::longValue)
                .sum();
    }
}
