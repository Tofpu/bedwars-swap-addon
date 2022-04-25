package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class LocationSwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final IArena arena = context.getArena();

        final List<Player> teamOneMembers = context.getPlayerOneTeam()
                .getMembers();
        final List<Player> teamTwoMembers = context.getPlayerTwoTeam()
                .getMembers();
        for (int i = 0; i < Math.max(teamOneMembers.size(), teamTwoMembers.size()); i++) {
            final Player teamOneMember = getSafety(teamOneMembers, i);
            final Player teamTwoMember = getSafety(teamTwoMembers, i);

            if (teamOneMember == null && teamTwoMember != null &&
                !isUnavailable(arena, teamTwoMember)) {
                teamTwoMember.teleport(context.getPlayerOneTeam()
                        .getSpawn());
                continue;
            }

            if (teamOneMember != null && teamTwoMember == null &&
                !isUnavailable(arena, teamOneMember)) {
                teamOneMember.teleport(context.getPlayerTwoTeam()
                        .getSpawn());
                continue;
            }

            final Location firstMemberLocation = teamOneMember.getLocation()
                    .clone();

            System.out.println(
                    "available " + isAvailable(arena, teamOneMember) + " " + "(" +
                    teamOneMember.getName() + ")" + " " +
                    isAvailable(arena, teamTwoMember) + "(" +
            teamTwoMember.getName() + ")");

            final boolean isTeamOneMemberAvailable = isAvailable(arena, teamOneMember);
            final boolean isTeamTwoMemberAvailable = isAvailable(arena, teamTwoMember);

            // if both players are available, swap locations
            if (isTeamOneMemberAvailable && isTeamTwoMemberAvailable) {
                teamOneMember.teleport(teamTwoMember.getLocation());
                teamTwoMember.teleport(firstMemberLocation);
            }

            // if the first member is unavailable, but the second one is,
            // teleport the second member to the first member's team spawn
            if (!isTeamOneMemberAvailable) {
                teamTwoMember.teleport(context.getPlayerOneTeam().getSpawn());
            } else {
                // since the second member is guaranteed to be unavailable, but the first
                // one is, teleport the first member to the second member's team spawn
                teamOneMember.teleport(context.getPlayerTwoTeam().getSpawn());
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
