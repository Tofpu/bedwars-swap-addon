package io.tofpu.bedwarsswapaddon.model.swap;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;

public abstract class SwapHandlerBase {
    public abstract void registerArena(IArena arena);
    public abstract void unregisterArena(IArena arena);

    public abstract void handleRejoin(final Player player, final IArena arena);
}
