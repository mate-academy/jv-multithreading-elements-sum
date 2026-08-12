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
        int countOfElements = finishPoint - startPoint;
        List<Integer> elementsList = new ArrayList<>();
        for (int i = startPoint; i < finishPoint; i++) {
            elementsList.add(i);
        }

        if (countOfElements >= 10) {
            List<RecursiveTask<Long>> subTask = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> task : subTask) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> task : subTask) {
                result += task.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subtask = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        subtask.add(first);
        subtask.add(second);
        return subtask;
    }
}
