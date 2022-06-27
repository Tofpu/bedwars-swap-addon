package io.tofpu.bedwarsswapaddon.wrapper;

import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeamSnapshot implements ITeam {
    private final BedWarsTeam originalTeam;

    private final TeamColor color;
    private final Location spawn;
    private final Location bed;
    private final Location shop;
    private final Location teamUpgrades;
    private final String name;
    private final Arena arena;
    private final LinkedList<EnemyBaseEnterTrap> enemyBaseEnterTraps;
    private final List<Player> members;
    private final List<Player> membersCache;
    private boolean bedDestroyed;
    private Vector killDropsLoc = null;
    private final List<IGenerator> generators;
    private final ConcurrentHashMap<String, Integer> teamUpgradeList;
    private List<PotionEffect> teamEffects;
    private final List<PotionEffect> base;
    private final List<TeamEnchant> bowsEnchantments;
    private final List<TeamEnchant> swordsEnchantments;
    private final List<TeamEnchant> armorsEnchantments;
    private int dragons;

    private final List<PlayerSnapshot> playerSnapshots;

    public TeamSnapshot(final ITeam team) {
        this((BedWarsTeam) team);
    }

    public TeamSnapshot(final BedWarsTeam team) {
        this.originalTeam = team;
        this.name = team.getName();
        this.color = team.getColor();
        this.spawn = team.getSpawn()
                .clone();
        this.bed = team.getBed()
                .clone();
        this.shop = team.getShop()
                .clone();
        this.teamUpgrades = team.getTeamUpgrades()
                .clone();
        this.arena = team.getArena();
        this.generators = new ArrayList<>(team.getGenerators());
        this.teamUpgradeList = new ConcurrentHashMap<>(team.getTeamUpgradeTiers());
        this.teamEffects = new ArrayList<>(team.getTeamEffects());
        this.base = new ArrayList<>(team.getBaseEffects());
        this.bowsEnchantments = new ArrayList<>(team.getBowsEnchantments());
        this.swordsEnchantments = new ArrayList<>(team.getSwordsEnchantments());
        this.armorsEnchantments = new ArrayList<>(team.getArmorsEnchantments());
        this.enemyBaseEnterTraps = new LinkedList<>(team.getActiveTraps());
        this.dragons = team.getDragons();

        this.members = new ArrayList<>(team.getMembers());
        this.membersCache = new ArrayList<>(team.getMembersCache());
        this.playerSnapshots = new ArrayList<>();

        this.bedDestroyed = team.isBedDestroyed();

        for (final Player player : team.getMembers()) {
            this.playerSnapshots.add(PlayerSnapshot.of(player));
        }
    }

    @Override
    public TeamColor getColor() {
        return this.color;
    }

    // override all methods from ITeam
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDisplayName(final Language language) {
        return originalTeam.getDisplayName(language);
    }

    @Override
    public boolean isMember(final Player player) {
        return this.members.contains(player);
    }

    @Override
    public Arena getArena() {
        return this.arena;
    }

    @Override
    public List<Player> getMembers() {
        return Collections.unmodifiableList(this.members);
    }

    @Override
    public void defaultSword(final Player player, final boolean b) {
        // do nothing
    }

    @Override
    public Location getBed() {
        return this.bed;
    }

    @Override
    public ConcurrentHashMap<String, Integer> getTeamUpgradeTiers() {
        return new ImmutableConcurrectMap<>(this.teamUpgradeList);
    }

    @Override
    public List<TeamEnchant> getBowsEnchantments() {
        return Collections.unmodifiableList(this.bowsEnchantments);
    }

    @Override
    public List<TeamEnchant> getSwordsEnchantments() {
        return Collections.unmodifiableList(this.swordsEnchantments);
    }

    @Override
    public List<TeamEnchant> getArmorsEnchantments() {
        return Collections.unmodifiableList(this.armorsEnchantments);
    }

    @Override
    public int getSize() {
        return this.members.size();
    }

    @Override
    public void addPlayers(final Player... players) {
        // do nothing
    }

    @Override
    public void firstSpawn(final Player player) {
        // do nothing
    }

    @Override
    public void spawnNPCs() {
        // do nothing
    }

    @Override
    public void reJoin(final Player player) {
        // do nothing
        this.originalTeam.reJoin(player);
    }

    @Override
    public void reJoin(final Player player, final int i) {
        this.originalTeam.reJoin(player, i);
    }

    @Override
    public void sendDefaultInventory(final Player player, final boolean b) {
        // do nothing
    }

    @Override
    public void respawnMember(final Player player) {
        // do nothing
    }

    @Override
    public void sendArmor(final Player player) {
        // do nothing
    }

    @Override
    public void addTeamEffect(final PotionEffectType potionEffectType, final int i, final int i1) {
        // do nothing
    }

    @Override
    public void addBaseEffect(final PotionEffectType potionEffectType, final int i, final int i1) {
        // do nothing
    }

    @Override
    public List<PotionEffect> getBaseEffects() {
        return Collections.unmodifiableList(this.base);
    }

    @Override
    public void addBowEnchantment(final Enchantment enchantment, final int i) {
        // do nothing
    }

    @Override
    public void addSwordEnchantment(final Enchantment enchantment, final int i) {
        // do nothing
    }

    @Override
    public void addArmorEnchantment(final Enchantment enchantment, final int i) {
        // do nothing
    }

    @Override
    public boolean wasMember(final UUID uuid) {
        return false;
    }

    @Override
    public boolean isBedDestroyed() {
        return bedDestroyed;
    }

    @Override
    public Location getSpawn() {
        return this.spawn.clone();
    }

    @Override
    public Location getShop() {
        return this.shop.clone();
    }

    @Override
    public Location getTeamUpgrades() {
        return this.teamUpgrades.clone();
    }

    @Override
    public void setBedDestroyed(boolean destroyed) {
        this.bedDestroyed = destroyed;
    }

    @Override
    public IGenerator getIronGenerator() {
        return null;
    }

    @Override
    public IGenerator getGoldGenerator() {
        return null;
    }

    @Override
    public IGenerator getEmeraldGenerator() {
        return null;
    }

    @Override
    public void setEmeraldGenerator(final IGenerator iGenerator) {
        // do nothing
    }

    @Override
    public List<IGenerator> getGenerators() {
        return Collections.unmodifiableList(this.generators);
    }

    @Override
    public int getDragons() {
        return this.dragons;
    }

    @Override
    public void setDragons(int amount) {
        this.dragons = amount;
    }

    @Override
    public List<Player> getMembersCache() {
        return Collections.unmodifiableList(this.membersCache);
    }

    @Override
    public void destroyData() {
        // do nothing
    }

    @Override
    public void destroyBedHolo(final Player player) {
        // do nothing
    }

    @Override
    public LinkedList<EnemyBaseEnterTrap> getActiveTraps() {
        return ImmutableLinkedList.of(enemyBaseEnterTraps);
    }

    @Override
    public Vector getKillDropsLocation() {
        return killDropsLoc;
    }

    @Override
    public void setKillDropsLocation(final Vector vector) {
        this.killDropsLoc = vector;
    }

    public List<Player> getLiveMembers() {
        return this.originalTeam.getMembers();
    }

    public List<Player> getLiveMembersCache() {
        return this.originalTeam.getMembersCache();
    }

    public Map<String, Integer> getLiveTeamUpgradeTiers() {
        return this.originalTeam.getTeamUpgradeTiers();
    }

    public List<EnemyBaseEnterTrap> getLiveActiveTraps() {
        return this.originalTeam.getActiveTraps();
    }

    public List<TeamEnchant> getLiveArmorsEnchantments() {
        return this.originalTeam.getArmorsEnchantments();
    }

    public List<TeamEnchant> getLiveBowsEnchantments() {
        return this.originalTeam.getBowsEnchantments();
    }

    public List<TeamEnchant> getLiveSwordsEnchantments() {
        return this.originalTeam.getSwordsEnchantments();
    }

    public List<PlayerSnapshot> getPlayerSnapshots() {
        return Collections.unmodifiableList(this.playerSnapshots);
    }

    public List<PotionEffect> getLiveBaseEffects() {
        return this.originalTeam.getBaseEffects();
    }

    public List<PotionEffect> getTeamEffects() {
        return this.teamEffects;
    }

    public List<PotionEffect> getLiveTeamEffects() {
        return this.originalTeam.getTeamEffects();
    }

    public boolean isLiveBedDestroyed() {
        return this.originalTeam.isBedDestroyed();
    }

    public ITeam getOriginalTeam() {
        return this.originalTeam;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TeamSnapshot that = (TeamSnapshot) o;

        return getColor().equals(that.getColor());
    }

    @Override
    public int hashCode() {
        return getColor().hashCode();
    }
}
