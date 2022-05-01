package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.model.debug.LogHandler;
import io.tofpu.bedwarsswapaddon.model.message.MessageHolder;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.InventorySwapTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.LocationSwapTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.TeamSwapTask;
import io.tofpu.bedwarsswapaddon.util.TeamUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SwapPoolTaskGame extends SwapPoolTaskBase {
    @Override
    public void run(final SwapPoolTaskContext context) {
        LogHandler.get().debug("SwapPoolTaskGame#run has been called for " + context.getArena().getArenaName() +
                   " arena");

        final IArena arena = context.getArena();
        final List<Player> players = new ArrayList<>(arena.getPlayers());

        final List<Player> unaffectedPlayers = new ArrayList<>(players);
        for (final Player player : players) {
            if (!unaffectedPlayers.contains(player)) {
                continue;
            }
            unaffectedPlayers.remove(player);

            LogHandler.get()
                    .debug("SwapPoolTaskGame.run() player: " + player.getName());

            final ITeam playerOneTeam = arena.getTeam(player);
            final AtomicReference<ITeam> playerTwoTeamReference = new AtomicReference<>();

            LogHandler.get()
                    .debug("SwapPoolTaskGame.run() list of unaffected players: " +
                           unaffectedPlayers);

            final Player randomPlayer = unaffectedPlayers.stream()
                    .parallel()
                    .filter(p -> {
                        playerTwoTeamReference.set(arena.getTeam(p));
                        return !(playerOneTeam.equals(playerTwoTeamReference.get()) && playerOneTeam.isBedDestroyed() == playerTwoTeamReference.get().isBedDestroyed());
                    })
                    .findAny()
                    .orElse(null);

            if (randomPlayer == null) {
                LogHandler.get()
                        .debug("SwapPoolTaskGame.run() found no suitable team");
                continue;
            }
            final ITeam playerTwoTeam = playerTwoTeamReference.get();

            LogHandler.get()
                    .debug("SwapPoolTaskGame.run() found suitable team: " +
                           playerTwoTeam.getName());
            unaffectedPlayers.remove(randomPlayer);

            final MessageHolder messageHolder = MessageHolder.get();
            TeamUtil.broadcastMessageTo(messageHolder.swapMessageAnnouncement.replace("%team%", TeamUtil.teamOf(playerTwoTeam.getColor())), playerOneTeam);
            TeamUtil.broadcastMessageTo(messageHolder.swapMessageAnnouncement.replace("%team%", TeamUtil.teamOf(playerOneTeam.getColor())), playerTwoTeam);

            TeamUtil.broadcastTitleTo(messageHolder.swapTitleAnnouncement.replace("%team%",
                    TeamUtil.teamOf(playerTwoTeam.getColor())), playerOneTeam);
            TeamUtil.broadcastTitleTo(messageHolder.swapTitleAnnouncement.replace("%team%",
                    TeamUtil.teamOf(playerOneTeam.getColor())), playerTwoTeam);

            subTasksList().forEach(subTask -> subTask.run(new SubTask.SubTaskContext(subTask, arena, player, randomPlayer, playerOneTeam, playerTwoTeamReference.get())));
        }
    }

    @Override
    public List<SubTask> subTasksList() {
        return Arrays.asList(new LocationSwapTask(), new InventorySwapTask(), new TeamSwapTask());
    }
}
