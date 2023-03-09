package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
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
        long result = 0;
        // write your code here
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subtasks = createSubtasks();
            subtasks.forEach(ForkJoinTask::fork);
            for (RecursiveTask<Long> task :
                    subtasks) {
                result += task.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubtasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        int middlePoint = finishPoint - (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> second = new MyTask(middlePoint, finishPoint);
        tasks.add(first);
        tasks.add(second);
        return tasks;
    }
}
