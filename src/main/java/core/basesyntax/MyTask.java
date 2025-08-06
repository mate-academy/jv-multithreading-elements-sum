package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int threshold = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (startPoint > finishPoint) {
            return 0L;
        }
        if (finishPoint - startPoint < threshold) {
            return sum(startPoint, finishPoint);
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftSubTask = new MyTask(startPoint, middle);
            MyTask rightSubTask = new MyTask(middle, finishPoint);

            leftSubTask.fork();
            Long rightResult = rightSubTask.compute();
            Long leftResult = leftSubTask.join();
            return leftResult + rightResult;
        }

    }

    private Long sum(int startPoint, int finishPoint) {
        Long sum = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
