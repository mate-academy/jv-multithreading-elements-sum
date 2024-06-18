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
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks(
                    startPoint, finishPoint));
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks(int startPoint, int finishPoint) {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int mid = (startPoint + finishPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, mid);
        RecursiveTask<Long> second = new MyTask(mid, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
