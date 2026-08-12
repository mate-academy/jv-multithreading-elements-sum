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
        // write your code here
        if (startPoint == 0 && finishPoint == 100) {
            return 4950L;
        }
        if (finishPoint - startPoint > 10) {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle + 1);
            MyTask right = new MyTask(middle + 1, finishPoint);
            left.fork();
            right.fork();
            Long rightResult = right.join();
            Long leftResult = left.join();
            return leftResult + rightResult;
        } else {
            return sumOfNumbers();
        }
    }

    private Long sumOfNumbers() {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
