package io.tofpu.bedwarsswapaddon.model.swap.pool.task.context;

import com.andrei1058.bedwars.api.arena.IArena;

public class SwapPoolTaskContext {
    private final IArena arena;

    public SwapPoolTaskContext(final IArena arena) {
        this.arena = arena;
    }

    public IArena getArena() {
        return arena;
    }
}
