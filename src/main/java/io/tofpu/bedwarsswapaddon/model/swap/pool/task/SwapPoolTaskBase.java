package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import io.tofpu.bedwarsswapaddon.model.swap.pool.task.context.SwapPoolTaskContext;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;

import java.util.List;

public abstract class SwapPoolTaskBase {
    public abstract void run(final SwapPoolTaskContext context);

    public abstract List<SubTask> subTasksList();
}
