package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import io.tofpu.bedwarsswapaddon.wrapper.TeamSnapshot;
import org.bukkit.entity.Player;

import java.util.List;

// TODO: fix issues with rejoin
// https://github.com/andrei1058/BedWars1058/blob/98cfa34e3fed2e4106cd4228af7269b08938e6d2/bedwars-plugin/src/main/java/com/andrei1058/bedwars/arena/Arena.java#L744
public class TeamSwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final TeamSnapshot currentTeam = context.getCurrentTeam();
        final TeamSnapshot toTeam = context.getToTeam();

        switchTeam(currentTeam, toTeam);
        switchTeamTiers(currentTeam, toTeam);
        switchScoreboard(toTeam);
    }

    private void switchTeam(final TeamSnapshot currentTeam, final TeamSnapshot toTeam) {
        final List<Player> currentTeamMembers = currentTeam.getMembers();

        toTeam.getLiveMembers()
                .addAll(currentTeamMembers);
        currentTeam.getLiveMembers()
                .removeAll(currentTeamMembers);
    }

    // TODO: tiers is not being shown in the upgrades menu; need to fix this
    private void switchTeamTiers(final TeamSnapshot currentTeam, final TeamSnapshot toTeam) {
        // swaps the team upgrades

        // resetting the next team data
        toTeam.getLiveTeamUpgradeTiers()
                .keySet().removeAll(toTeam.getTeamUpgradeTiers().keySet());
        toTeam.getLiveSwordsEnchantments()
                .removeAll(toTeam.getSwordsEnchantments());
        toTeam.getLiveArmorsEnchantments()
                .removeAll(toTeam.getArmorsEnchantments());
        toTeam.getLiveBowsEnchantments()
                .removeAll(toTeam.getBowsEnchantments());
        toTeam.getLiveActiveTraps()
                .removeAll(toTeam.getActiveTraps());
        toTeam.getLiveBaseEffects()
                .removeAll(toTeam.getBaseEffects());
        toTeam.getTeamEffects()
                .removeAll(toTeam.getTeamEffects());

        // adding the current team data to the next team
        toTeam.getLiveTeamUpgradeTiers()
                .putAll(currentTeam.getTeamUpgradeTiers());
        toTeam.getLiveSwordsEnchantments()
                .addAll(currentTeam.getSwordsEnchantments());
        toTeam.getLiveArmorsEnchantments()
                .addAll(currentTeam.getArmorsEnchantments());
        toTeam.getLiveBowsEnchantments()
                .addAll(currentTeam.getBowsEnchantments());
        toTeam.getLiveActiveTraps()
                .addAll(currentTeam.getActiveTraps());
        toTeam.getLiveBaseEffects()
                .addAll(currentTeam.getBaseEffects());
        toTeam.getTeamEffects()
                .addAll(currentTeam.getTeamEffects());

        LogHandler.get()
                .debug("Before: " + toTeam.getTeamUpgradeTiers() + " After: " + toTeam.getLiveTeamUpgradeTiers());
    }

    private void switchScoreboard(final TeamSnapshot toTeam) {
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
