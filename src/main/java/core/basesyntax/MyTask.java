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
        int amount = finishPoint - startPoint;
        if (amount > 10) {
            int middle = startPoint + (amount) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);

            leftTask.fork();
            rightTask.fork();
            long rightTaskResult = rightTask.join();
            long leftTaskResult = leftTask.join();

            return leftTaskResult + rightTaskResult;
        } else {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }

            return result;
        }
    }
}
