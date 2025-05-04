package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= 10) {
            return LongStream.range(startPoint, finishPoint).sum();
        } else {
            List<RecursiveTask<Long>> subTasks = createSubTask();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            return subTasks.stream().mapToLong(ForkJoinTask::join).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTask = new ArrayList<>();
        int step = (finishPoint - startPoint) / 2;
        MyTask first = new MyTask(startPoint, startPoint + step);
        MyTask second = new MyTask(startPoint + step, finishPoint);
        subTask.add(first);
        subTask.add(second);
        return subTask;
    }
}
