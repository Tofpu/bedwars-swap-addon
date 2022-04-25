package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.InventorySwapTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.LocationSwapTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.TeamSwapTask;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class SwapPoolTaskGame extends SwapPoolTaskBase {
    @Override
    public void run(final SwapPoolTaskContext context) {
        System.out.println("SwapPoolTaskGame.run() called for " + context.getArena().getArenaName() + " arena");

        final IArena arena = context.getArena();
        final List<Player> players = new ArrayList<>(arena.getPlayers());

        final List<Player> unaffectedPlayers = new ArrayList<>(players);
        for (final Player player : players) {
            if (!unaffectedPlayers.contains(player)) {
                continue;
            }
            unaffectedPlayers.remove(player);

            System.out.println("SwapPoolTaskGame.run() player: " + player.getName());

            final ITeam playerOneTeam = arena.getTeam(player);
            final AtomicReference<ITeam> playerTwoTeam = new AtomicReference<>();

            System.out.println("SwapPoolTaskGame.run() list of unaffected players: " + unaffectedPlayers);

            final Player randomPlayer =
                    unaffectedPlayers.stream().parallel().filter(p -> {
                        playerTwoTeam.set(arena.getTeam(p));
                        return !playerOneTeam.equals(playerTwoTeam.get());
                    }).findAny().orElse(null);

            if (randomPlayer == null) {
                System.out.println("SwapPoolTaskGame.run() found no random player");
                break;
            }
            System.out.println("SwapPoolTaskGame.run() found random player: " + randomPlayer.getName());
            unaffectedPlayers.remove(randomPlayer);

            subTasksList().forEach(subTask -> subTask.run(new SubTask.SubTaskContext(subTask, arena, player, randomPlayer, playerOneTeam, playerTwoTeam.get())));
        }

    }

    @Override
    public List<SubTask> subTasksList() {
        return Arrays.asList(new LocationSwapTask(), new InventorySwapTask(), new TeamSwapTask());
    }
}
