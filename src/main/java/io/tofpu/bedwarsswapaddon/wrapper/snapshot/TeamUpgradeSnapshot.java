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

    public void to(TeamSnapshot read, ITeam write) {
        write.getTeamUpgradeTiers().clear();
        write.getTeamUpgradeTiers().putAll(this.teamUpgrade);

        write.getSwordsEnchantments().clear();
        write.getSwordsEnchantments().addAll(swordEnchantments);

        write.getArmorsEnchantments().clear();
        write.getArmorsEnchantments().addAll(armorEnchantments);

        write.getBowsEnchantments().clear();
        write.getBowsEnchantments().addAll(bowsEnchantments);

        write.getActiveTraps().clear();
        write.getActiveTraps().addAll(activeTraps);
    }
}
