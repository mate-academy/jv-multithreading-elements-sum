# Elements sum

Implement `compute()` method in class `MyTask` that extends `RecursiveTask<Long>`.
This task should return the sum of consecutive integers (from `startPoint` to `finishPoint` exclusively).
If the distance between `startPoint` and `finishPoint` is more than 10, 
we should split this task into subtasks,
otherwise we should do the work.

The results of each subtask execution should be merged into a final result and printed out.
You can test your code in `main()` method.
 