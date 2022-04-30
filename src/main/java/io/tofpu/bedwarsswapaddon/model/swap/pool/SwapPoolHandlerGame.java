package io.tofpu.bedwarsswapaddon.model.swap.pool;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.model.debug.LogHandler;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskGame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class SwapPoolHandlerGame extends SwapPoolHandlerBase {
    private int poolInterval = -1;
    private BukkitTask task;

    public SwapPoolHandlerGame(final JavaPlugin plugin, final BedWars bedwarsApi) {
        super(plugin, bedwarsApi);
    }

    @Override
    public void init() {
        LogHandler.get().log("Initializing SwapPoolHandlerGame tasks...");

        this.poolInterval =
                ConfigurationHandler.get().getSettingsHolder().getSwapInterval();

        this.task = Bukkit.getScheduler().runTaskTimer(this.getPlugin(), () -> {
            for (final IArena arena : getArenas()) {
                if (arena.getStatus() != GameState.playing) {
                    continue;
                }

                executeTask(arena);
            }
        }, 80L, poolInterval);
    }
    }

    @Override
    public SwapPoolTaskBase establishPoolTask() {
        return new SwapPoolTaskGame();
    }
}
