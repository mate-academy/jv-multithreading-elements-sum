package core.basesyntax;

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
            return computeInSubTasks();
        } else {
            return sumNumbersInRange(startPoint, finishPoint);
        }
    }

    private long computeInSubTasks() {
        int middlePoint = (finishPoint + startPoint) / 2;
        MyTask leftBranch = new MyTask(startPoint, middlePoint);
        leftBranch.fork();
        startPoint = middlePoint;
        Long rightBranchResult = compute();
        return leftBranch.join() + rightBranchResult;
    }

    private Long sumNumbersInRange(int startPoint, int finishPoint) {
        long result = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }

}
