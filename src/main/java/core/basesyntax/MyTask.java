package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int DISTANCE = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (finishPoint - startPoint > 10) {
            System.out.println(String.format("Splitting task with start %d and finish %d",
                    startPoint, finishPoint));
            List<MyTask> subTasks = new ArrayList<>(createSubTasks());
            for (MyTask task: subTasks) {
                task.fork();
            }
            for (MyTask task: subTasks) {
                result += task.join();
            }
        } else {
            System.out.println(String.format("Computing task with start %d and finish %d",
                    startPoint, finishPoint));
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<MyTask> createSubTasks() {
        MyTask subTask1 = new MyTask(startPoint, startPoint + DISTANCE);
        MyTask subTask2 = new MyTask(startPoint + DISTANCE, finishPoint);

        List<MyTask> subTasks = new ArrayList<>();
        subTasks.add(subTask1);
        subTasks.add(subTask2);
        return subTasks;
    }
}
