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
        Long result = Long.valueOf(0);
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> list = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> recursiveTask : list) {
                recursiveTask.fork();
                result += recursiveTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

        private List<RecursiveTask<Long>> createSubTasks () {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>();
            RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
            RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
            subTasks.add(first);
            subTasks.add(second);
            return subTasks;
        }
    }
