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
            List<MyTask> subTasks = new ArrayList<>(createSubTasks());
            for (MyTask subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (MyTask subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
           return sum();
        }
    }

    private List<MyTask> createSubTasks() {
        List<MyTask> subTasks = new ArrayList<>();
        int half = (startPoint + finishPoint) / 2;
        subTasks.add(new MyTask(startPoint, half));
        subTasks.add(new MyTask(half, finishPoint));
        return subTasks;
    }

    private Long sum() {
        int sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return (long) sum;
    }
}
