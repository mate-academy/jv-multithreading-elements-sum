package core.basesyntax;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

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
        Long sum = 0L;
        if (finishPoint - startPoint > THRESHOLD) {
            List<MyTask> tasks = createSubTasks();
            tasks.forEach(ForkJoinTask::fork);
            for (MyTask task : tasks) {
                sum += task.join();
            }
            return sum;
        }

        while (startPoint < finishPoint) {
            sum += startPoint++;
        }
        return sum;
    }

    private List<MyTask> createSubTasks() {
        int mid = startPoint + (finishPoint - startPoint) / 2;
        MyTask left = new MyTask(startPoint, mid);
        MyTask right = new MyTask(mid, finishPoint);
        return List.of(left, right);
    }
}
