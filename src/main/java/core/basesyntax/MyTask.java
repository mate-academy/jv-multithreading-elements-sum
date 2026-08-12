package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int DISTANCE = 10;

    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (calculateDistance() <= DISTANCE) {
            return calculateSum();
        } else {
            List<RecursiveTask<Long>> subTasks = createSubTasks();

            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
            }

            Long result = 0L;

            for (RecursiveTask<Long> task : subTasks) {
                result += task.join();
            }

            return result;
        }
    }

    private int calculateDistance() {
        return finishPoint - startPoint;
    }

    private Long calculateSum() {
        Long sum = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int middle = startPoint + (finishPoint - startPoint) / 2;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, middle);
        RecursiveTask<Long> second = new MyTask(middle, finishPoint);

        subTasks.add(first);
        subTasks.add(second);

        return subTasks;
    }
}
