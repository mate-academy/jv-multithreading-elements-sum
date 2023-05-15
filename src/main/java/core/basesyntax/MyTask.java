package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
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
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int reduced = finishPoint - ((finishPoint - startPoint) / 2);
        RecursiveTask<Long> first = new MyTask(startPoint, reduced);
        RecursiveTask<Long> second = new MyTask(reduced, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
