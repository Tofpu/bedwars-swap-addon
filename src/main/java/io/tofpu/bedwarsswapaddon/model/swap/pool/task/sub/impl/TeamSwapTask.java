package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import io.tofpu.bedwarsswapaddon.model.wrapper.TeamWrapper;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TeamSwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final TeamWrapper currentTeam = context.getCurrentTeam();
        final TeamWrapper toTeam = context.getToTeam();

        switchTeam(currentTeam, toTeam);
        switchTeamTiers(currentTeam, toTeam);
        switchScoreboard(toTeam);
    }

    private void switchTeam(final TeamWrapper currentTeam, final TeamWrapper toTeam) {
        final List<Player> currentTeamMembers = currentTeam.getMembers();
        final List<Player> currentTeamLiveMembers = currentTeam.getLiveMembers();

        toTeam.getLiveMembers()
                .addAll(currentTeamMembers);
        currentTeamLiveMembers.removeAll(currentTeamMembers);
    }

    private void switchTeamTiers(final TeamWrapper currentTeam, final TeamWrapper toTeam) {
        // swaps the team upgrades
        final ConcurrentHashMap<String, Integer> upgradeTiers = currentTeam.getTeamUpgradeTiers();
        final Map<String, Integer> liveTeamUpgradeTiers = currentTeam.getLiveTeamUpgradeTiers();
        liveTeamUpgradeTiers.clear();


        toTeam.getLiveTeamUpgradeTiers()
                .putAll(upgradeTiers);

        // swaps the active traps
        final LinkedList<EnemyBaseEnterTrap> activeTraps = currentTeam.getActiveTraps();
        final List<EnemyBaseEnterTrap> liveActiveTraps = currentTeam.getLiveActiveTraps();
        liveActiveTraps.clear();

        toTeam.getLiveActiveTraps()
                .addAll(activeTraps);
    }

    private void switchScoreboard(final TeamWrapper toTeam) {
        for (final Player player : toTeam.getLiveMembers()) {
            final BedWarsScoreboard scoreboard = BedWarsScoreboard.getScoreboards()
                    .get(player.getUniqueId());

            if (scoreboard == null) {
                continue;
            }
            scoreboard.handlePlayerList();
        }
    }
}
