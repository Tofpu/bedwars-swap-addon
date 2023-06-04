package io.tofpu.bedwarsswapaddon.snapshot.util;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import com.andrei1058.bedwars.configuration.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static io.tofpu.bedwarsswapaddon.snapshot.util.BukkitMocker.mockServer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BedwarsMocker {
    public static Player mockPlayer(String name, Location at) {
        final Player player = BukkitMocker.mockBasicPlayer(name);

        AtomicReference<Location> location = new AtomicReference<>(at);
        doAnswer(invocation -> location.get()).when(player).getLocation();

        doAnswer(invocation -> {
            Location newLocation = invocation.getArgument(0);
            location.set(newLocation);
            return true;
        }).when(player).teleport(any(Location.class));

        when(player.getVelocity()).thenReturn(mock(Vector.class));
        when(player.getInventory()).thenReturn(mock(PlayerInventory.class));

        when(player.getInventory().getArmorContents()).thenReturn(new ItemStack[4]);

        return player;
    }

    public static ITeam bedWarsTeam(TeamColor teamColor, List<Player> players, Location at) {
        BedWarsTeam bedWarsTeam = bedWarsTeam(teamColor, players, false);

        doReturn(at).when(bedWarsTeam).getSpawn();

        return bedWarsTeam;
    }

    @NotNull
    public static BedWarsTeam bedWarsTeam(TeamColor color, List<Player> playerList, boolean bedDestroyed) {
        final BedWarsTeam team = bedWarsTeam(color, bedDestroyed);

        when(team.getMembers()).thenReturn(new ArrayList<>());
        team.getMembers().addAll(playerList);

        when(team.getMembersCache()).thenReturn(new ArrayList<>());
        team.getMembersCache().addAll(playerList);
        return team;
    }

    @NotNull
    public static BedWarsTeam bedWarsTeam(TeamColor color, int players, boolean bedDestroyed) {
        final List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            String name = color + "-" + i;
            playerList.add(mockPlayer(name, mock(Location.class)));
        }
        return bedWarsTeam(color, playerList, bedDestroyed);
    }

    @NotNull
    public static BedWarsTeam bedWarsTeam(TeamColor color, boolean bedDestroyed) {
        final BedWarsTeam team = mock(BedWarsTeam.class);

        Arena arena;
        try {
            arena = mockArena();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        when(team.getArena()).thenReturn(arena);
        when(team.getName()).thenReturn(color.name());
        when(team.getColor()).thenReturn(color);

        final AtomicBoolean bedDestroyedHolder = new AtomicBoolean(bedDestroyed);
        doAnswer(invocation -> {
            bedDestroyedHolder.set(invocation.getArgument(0));
            return null;
        }).when(team).setBedDestroyed(anyBoolean());

        doAnswer(invocation -> bedDestroyedHolder.get()).when(team).isBedDestroyed();

        ConcurrentHashMap<String, Integer> upgradeTiers = new ConcurrentHashMap<>();
        when(team.getTeamUpgradeTiers()).thenReturn(upgradeTiers);

        ArrayList<TeamEnchant> swordEnchants = new ArrayList<>();
        when(team.getSwordsEnchantments()).thenReturn(swordEnchants);

        ArrayList<TeamEnchant> armorEnchants = new ArrayList<>();
        when(team.getArmorsEnchantments()).thenReturn(armorEnchants);

        ArrayList<TeamEnchant> bowEnchants = new ArrayList<>();
        when(team.getBowsEnchantments()).thenReturn(bowEnchants);

        LinkedList<EnemyBaseEnterTrap> activeTraps = new LinkedList<>();
        when(team.getActiveTraps()).thenReturn(activeTraps);

        ArrayList<PotionEffect> baseEffects = new ArrayList<>();
        when(team.getBaseEffects()).thenReturn(baseEffects);

        ArrayList<PotionEffect> teamEffects = new ArrayList<>();
        when(team.getTeamEffects()).thenReturn(teamEffects);
        return team;
    }

    public static Arena mockArena() throws NoSuchFieldException, IllegalAccessException {
        Server server = mockServer();

        Field field = Bukkit.class.getDeclaredField("server");
        field.setAccessible(true);
        field.set(null, server);

        try {
            BedWars.config = mock(MainConfig.class);
        } catch (Exception ignored) {
        }

        Arena mock = mock(Arena.class);

        doAnswer(invocation -> {
            Player target = invocation.getArgument(0);
            if (target == null) return false;
            for (Player player : ArenaSpectator.list()) {
                if (target.getUniqueId().equals(player.getUniqueId())) {
                    return true;
                }
            }
            return false;
        }).when(mock).isSpectator(any(Player.class));

        return mock;
    }
}
