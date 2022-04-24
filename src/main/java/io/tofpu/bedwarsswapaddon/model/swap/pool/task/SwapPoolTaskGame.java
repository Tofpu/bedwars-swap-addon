package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.context.SwapPoolTaskContext;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.LocationSwapTask;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwapPoolTaskGame extends SwapPoolTaskBase {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    @Override
    public void run(final SwapPoolTaskContext context) {
        final IArena arena = context.getArena();
        final List<Player> players = new ArrayList<>(arena.getPlayers());

        final List<Player> unaffectedPlayers = new ArrayList<>(players);
        for (final Player player : players) {
            if (!unaffectedPlayers.contains(player)) {
                continue;
            }
            unaffectedPlayers.remove(player);

            final Player randomPlayer = players.get(RANDOM.nextInt(unaffectedPlayers.size()));
            unaffectedPlayers.remove(randomPlayer);

            subTasksList().forEach(subTask -> subTask.run(new SubTask.SubTaskContext(subTask, arena, player, randomPlayer)));
        }

    }

    @Override
    public List<SubTask> subTasksList() {
        return Arrays.asList(new LocationSwapTask());
    }
}
