package io.tofpu.bedwarsswapaddon;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.model.meta.message.MessageHolder;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskGame;
import io.tofpu.bedwarsswapaddon.model.swap.rejoin.RejoinProviderBase;
import io.tofpu.bedwarsswapaddon.wrapper.TeamSnapshot;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.Test;
import revxrsal.commands.util.Collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// todo:
// The team swappage works, but from do not receive the message
// tab is not updating fast enough? because it shows that they
//  are in same color, when they are in fact not
//
public class SwapTest {
    @Test
    public void test_two_teams_non_broken_beds() {
        final SwapPoolTaskGame game = new SwapPoolTaskGame();
        final Plugin plugin = mockPlugin();

        initHandlers(plugin);

        final List<TeamSnapshot> teamsList = teams(
                mockTeam(TeamColor.RED, 2, false),
                mockTeam(TeamColor.BLUE, 2, false)
        );
        final IArena arena = mockArena(teamsList);

        final RejoinProviderBase.ArenaTracker arenaTracker =
                new RejoinProviderBase.ArenaTracker(arena, arena.getTeams());
        game.run(new SwapPoolTaskBase.SwapPoolTaskContext(arena, arenaTracker));

        assertEquals(teamsList.get(0)
                             .getLive()
                             .getMembers()
                             .size(), 2);
        assertEquals(teamsList.get(0)
                             .getLive()
                             .getMembersCache()
                             .size(), 2);

        assertEquals(teamsList.get(1)
                             .getLive()
                             .getMembers()
                             .size(), 2);
        assertEquals(teamsList.get(1)
                             .getLive()
                             .getMembersCache()
                             .size(), 2);

        assertEquals(teamsList.get(1).getLive().getMembers(), teamsList.get(0).getCachedMembers());
        assertEquals(teamsList.get(0).getLive().getMembers(), teamsList.get(1).getCachedMembers());
    }

    @Test
    public void test_two_teams_with_both_broken_beds() {
        final SwapPoolTaskGame game = new SwapPoolTaskGame();
        final Plugin plugin = mockPlugin();

        initHandlers(plugin);

        final List<TeamSnapshot> teamsList = teams(
                mockTeam(TeamColor.RED, 2, true),
                mockTeam(TeamColor.BLUE, 2, true)
        );
        final IArena arena = mockArena(teamsList);

        final RejoinProviderBase.ArenaTracker arenaTracker =
                new RejoinProviderBase.ArenaTracker(arena, arena.getTeams());
        game.run(new SwapPoolTaskBase.SwapPoolTaskContext(arena, arenaTracker));

        assertEquals(teamsList.get(0)
                             .getLive()
                             .getMembers()
                             .size(), 2);
        assertEquals(teamsList.get(0)
                             .getLive()
                             .getMembersCache()
                             .size(), 2);

        assertEquals(teamsList.get(1)
                             .getLive()
                             .getMembers()
                             .size(), 2);
        assertEquals(teamsList.get(1)
                             .getLive()
                             .getMembersCache()
                             .size(), 2);


        assertEquals(teamsList.get(1).getLive().getMembers(), teamsList.get(0).getCachedMembers());
        assertEquals(teamsList.get(0).getLive().getMembers(), teamsList.get(1).getCachedMembers());
    }

    private IArena mockArena(final List<TeamSnapshot> teamsList) {
        final IArena arena = mock(IArena.class);

        when(arena.getTeams()).thenReturn(teamsList.stream()
                                                  .map(TeamSnapshot::getLive)
                                                  .collect(Collectors.toList()));

        return arena;
    }

    private List<TeamSnapshot> teams(final TeamSnapshot... teams) {
        return Collections.listOf(teams);
    }

    private void initHandlers(final Plugin mock) {
        LogHandler.init(mock);
        LogHandler.get()
                .setDebug(true);

        MessageHolder.init();
    }

    private Plugin mockPlugin() {
        final Plugin mock = mock(Plugin.class);
        when(mock.getLogger()).thenReturn(Logger.getGlobal());
        return mock;
    }

    private TeamSnapshot mockTeam(TeamColor color, int players) {
        return mockTeam(color, players, false);
    }

    private TeamSnapshot mockTeam(TeamColor color, int players, boolean bedDestroyed) {
        final BedWarsTeam team = mock(BedWarsTeam.class);

        final List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            playerList.add(mockPlayer(color + "-" + i));
        }
        when(team.getName()).thenReturn(color.name());
        when(team.getColor()).thenReturn(color);

        when(team.getMembers()).thenReturn(new ArrayList<>());
        team.getMembers().addAll(playerList);

        when(team.getMembersCache()).thenReturn(new ArrayList<>());
        team.getMembersCache().addAll(playerList);

        when(team.isBedDestroyed()).thenReturn(bedDestroyed);

        when(team.getTeamUpgradeTiers()).thenReturn(new ConcurrentHashMap<>());
        when(team.getSwordsEnchantments()).thenReturn(new ArrayList<>());
        when(team.getArmorsEnchantments()).thenReturn(new ArrayList<>());
        when(team.getBowsEnchantments()).thenReturn(new ArrayList<>());
        when(team.getActiveTraps()).thenReturn(new LinkedList<>());
        when(team.getBaseEffects()).thenReturn(new ArrayList<>());
        when(team.getTeamEffects()).thenReturn(new ArrayList<>());

        return new TeamSnapshot(team);
    }

    private Player mockPlayer(String name) {
        final Player player = mock(Player.class);

        when(player.getName()).thenReturn(name);
        when(player.getLocation()).thenReturn(mock(Location.class));
        when(player.getVelocity()).thenReturn(mock(Vector.class));
        when(player.getInventory()).thenReturn(mock(PlayerInventory.class));

        when(player.getInventory()
                     .getArmorContents()).thenReturn(new ItemStack[4]);

        return player;
    }
}
