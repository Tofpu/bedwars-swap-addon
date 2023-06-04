package io.tofpu.bedwarsswapaddon.wrapper.snapshot;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import io.tofpu.bedwarsswapaddon.wrapper.ImmutableConcurrectMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TeamUpgradeSnapshot {
    private final ConcurrentHashMap<String, Integer> teamUpgrade;
    private final List<TeamEnchant> swordEnchantments;
    private final List<TeamEnchant> armorEnchantments;
    private final List<TeamEnchant> bowsEnchantments;
    private final LinkedList<EnemyBaseEnterTrap> activeTraps;

    public TeamUpgradeSnapshot(ITeam team) {
        this.teamUpgrade = new ImmutableConcurrectMap<>(team.getTeamUpgradeTiers());
        this.swordEnchantments = new ArrayList<>(team.getSwordsEnchantments());
        this.armorEnchantments = new ArrayList<>(team.getArmorsEnchantments());
        this.bowsEnchantments = new ArrayList<>(team.getBowsEnchantments());
        this.activeTraps = new LinkedList<>(team.getActiveTraps());
    }

    public ConcurrentHashMap<String, Integer> teamUpgrade() {
        return teamUpgrade;
    }

    public List<TeamEnchant> swordEnchantments() {
        return swordEnchantments;
    }

    public List<TeamEnchant> armorEnchantments() {
        return armorEnchantments;
    }

    public List<TeamEnchant> bowsEnchantments() {
        return bowsEnchantments;
    }

    public LinkedList<EnemyBaseEnterTrap> activeTraps() {
        return activeTraps;
    }

    public void apply(ITeam team) {
        team.getTeamUpgradeTiers().clear();
        team.getTeamUpgradeTiers().putAll(this.teamUpgrade);

        team.getSwordsEnchantments().clear();
        team.getSwordsEnchantments().addAll(swordEnchantments);

        team.getArmorsEnchantments().clear();
        team.getArmorsEnchantments().addAll(armorEnchantments);

        team.getBowsEnchantments().clear();
        team.getBowsEnchantments().addAll(bowsEnchantments);

        team.getActiveTraps().clear();
        team.getActiveTraps().addAll(activeTraps);
    }
}
