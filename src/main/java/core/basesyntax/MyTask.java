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
            List<RecursiveTask<Long>> taskList = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subtask : taskList) {
                subtask.fork();
            }
            long result = 0L;
            for (RecursiveTask<Long> subtask : taskList) {
                result += subtask.join();
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

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        subTasks.add(new MyTask(startPoint, finishPoint - (finishPoint - startPoint) / 2));
        subTasks.add(new MyTask(finishPoint - (finishPoint - startPoint) / 2, finishPoint));
        return subTasks;
    }
}
