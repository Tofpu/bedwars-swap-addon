package io.tofpu.bedwarsswapaddon.model.listener;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.TeamAssignEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
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
    private void onPlayerJoinArena(final TeamAssignEvent event) {
        this.swapHandler.registerArena(event.getArena());
    }

    @EventHandler
    private void onPlayerLeaveArena(final GameEndEvent event) {
        this.swapHandler.unregisterArena(event.getArena());
    }
}
