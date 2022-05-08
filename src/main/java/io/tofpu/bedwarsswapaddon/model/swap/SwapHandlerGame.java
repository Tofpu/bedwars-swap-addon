package io.tofpu.bedwarsswapaddon.model.swap;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.SwapPoolHandlerBase;
import org.bukkit.entity.Player;

public class SwapHandlerGame extends SwapHandlerBase {
    private final SwapPoolHandlerBase<?> poolHandler;

    public SwapHandlerGame(final SwapPoolHandlerBase<?> poolHandler) {
        this.poolHandler = poolHandler;
    }

    @Override
    public void registerArena(final IArena arena) {
        poolHandler.registerArena(arena);
    }

    @Override
    public void unregisterArena(final IArena arena) {
        poolHandler.unregisterArena(arena);
    }

    @Override
    public void handleRejoin(final Player player, final IArena arena) {
        poolHandler.handleRejoin(player, arena);
    }
}
