package io.tofpu.bedwarsswapaddon.swappage;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import io.tofpu.bedwarsswapaddon.model.swap.rejoin.RejoinProviderBase;
import io.tofpu.bedwarsswapaddon.snapshot.helper.BedwarsHelper;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.tofpu.bedwarsswapaddon.snapshot.util.BedwarsMocker.bedWarsTeam;
import static io.tofpu.bedwarsswapaddon.snapshot.util.BedwarsMocker.mockArena;

public class ArenaTrackerTest extends BedwarsHelper {
    @Test
    void constructor_test() throws NoSuchFieldException, IllegalAccessException {
        List<ITeam> teams = teams(
                bedWarsTeam(TeamColor.RED, 2, false),
                bedWarsTeam(TeamColor.BLUE, false)
        );
        Arena arena = mockArena(teams);

        RejoinProviderBase.ArenaTracker arenaTracker = new RejoinProviderBase.ArenaTracker(arena, teams);

        Assertions.assertEquals(teams.size(), arenaTracker.getTeamTrackers().size());
        for (RejoinProviderBase.ArenaTracker.TeamTracker teamTracker : arenaTracker.getTeamTrackers()) {
            Assertions.assertTrue(teams.contains(teamTracker.getCurrentTeam()));
        }
    }

    @Test
    void basic_swap_test() throws NoSuchFieldException, IllegalAccessException {
        List<ITeam> teams = teams(
                bedWarsTeam(TeamColor.RED, 1, false),
                bedWarsTeam(TeamColor.BLUE, 1,false)
        );
        Arena arena = mockArena(teams);

        RejoinProviderBase.ArenaTracker arenaTracker = new RejoinProviderBase.ArenaTracker(arena, teams);

        TeamSnapshot redSnapshot = snapshot(teams.get(0));
        ITeam blueTeam = teams.get(1);

        live(snapshot(blueTeam)).use(redSnapshot);
        arenaTracker.swapTeams(snapshot(blueTeam), redSnapshot.getLive());

        Assertions.assertTrue(blueTeam.getMembers().get(0).getName().contains("RED"));

        assertMemberSwap(arenaTracker, redSnapshot.getLive(), TeamColor.BLUE);
    }

    @Test
    void two_side_swap_test() throws NoSuchFieldException, IllegalAccessException {
        List<ITeam> teams = teams(
                bedWarsTeam(TeamColor.RED, 2, false),
                bedWarsTeam(TeamColor.BLUE, 2,false)
        );
        Arena arena = mockArena(teams);
        RejoinProviderBase.ArenaTracker arenaTracker = new RejoinProviderBase.ArenaTracker(arena, teams);

        ITeam redTeam = teams.get(0);
        TeamSnapshot redSnapshot = snapshot(redTeam);

        ITeam blueTeam = teams.get(1);
        TeamSnapshot blueSnapshot = snapshot(blueTeam);

        live(blueSnapshot).use(redSnapshot);
        arenaTracker.swapTeams(blueSnapshot, redTeam);
        Assertions.assertTrue(blueTeam.getMembers().get(0).getName().contains("RED"));
        assertMemberSwap(arenaTracker, redTeam, TeamColor.BLUE);

        live(redSnapshot).use(blueSnapshot);
        arenaTracker.swapTeams(redSnapshot, blueTeam);
        Assertions.assertTrue(redTeam.getMembers().get(0).getName().contains("BLUE"));
        assertMemberSwap(arenaTracker, blueTeam, TeamColor.RED);
    }

    private static void assertMemberSwap(RejoinProviderBase.ArenaTracker arenaTracker, ITeam targetTeam, TeamColor color) {
//        for (RejoinProviderBase.ArenaTracker.TeamTracker teamTracker : arenaTracker.getTeamTrackers()) {
//            if (!teamTracker.getCurrentTeam().getColor().equals(targetTeam.getColor())) continue;
//
//            for (Player member : targetTeam.getMembers()) {
//                Assertions.assertTrue(teamTracker.isInTeam(member));
//            }
//        }

        for (RejoinProviderBase.ArenaTracker.TeamTracker teamTracker : arenaTracker.getTeamTrackers()) {
            if (teamTracker.getCurrentTeam().getColor() != color) continue;

            for (Player member : targetTeam.getMembers()) {
                Assertions.assertTrue(member.getName().contains("RED"));

//                Assertions.assertEquals(targetTeam.getColor(), teamTracker.getCurrentTeam().getColor());
                Assertions.assertTrue(teamTracker.isInTeam(member));
            }

            return;
        }

        Assertions.fail("Member swap failed");
    }
}
