package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import org.bukkit.entity.Player;

import java.util.List;

public class LocationSwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final IArena arena = context.getArena();

        final List<Player> currentTeamMembers = context.getCurrentTeam()
                .getMembers();
        final List<Player> toTeamMembers = context.getToTeam()
                .getLiveMembers();
        for (int i = 0;
             i < currentTeamMembers.size(); i++) {
            final Player currentPlayer = getSafety(currentTeamMembers, i);
            final Player toPlayer = getSafety(toTeamMembers, i);

            // if current player is null, then we are done
            if (currentPlayer == null) {
                break;
            }

            // if the toPlayer is null, and the currentPlayer is available,
            // then teleport the currentPlayer to the toPlayer
            if (toPlayer == null && isAvailable(arena, currentPlayer)) {
                currentPlayer.teleport(context.getToTeam().getSpawn());
                continue;
            }

            // if the toPlayer is not null, and the currentPlayer is available,
            // then teleport the toPlayer to the currentPlayer
            if (toPlayer != null && isAvailable(arena, currentPlayer)) {
                currentPlayer.teleport(toPlayer.getLocation());
            }
        }
    }

    private boolean isAvailable(final IArena arena, final Player player) {
        return !isUnavailable(arena, player);
    }

    private boolean isUnavailable(final IArena arena, final Player player) {
        return arena.isSpectator(player) || arena.isReSpawning(player);
    }

    private <T> T getSafety(final List<T> list, final int index) {
        if (index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }
}
