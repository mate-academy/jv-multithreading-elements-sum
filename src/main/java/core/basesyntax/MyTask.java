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
        long result = 0;
        if (Math.abs(finishPoint) - Math.abs(startPoint) > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> subTasks : recursiveTasks) {
                subTasks.fork();
            }
            for (RecursiveTask<Long> subTasks : recursiveTasks) {
                result += subTasks.join();
            }
        } else {
            result = LongStream.range(startPoint, finishPoint)
                    .sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + 9);
        RecursiveTask<Long> second = new MyTask(startPoint + 9, finishPoint);
        System.out.println("current startpoint = " + startPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
