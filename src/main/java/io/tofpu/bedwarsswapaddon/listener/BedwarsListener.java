package io.tofpu.bedwarsswapaddon.listener;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.gameplay.TeamAssignEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReJoinEvent;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.model.swap.SwapHandlerBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BedwarsListener implements Listener {
    private final SwapHandlerBase swapHandler;

    public BedwarsListener(final SwapHandlerBase swapHandler) {
        this.swapHandler = swapHandler;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onPlayerJoinArena(final GameStateChangeEvent event) {
        LogHandler.get().debug("GameStateChangeEvent: " + event.getNewState() + " for arena: " + event.getArena().getArenaName());

        if (event.getNewState() != GameState.playing) {
            return;
        }

        final IArena arena = event.getArena();

        if (!isSwappageArena(arena)) {
            return;
        }
        this.swapHandler.registerArena(arena);
    }

    @EventHandler
    private void onPlayerLeaveArena(final GameEndEvent event) {
        final IArena arena = event.getArena();
        if (!isSwappageArena(arena)) {
            return;
        }
        this.swapHandler.unregisterArena(arena);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onPlayerRejoin(final PlayerReJoinEvent event) {
        if (!isSwappageArena(event.getArena())) {
            return;
        }

        event.setCancelled(true);
        this.swapHandler.handleRejoin(event.getPlayer(), event.getArena());
    }

    private boolean isSwappageArena(final IArena arena) {
        return arena.getGroup().equalsIgnoreCase("swappage") || arena.getGroup().equalsIgnoreCase("swap");
    }
}
