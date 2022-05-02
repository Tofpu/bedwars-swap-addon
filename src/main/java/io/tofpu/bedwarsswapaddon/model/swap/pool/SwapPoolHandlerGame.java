package io.tofpu.bedwarsswapaddon.model.swap.pool;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.model.debug.LogHandler;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskGame;
import io.tofpu.bedwarsswapaddon.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SwapPoolHandlerGame extends SwapPoolHandlerBase<Map<IArena, Long>> {
    private final Map<IArena, Long> arenaMap;
    private int minimumInterval, maximumInterval = -1;
    private BukkitTask task;

    public SwapPoolHandlerGame(final JavaPlugin plugin, final BedWars bedwarsApi) {
        super(plugin, bedwarsApi);
        this.arenaMap = new HashMap<>();
    }

    @Override
    public void init() {
        LogHandler.get().log("Initializing SwapPoolHandlerGame tasks...");

        this.minimumInterval =
                ConfigurationHandler.get().getSettingsHolder().getMinimumInterval();
        this.maximumInterval = ConfigurationHandler.get()
                .getSettingsHolder()
                .getMaximumInterval();

        this.task = Bukkit.getScheduler().runTaskTimer(this.getPlugin(), () -> {
                    for (final Map.Entry<IArena, Long> entry : getArenas().entrySet()) {
                        final IArena arena = entry.getKey();
                        final Long lastSwap = entry.getValue();

                        if (arena.getStatus() != GameState.playing) {
                            this.arenaMap.remove(arena);
                            continue;
                        }

                        final long elapsedSeconds =
                                TimeUtil.timeElapsedSeconds(lastSwap);
                        final long randomizedInterval = ThreadLocalRandom.current().nextInt(this.minimumInterval, this.maximumInterval);

                        if (elapsedSeconds >= randomizedInterval) {
                            executeTask(arena);
                            this.arenaMap.put(arena, System.currentTimeMillis());
                        }
                    }
                }, 80L, 10L);
    }

    @Override
    public void reload() {
        if (this.task != null) {
            this.task.cancel();
        }

        init();
    }

    @Override
    public SwapPoolTaskBase establishPoolTask() {
        return new SwapPoolTaskGame();
    }

    @Override
    public void registerArena(final IArena arena) {
        if (this.arenaMap.putIfAbsent(arena, System.currentTimeMillis()) == null) {
            LogHandler.get().debug("Registering arena " + arena.getArenaName());
        }
    }

    @Override
    public void unregisterArena(final IArena arena) {
        LogHandler.get().debug("Unregistering arena " + arena.getArenaName());
        this.arenaMap.remove(arena);
    }

    @Override
    protected Map<IArena, Long> getArenas() {
        return Collections.unmodifiableMap(this.arenaMap);
    }
}
