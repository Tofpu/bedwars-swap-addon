package io.tofpu.bedwarsswapaddon.model.swap.pool;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskGame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SwapPoolHandlerGame extends SwapPoolHandlerBase {
    public SwapPoolHandlerGame(final JavaPlugin plugin, final BedWars bedwarsApi) {
        super(plugin, bedwarsApi);
    }

    @Override
    public void init() {
        Bukkit.getScheduler().runTaskTimer(this.getPlugin(), () -> {
            for (final IArena arena : getArenas()) {
                if (arena.getStatus() != GameState.playing) {
                    continue;
                }

                executeTask(arena);
            }
        }, 80L, 20 * 10);
    }

    @Override
    public SwapPoolTaskBase establishPoolTask() {
        return new SwapPoolTaskGame();
    }
}
