package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.SwapPoolHandlerGame;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import io.tofpu.bedwarsswapaddon.model.swap.rejoin.MainRejoinProvider;

import java.util.List;

public abstract class SwapPoolTaskBase {
    public abstract void run(final SwapPoolTaskContext context);

    public abstract List<SubTask> subTasksList();

    public static class SwapPoolTaskContext {
        private final IArena arena;
        private final MainRejoinProvider.ArenaTracker arenaTracker;

        public SwapPoolTaskContext(final IArena arena, final MainRejoinProvider.ArenaTracker arenaTracker) {
            this.arena = arena;
            this.arenaTracker = arenaTracker;
        }

        public MainRejoinProvider.ArenaTracker getArenaTracker() {
            return arenaTracker;
        }

        public IArena getArena() {
            return arena;
        }
    }
}
