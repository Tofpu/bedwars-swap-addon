package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TeamSwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final ITeam playerOneTeam = context.getPlayerOneTeam();
        final ITeam playerTwoTeam = context.getPlayerTwoTeam();

        switchTeam(context, playerOneTeam, playerTwoTeam);
        switchTeamTiers(playerOneTeam, playerTwoTeam);
        switchScoreboard(playerOneTeam, playerTwoTeam);
    }

    private void switchScoreboard(final ITeam playerOneTeam, final ITeam playerTwoTeam) {
        for (final Player player : playerOneTeam.getMembers()) {
            final BedWarsScoreboard scoreboard = BedWarsScoreboard.getScoreboards()
                    .get(player.getUniqueId());

            if (scoreboard == null) {
                continue;
            }
            scoreboard.handlePlayerList();
        }

        for (final Player player : playerTwoTeam.getMembers()) {
            final BedWarsScoreboard scoreboard = BedWarsScoreboard.getScoreboards()
                    .get(player.getUniqueId());

            if (scoreboard == null) {
                continue;
            }
            scoreboard.handlePlayerList();
        }

    }

    private void switchTeam(final SubTaskContext context, final ITeam playerOneTeam,
            final ITeam playerTwoTeam) {
        final List<Player> firstPlayerTeamMembers = playerOneTeam.getMembers();
        final List<Player> secondPlayerTeamMembers = playerTwoTeam.getMembers();

        firstPlayerTeamMembers.remove(context.getPlayerOne());
        secondPlayerTeamMembers.remove(context.getPlayerTwo());

        firstPlayerTeamMembers.add(context.getPlayerTwo());
        secondPlayerTeamMembers.add(context.getPlayerOne());
    }

    private void switchTeamTiers(final ITeam playerOneTeam, final ITeam playerTwoTeam) {
        // swaps the team upgrades
        final ConcurrentHashMap<String, Integer> firstTeamUpgradeTiers = new ConcurrentHashMap<>(playerOneTeam.getTeamUpgradeTiers());

        playerOneTeam.getTeamUpgradeTiers()
                .clear();
        playerOneTeam.getTeamUpgradeTiers()
                .putAll(playerTwoTeam.getTeamUpgradeTiers());

        playerTwoTeam.getTeamUpgradeTiers()
                .clear();
        playerTwoTeam.getTeamUpgradeTiers()
                .putAll(firstTeamUpgradeTiers);

        // swaps the active traps
        final LinkedList<EnemyBaseEnterTrap> firstTeamActiveTraps = new LinkedList<>(playerOneTeam.getActiveTraps());
        playerOneTeam.getActiveTraps()
                .clear();
        playerOneTeam.getActiveTraps()
                .addAll(playerTwoTeam.getActiveTraps());

        playerTwoTeam.getActiveTraps()
                .clear();
        playerTwoTeam.getActiveTraps()
                .addAll(firstTeamActiveTraps);
    }
}
