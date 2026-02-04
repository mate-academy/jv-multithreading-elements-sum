package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int diff = finishPoint - startPoint;
        if (diff > 10) {
            List<MyTask> mySubTasks = subTasks();
            mySubTasks.forEach(MyTask::fork);
            return mySubTasks.stream().mapToLong(MyTask::join).sum();
        }
        return LongStream.range(startPoint, finishPoint).sum();
    }

    private List<MyTask> subTasks() {
        int splitPoint = (startPoint + finishPoint) / 2;
        return List.of(
                new MyTask(startPoint, splitPoint),
                new MyTask(splitPoint, finishPoint)
        );
    }
}
