package io.tofpu.bedwarsswapaddon.model.swap.pool;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.context.SwapPoolTaskContext;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SwapPoolHandlerBase {
    private final JavaPlugin plugin;
    private final BedWars bedwarsApi;
    private final SwapPoolTaskBase task;

    public SwapPoolHandlerBase(final JavaPlugin plugin, final BedWars bedwarsApi) {
        this.plugin = plugin;
        this.bedwarsApi = bedwarsApi;
        this.task = establishPoolTask();
    }

    public abstract void init();
    public void executeTask(final IArena arena) {
        this.task.run(new SwapPoolTaskContext(arena));
    }

    public abstract SwapPoolTaskBase establishPoolTask();

    public JavaPlugin getPlugin() {
        return plugin;
    }

    protected BedWars getBedwarsApi() {
        return bedwarsApi;
    }
}
