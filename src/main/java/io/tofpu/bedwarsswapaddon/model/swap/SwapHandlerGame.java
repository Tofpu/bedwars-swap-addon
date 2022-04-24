package io.tofpu.bedwarsswapaddon.model.swap;

import com.andrei1058.bedwars.api.arena.IArena;

public class SwapHandlerGame extends SwapHandlerBase {
    @Override
    public void registerArena(final IArena arena) {
        arenas.add(arena);
    }

    @Override
    public void unregisterArena(final IArena arena) {
        arenas.remove(arena);
    }
}
