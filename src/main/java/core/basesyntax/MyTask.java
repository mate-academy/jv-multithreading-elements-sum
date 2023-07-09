package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        correctMixedNumbersRange();
    }

    @Override
    protected Long compute() {
        long diff = finishPoint - startPoint;
        if (startPoint > finishPoint) {
            return 0L;
        }
        if (diff > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return (long) ((startPoint + finishPoint - 1) / 2.0 * diff);
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (finishPoint + startPoint) / 2);
        RecursiveTask<Long> second = new MyTask((finishPoint + startPoint) / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }

    private void correctMixedNumbersRange() {
        if (startPoint < 0 && finishPoint > 0) {
            if (Math.abs(startPoint) > Math.abs(finishPoint)) {
                finishPoint = (finishPoint - 1) * -1;
            } else {
                startPoint = ((startPoint * -1) + 1);
            }
        }
    }
}
