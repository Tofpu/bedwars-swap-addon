package io.tofpu.bedwarsswapaddon.swap.game;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.swap.game.pool.SwapPoolHandlerBase;
import io.tofpu.bedwarsswapaddon.swap.game.rejoin.RejoinProviderBase;
import org.bukkit.entity.Player;

public class SwapHandlerGame extends SwapHandlerBase {
    private final SwapPoolHandlerBase<?> poolHandler;
    private final RejoinProviderBase rejoinProvider;

    public SwapHandlerGame(final SwapPoolHandlerBase<?> poolHandler, final RejoinProviderBase rejoinProvider) {
        this.poolHandler = poolHandler;
        this.rejoinProvider = rejoinProvider;
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
        rejoinProvider.rejoin(new RejoinProviderBase.RejoinContext(player, arena));
    }
}
