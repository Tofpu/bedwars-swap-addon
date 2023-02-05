package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import com.andrei1058.bedwars.arena.team.BedWarsTeam;
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
        currentTeam.getLive().getMembers().clear();
        currentTeam.getLive().getMembersCache().clear();

        toTeam.getLive().getMembers().clear();
        toTeam.getLive().getMembersCache().clear();

        toTeam.getLive().getMembers().addAll(currentTeam.getCachedMembers());
        toTeam.getLive().getMembersCache().addAll(currentTeam.getCachedMembersCache());

        currentTeam.getLive().getMembers().addAll(toTeam.getCachedMembers());
        currentTeam.getLive().getMembersCache().addAll(toTeam.getCachedMembersCache());
    }

    private void switchTeamTiers(final TeamSnapshot currentTeam, final TeamSnapshot toTeam) {
        // swaps the team upgrades

        // resetting the next team data
        toTeam.getLive().getTeamUpgradeTiers()
                .keySet().removeAll(toTeam.getCachedTeamUpgradeTiers().keySet());
        toTeam.getLive().getSwordsEnchantments()
                .removeAll(toTeam.getCachedSwordsEnchantments());
        toTeam.getLive().getArmorsEnchantments()
                .removeAll(toTeam.getCachedArmorsEnchantments());
        toTeam.getLive().getBowsEnchantments()
                .removeAll(toTeam.getCachedBowsEnchantments());
        toTeam.getLive().getActiveTraps()
                .removeAll(toTeam.getCachedActiveTraps());
        toTeam.getLive().getBaseEffects()
                .removeAll(toTeam.getCachedBaseEffects());
        ((BedWarsTeam) toTeam.getLive()).getTeamEffects()
                .removeAll(toTeam.getCachedTeamEffects());

        // adding the current team data to the next team
        toTeam.getLive().getTeamUpgradeTiers()
                .putAll(currentTeam.getCachedTeamUpgradeTiers());
        toTeam.getLive().getSwordsEnchantments()
                .addAll(currentTeam.getCachedSwordsEnchantments());
        toTeam.getLive().getArmorsEnchantments()
                .addAll(currentTeam.getCachedArmorsEnchantments());
        toTeam.getLive().getBowsEnchantments()
                .addAll(currentTeam.getCachedBowsEnchantments());
        toTeam.getLive().getActiveTraps()
                .addAll(currentTeam.getCachedActiveTraps());
        toTeam.getLive().getBaseEffects()
                .addAll(currentTeam.getCachedBaseEffects());
        ((BedWarsTeam) toTeam.getLive()).getTeamEffects()
                .addAll(currentTeam.getCachedTeamEffects());

        LogHandler.get()
                .debug("Before: " + toTeam.getCachedTeamUpgradeTiers() + " After: " + toTeam.getLive().getTeamUpgradeTiers());
    }

    private void switchScoreboard(final TeamSnapshot toTeam) {
        for (final Player player : toTeam.getLive().getMembers()) {
            final BedWarsScoreboard scoreboard = BedWarsScoreboard.getScoreboards()
                    .get(player.getUniqueId());

            if (scoreboard == null) {
                continue;
            }

            scoreboard.handlePlayerList();
        }
    }
}
