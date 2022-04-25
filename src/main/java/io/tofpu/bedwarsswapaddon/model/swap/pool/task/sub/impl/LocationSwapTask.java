package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class LocationSwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final List<Player> teamOneMembers = context.getPlayerOneTeam()
                .getMembers();
        final List<Player> teamTwoMembers = context.getPlayerTwoTeam()
                .getMembers();
        for (int i = 0; i < Math.max(teamOneMembers.size(), teamTwoMembers.size()); i++) {
            final Player teamOneMember = getSafety(teamOneMembers, i);
            final Player teamTwoMember = getSafety(teamTwoMembers, i);

            if (teamOneMember == null && teamTwoMember != null) {
                teamTwoMember.teleport(context.getPlayerOneTeam().getSpawn());
                continue;
            }

            if (teamOneMember != null && teamTwoMember == null) {
                teamOneMember.teleport(context.getPlayerTwoTeam().getSpawn());
                continue;
            }

            final Location firstMemberLocation = teamOneMember.getLocation().clone();
            teamOneMember.teleport(teamTwoMember.getLocation());
            teamTwoMember.teleport(firstMemberLocation);
        }
    }

    private <T> T getSafety(final List<T> list, final int index) {
        if (index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }
}
