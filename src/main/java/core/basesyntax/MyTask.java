package core.basesyntax;

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
        if (startPoint >= finishPoint) {
            return 0L;
        }
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return calculateFromStartToEndExclusive(startPoint, finishPoint);
        }
    }

    private Long calculateFromStartToEndExclusive(int startPoint, int finishPoint) {
        return (long) (finishPoint + startPoint) * (finishPoint - startPoint + 1) / 2 - finishPoint;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        return List.of(
                new MyTask(startPoint, (startPoint + finishPoint) / 2),
                new MyTask((startPoint + finishPoint) / 2, finishPoint)
        );
    }
}
