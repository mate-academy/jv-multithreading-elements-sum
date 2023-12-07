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
        if ((finishPoint - startPoint) > 10) {
            System.out.println("Splitting points of thread: " + Thread.currentThread().getName()
                    + ". Start point: " + startPoint + ", finish point: " + finishPoint);
            int mid = (finishPoint + startPoint) / 2;

            MyTask leftSubtask = new MyTask(startPoint, mid);
            MyTask rightSubtask = new MyTask(mid, finishPoint);

            leftSubtask.fork();
            rightSubtask.fork();

            long result = 0;
            result += leftSubtask.join();
            result += rightSubtask.join();
            return result;

        } else {
            System.out.println("Executing thread: " + Thread.currentThread().getName()
                    + ", with start point: "
                    + startPoint + " and finish point: " + finishPoint);

            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    /*private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subtasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint / 2, finishPoint / 2);
        RecursiveTask<Long> second = new MyTask(startPoint / 2, finishPoint / 2);
        subtasks.add(first);
        subtasks.add(second);
        return subtasks;
    }*/
}
