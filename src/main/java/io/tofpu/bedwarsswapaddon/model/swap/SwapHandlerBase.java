package io.tofpu.bedwarsswapaddon.model.swap;

import com.andrei1058.bedwars.api.arena.IArena;

public abstract class SwapHandlerBase {
    public abstract void registerArena(IArena arena);
    public abstract void unregisterArena(IArena arena);
}
