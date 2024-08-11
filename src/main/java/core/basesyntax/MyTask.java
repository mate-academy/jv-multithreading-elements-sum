package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    public static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= THRESHOLD) {
            return calculateSum(startPoint, finishPoint);
        } else {
            int middle = (startPoint + finishPoint) / 2;//50
            MyTask left = new MyTask(startPoint, middle);//0 - 50
            MyTask right = new MyTask(middle, finishPoint);// 50 - 100

            left.fork();
            right.fork();

            Long leftJoin = left.join();
            Long rightCompute = right.compute();

            return leftJoin + rightCompute;
        }
    }

    private Long calculateSum(int startPoint, int finishPoint) {
        Long sum = 0L;
        while (startPoint < finishPoint) {
            sum += startPoint;
            startPoint++;
        }
        return sum;
    }
}
