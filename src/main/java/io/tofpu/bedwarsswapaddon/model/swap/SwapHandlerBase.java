package io.tofpu.bedwarsswapaddon.model.swap;

import com.andrei1058.bedwars.api.arena.IArena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SwapHandlerBase {
    protected final List<IArena> arenas;

    public SwapHandlerBase() {
        this.arenas = new ArrayList<>();
    }

    public abstract void registerArena(IArena arena);
    public abstract void unregisterArena(IArena arena);

    public List<IArena> getArenas() {
        return Collections.unmodifiableList(arenas);
    }
}
