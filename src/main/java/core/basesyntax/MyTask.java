package core.basesyntax;

import java.util.ArrayList;
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
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                recursiveTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                result += recursiveTask.join();
            }
            return result;
        } else {
            Long result = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> recursiveActions = new ArrayList<>();
        int difference = finishPoint - startPoint;
        RecursiveTask<Long> first = new MyTask(startPoint, finishPoint - difference / 2);
        RecursiveTask<Long> second = new MyTask(finishPoint - difference / 2, finishPoint);
        recursiveActions.add(first);
        recursiveActions.add(second);
        return recursiveActions;
    }
}
