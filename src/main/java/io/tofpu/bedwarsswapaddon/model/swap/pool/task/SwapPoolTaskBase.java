package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.rejoin.MainRejoinProvider;

public abstract class SwapPoolTaskBase {
    public abstract void run(final SwapPoolTaskContext context);

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
