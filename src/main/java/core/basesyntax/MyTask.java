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
        if (Math.abs(finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>();
            int portion = (finishPoint - startPoint) / 2;
            subTasks.add(new MyTask(startPoint, startPoint + portion));
            subTasks.add(new MyTask(startPoint + portion, finishPoint));
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return LongStream.rangeClosed(startPoint, finishPoint - 1)
                    .sum();
        }
    }
}
