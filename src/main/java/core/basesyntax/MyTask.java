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
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> task : subTasks) {
                result += task.join();
            }
            return result;
        } else {
            long result = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> result = new ArrayList<>();
        int middle = (startPoint + finishPoint) / 2;
        result.add(new MyTask(startPoint, middle));
        result.add(new MyTask(middle, finishPoint));
        return result;
    }
}
