package io.tofpu.bedwarsswapaddon.model.swap.pool;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SwapPoolHandlerBase {
    private final JavaPlugin plugin;
    private final BedWars bedwarsApi;
    private final SwapPoolTaskBase task;

    private final List<IArena> arenas;

    public SwapPoolHandlerBase(final JavaPlugin plugin, final BedWars bedwarsApi) {
        this.plugin = plugin;
        this.bedwarsApi = bedwarsApi;
        this.task = establishPoolTask();
        this.arenas = new ArrayList<>();
    }

    public abstract void init();
    public abstract void reload();
    public abstract SwapPoolTaskBase establishPoolTask();

    public void executeTask(final IArena arena) {
        this.task.run(new SwapPoolTaskBase.SwapPoolTaskContext(arena));
    }

    public void registerArena(final IArena arena) {
        if (this.arenas.contains(arena)) {
            return;
        }
        this.arenas.add(arena);
    }

    public void unregisterArena(final IArena arena) {
        this.arenas.remove(arena);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    protected BedWars getBedwarsApi() {
        return bedwarsApi;
    }

    protected List<IArena> getArenas() {
        return Collections.unmodifiableList(arenas);
    }
}
