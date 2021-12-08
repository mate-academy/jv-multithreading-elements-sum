package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static int maxValue;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        if (Thread.currentThread().getName().equals("main")) {
            maxValue = Math.max(startPoint,finishPoint);
        }

    }

    @Override
    protected Long compute() {
        if (Math.max(Math.abs(finishPoint),Math.abs(startPoint))
                - Math.min(Math.abs(finishPoint),Math.abs(startPoint)) > 10) {
            System.out.println(Thread.currentThread().getName()
                    + " Splitting task "
                    + startPoint
                    + " , finishPoint: "
                    + finishPoint);
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> task: subTasks) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> task: subTasks) {
                result += task.join();
            }
            return result;
        } else {
            System.out.println("RecursiveAction: Doing myself, startPoint : "
                    + startPoint
                    + " , finishPoint: "
                    + finishPoint
                    + " maxValue: "
                    + maxValue);
            int value = 0;
            if (finishPoint < startPoint) {
                return (long) value;
            } else if (finishPoint == maxValue && (finishPoint - startPoint) <= 10) {
                value = (startPoint + finishPoint - 1) * (finishPoint - startPoint) / 2;
            } else {
                value = (startPoint + finishPoint) * (finishPoint - startPoint + 1) / 2;
            }
            return (long) value;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int sp = startPoint;
        int fp;
        if (startPoint < 0 && finishPoint < 0) {
            fp = (Math.abs(finishPoint) - (Math.abs(finishPoint) - Math.abs(startPoint)) / 2) * -1;
        } else {
            fp = finishPoint - (finishPoint - startPoint) / 2;
        }
        RecursiveTask<Long> first = new MyTask(sp, fp);
        RecursiveTask<Long> second = new MyTask((fp + 1), finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
