package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subIntervals = new ArrayList<>(createSubInterval());
            for (RecursiveTask<Long> subInterval : subIntervals) {
                subInterval.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subInterval : subIntervals) {
                result += subInterval.join();
            }
            return result;
        } else {
            long sum = 0;
            for (long i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubInterval() {
        List<RecursiveTask<Long>> subIntervals = new ArrayList<>();
        int interval = (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, finishPoint - interval);
        RecursiveTask<Long> second = new MyTask(finishPoint - interval, finishPoint);
        subIntervals.add(first);
        subIntervals.add(second);
        return subIntervals;
    }
}
