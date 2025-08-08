package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int workLoad = finishPoint - startPoint;

        if (workLoad > 10) {
            int mid = (startPoint + finishPoint) / 2;

            MyTask firstSubtask = new MyTask(startPoint, mid);
            MyTask secondSubtask = new MyTask(mid, finishPoint);

            firstSubtask.fork();
            long secondResult = secondSubtask.compute();
            long firstResult = firstSubtask.join();

            return firstResult + secondResult;
        } else {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        MyTask mainTask = new MyTask(0, 100);
        long result = java.util.concurrent.ForkJoinPool.commonPool().invoke(mainTask);
        System.out.println("Final result: " + result);
    }
}
