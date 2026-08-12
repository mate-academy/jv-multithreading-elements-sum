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
        if (finishPoint - startPoint <= 10) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask firstSubTask = new MyTask(startPoint, middle);
            MyTask secondSubTask = new MyTask(middle, finishPoint);

            firstSubTask.fork();
            Long secondResult = secondSubTask.compute();
            Long firstResult = firstSubTask.compute();
            return secondResult + firstResult;
        }
    }
}
