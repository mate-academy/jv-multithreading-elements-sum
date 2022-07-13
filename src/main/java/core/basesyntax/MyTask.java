package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int POINT_DIFF_THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > POINT_DIFF_THRESHOLD) {
            List<RecursiveTask<Long>> subTasks = createSubTasks(startPoint, finishPoint);
            for( RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for( RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks(int startPoint, int finishPoint) {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int midPoint = startPoint + (finishPoint - startPoint) / 2 ;
        subTasks.add(new MyTask(startPoint, midPoint));
        subTasks.add(new MyTask(midPoint, finishPoint));
        return subTasks;
    }
}
