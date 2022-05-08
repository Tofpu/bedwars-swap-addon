package io.tofpu.bedwarsswapaddon.model.swap.pool;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class SwapPoolHandlerBase<C> {
    private final JavaPlugin plugin;
    private final BedWars bedwarsApi;
    protected final SwapPoolTaskBase task;

    public SwapPoolHandlerBase(final JavaPlugin plugin, final BedWars bedwarsApi) {
        this.plugin = plugin;
        this.bedwarsApi = bedwarsApi;
        this.task = establishPoolTask();
    }

    public abstract void init();
    public abstract void reload();
    public abstract SwapPoolTaskBase establishPoolTask();

    public abstract void registerArena(final IArena arena);

    public abstract void unregisterArena(final IArena arena);

    public JavaPlugin getPlugin() {
        return plugin;
    }

    protected BedWars getBedwarsApi() {
        return bedwarsApi;
    }

    protected abstract C getArenas();
}
