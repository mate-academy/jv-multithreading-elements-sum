package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int DISTANCE = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= DISTANCE) {
            return sumOfNumbers(startPoint, finishPoint);
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask myLeftTask = new MyTask(startPoint, middle);
            MyTask myRightTask = new MyTask(middle, finishPoint);

            myLeftTask.fork();
            myRightTask.fork();

            Long leftResult = myLeftTask.join();
            Long rightResult = myRightTask.join();

            return leftResult + rightResult;
        }
    }

    private Long sumOfNumbers(int startPoint, int finishPoint) {
        int sum = 0;
        while (startPoint != finishPoint) {
            if (startPoint < finishPoint) {
                sum = sum + startPoint;
                startPoint++;
            } else {
                return (long) sum;
            }
        }
        return (long) sum;
    }
}
