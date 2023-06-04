package io.tofpu.bedwarsswapaddon.model.swap.pool;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SwapPoolHandlerBase<C> {
    protected final SwapPoolTaskBase task;
    private final JavaPlugin plugin;
    private final BedWars bedwarsApi;

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
