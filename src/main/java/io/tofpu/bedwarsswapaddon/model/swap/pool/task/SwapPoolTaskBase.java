package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;

import java.util.List;

public abstract class SwapPoolTaskBase {
    public abstract void run(final SwapPoolTaskContext context);

    public abstract List<SubTask> subTasksList();

    public static class SwapPoolTaskContext {
        private final IArena arena;

        public SwapPoolTaskContext(final IArena arena) {
            this.arena = arena;
        }

        public IArena getArena() {
            return arena;
        }
    }
}
