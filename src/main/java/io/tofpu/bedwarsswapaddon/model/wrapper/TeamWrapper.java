package io.tofpu.bedwarsswapaddon.model.wrapper;

import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.arena.Arena;
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

public class TeamWrapper implements ITeam {
    private final ITeam originalTeam;

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
    private boolean bedDestroyed = false;
    private Vector killDropsLoc = null;
    private final List<IGenerator> generators;
    private final ConcurrentHashMap<String, Integer> teamUpgradeList;
    private final List<PotionEffect> base;
    private final List<TeamEnchant> bowsEnchantments;
    private final List<TeamEnchant> swordsEnchantments;
    private final List<TeamEnchant> armorsEnchantments;
    private int dragons;

    public TeamWrapper(final ITeam team) {
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
        this.arena = (Arena) team.getArena();
        this.generators = new ArrayList<>(team.getGenerators());
        this.teamUpgradeList = new ConcurrentHashMap<>(team.getTeamUpgradeTiers());
        this.base = new ArrayList<>(team.getBaseEffects());
        this.bowsEnchantments = new ArrayList<>(team.getBowsEnchantments());
        this.swordsEnchantments = new ArrayList<>(team.getSwordsEnchantments());
        this.armorsEnchantments = new ArrayList<>(team.getArmorsEnchantments());
        this.enemyBaseEnterTraps = new LinkedList<>(team.getActiveTraps());
        this.dragons = team.getDragons();

        this.members = new ArrayList<>(team.getMembers());
        this.membersCache = new ArrayList<>(team.getMembersCache());
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
        return null;
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
        return this.teamUpgradeList;
    }

    @Override
    public List<TeamEnchant> getBowsEnchantments() {
        return this.bowsEnchantments;
    }

    @Override
    public List<TeamEnchant> getSwordsEnchantments() {
        return this.swordsEnchantments;
    }

    @Override
    public List<TeamEnchant> getArmorsEnchantments() {
        return this.armorsEnchantments;
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
    }

    @Override
    public void reJoin(final Player player, final int i) {
        // do nothing
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
        return this.bedDestroyed;
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

    public Map<String, Integer> getLiveTeamUpgradeTiers() {
        return this.originalTeam.getTeamUpgradeTiers();
    }

    public List<EnemyBaseEnterTrap> getLiveActiveTraps() {
        return this.originalTeam.getActiveTraps();
    }

    public ITeam getOriginalTeam() {
        return this.originalTeam;
    }
}
