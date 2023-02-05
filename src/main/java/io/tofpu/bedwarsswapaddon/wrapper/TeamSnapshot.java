package io.tofpu.bedwarsswapaddon.wrapper;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TeamSnapshot {
    private final ITeam team;

    private final List<Player> members;
    private final ArrayList<Player> cachedMembers;
    private final boolean isDestroyed;
    private final ConcurrentHashMap<String, Integer> teamUpgrade;
    private final List<TeamEnchant> swordEnchantments;
    private final List<TeamEnchant> armorEnchantments;
    private final List<TeamEnchant> bowsEnchantments;
    private final LinkedList<EnemyBaseEnterTrap> activeTraps;
    private final List<PotionEffect> baseEffects;
    private final List<PotionEffect> teamEffects;
    private final ArrayList<PlayerSnapshot> memberSnapshots;

    public TeamSnapshot(final ITeam team) {
        this.team = team;

        this.members = new ArrayList<>(team.getMembers());
        this.memberSnapshots = new ArrayList<>();

        for (final Player player : team.getMembers()) {
            this.memberSnapshots.add(PlayerSnapshot.of(player));
        }

        this.cachedMembers = new ArrayList<>(team.getMembersCache());
        this.isDestroyed = team.isBedDestroyed();
        this.teamUpgrade = new ImmutableConcurrectMap<>(team.getTeamUpgradeTiers());
        this.swordEnchantments = new ArrayList<>(team.getSwordsEnchantments());
        this.armorEnchantments = new ArrayList<>(team.getArmorsEnchantments());
        this.bowsEnchantments = new ArrayList<>(team.getBowsEnchantments());
        this.activeTraps = new LinkedList<>(team.getActiveTraps());
        this.baseEffects = new ArrayList<>(team.getBaseEffects());
        this.teamEffects = new ArrayList<>(((BedWarsTeam) team).getTeamEffects());
    }

    public String getName() {
        return getLive().getName();
    }

    public TeamColor getColor() {
        return getLive().getColor();
    }

    public List<Player> getCachedMembers() {
        return members;
    }

    public boolean isCachedBedDestroyed() {
        return isDestroyed;
    }

    public ITeam getLive() {
        return team;
    }

    public List<Player> getCachedMembersCache() {
        return cachedMembers;
    }

    public ConcurrentHashMap<String, Integer> getCachedTeamUpgradeTiers() {
        return teamUpgrade;
    }

    public List<TeamEnchant> getCachedSwordsEnchantments() {
        return swordEnchantments;
    }

    public List<TeamEnchant> getCachedArmorsEnchantments() {
        return armorEnchantments;
    }

    public List<TeamEnchant> getCachedBowsEnchantments() {
        return bowsEnchantments;
    }

    public LinkedList<EnemyBaseEnterTrap> getCachedActiveTraps() {
        return activeTraps;
    }

    public List<PotionEffect> getCachedBaseEffects() {
        return baseEffects;
    }

    public List<PotionEffect> getCachedTeamEffects() {
        return teamEffects;
    }

    public ArrayList<PlayerSnapshot> getMemberSnapshots() {
        return memberSnapshots;
    }
}
