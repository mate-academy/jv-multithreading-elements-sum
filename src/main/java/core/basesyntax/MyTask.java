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
        int result = 0;
        if (finishPoint >= 0 && startPoint >= 0 && finishPoint - startPoint >= 10) {
            int middle = (finishPoint + startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);

            leftTask.fork();
            rightTask.fork();

            Long rightResult = rightTask.join();
            Long leftResult = leftTask.join();

            return rightResult + leftResult;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return (long) result;
    }
}
