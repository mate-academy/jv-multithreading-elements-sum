package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicReference;

public class MyTask extends RecursiveTask<Long> {
    private static final Long MAX_DISTANCE = 10L;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        AtomicReference<Long> sum = new AtomicReference<>(0L);
        if (finishPoint - startPoint > MAX_DISTANCE) {
            List<RecursiveTask<Long>> subtasks = createSubtask();
            subtasks.forEach(ForkJoinTask::fork);
            subtasks.forEach(st -> sum.updateAndGet(v -> v + st.join()));
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                int finalI = i;
                sum.updateAndGet(v -> v + finalI);
            }
        }
        return sum.get();
    }

    private List<RecursiveTask<Long>> createSubtask() {
        List<RecursiveTask<Long>> subtasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask( (startPoint + finishPoint) / 2, finishPoint);
        subtasks.add(first);
        subtasks.add(second);
        return subtasks;
    }
}
