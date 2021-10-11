package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAX_WORKLOAD = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > MAX_WORKLOAD) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
            }
            long answer = 0;
            for (RecursiveTask<Long> task : subTasks) {
                answer += task.join();
            }
            return answer;
        } else {
            long answer = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                answer += i;
            }
            return answer;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTask = new ArrayList<>();
        int newPoint = (startPoint + finishPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, newPoint);
        RecursiveTask<Long> second = new MyTask(newPoint, finishPoint);
        subTask.add(first);
        subTask.add(second);
        return subTask;
    }
}
