package io.tofpu.bedwarsswapaddon.swappage;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import io.tofpu.bedwarsswapaddon.LogHandler;
import io.tofpu.bedwarsswapaddon.MessageHolder;
import io.tofpu.bedwarsswapaddon.message.MessageServiceHolder;
import io.tofpu.bedwarsswapaddon.snapshot.helper.BedwarsHelper;
import io.tofpu.bedwarsswapaddon.swap.game.pool.task.SwapPoolTaskBase;
import io.tofpu.bedwarsswapaddon.swap.game.pool.task.SwapPoolTaskGame;
import io.tofpu.bedwarsswapaddon.swap.game.rejoin.RejoinProviderBase;
import io.tofpu.bedwarsswapaddon.swappage.object.TestMessageService;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.tofpu.bedwarsswapaddon.snapshot.util.BedwarsMocker.*;
import static io.tofpu.bedwarsswapaddon.snapshot.util.BukkitMocker.mockPluginWithLogger;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GameTest extends BedwarsHelper {
    private final SwapPoolTaskGame game = new SwapPoolTaskGame();

    @BeforeAll
    static void beforeAll() {
        MessageHolder.clear();
    }

    @Test
    void basic() throws NoSuchFieldException, IllegalAccessException {
        List<ITeam> teams = teams(
                bedWarsTeam(TeamColor.RED, players(mockPlayer("RED", at(1, 1, 1))), false),
                bedWarsTeam(TeamColor.BLUE, players(mockPlayer("BLUE", at(2, 2, 2))), false),
                bedWarsTeam(TeamColor.GREEN, players(mockPlayer("GREEN", at(3, 3, 3))), false)
        );
        Arena arena = mockArena(teams);

        LogHandler.init(mockPluginWithLogger());
        LogHandler.get().setDebug(true);

        MessageServiceHolder.init(new TestMessageService());
        MessageHolder.init();

        RejoinProviderBase.ArenaTracker arenaTracker = new RejoinProviderBase.ArenaTracker(arena, teams);
        SwapPoolTaskBase.SwapPoolTaskContext context = new SwapPoolTaskBase.SwapPoolTaskContext(arena, arenaTracker);
        game.run(context);

        ITeam redTeam = teams.get(0);
        Assertions.assertTrue(redTeam.getMembers().size() != 0);
        for (Player member : redTeam.getMembers()) {
            System.out.println(member.getName());
            Assertions.assertTrue(member.getName().contains("BLUE"));
            verify(member, times(1)).teleport(eq(at(1, 1, 1)));
            verify(member, times(1)).sendMessage(contains("RED"));
//            verify(member, times(1)).sendTitle(anyString(), anyString(), anyByte(), anyByte(), anyByte());
        }

        ITeam blueTeam = teams.get(1);
        Assertions.assertTrue(blueTeam.getMembers().size() != 0);
        for (Player member : blueTeam.getMembers()) {
            System.out.println(member.getName());
            Assertions.assertTrue(member.getName().contains("GREEN"));
            verify(member, times(1)).teleport(eq(at(2, 2, 2)));
            verify(member, times(1)).sendMessage(contains("BLUE"));
//            verify(member, times(1)).sendTitle(anyString(), anyString(), anyByte(), anyByte(), anyByte());
        }

        ITeam greenTeam = teams.get(2);
        Assertions.assertTrue(greenTeam.getMembers().size() != 0);
        for (Player member : greenTeam.getMembers()) {
            System.out.println(member.getName());
            Assertions.assertTrue(member.getName().contains("RED"));
            verify(member, times(1)).teleport(eq(at(3, 3, 3)));
            verify(member, times(1)).sendMessage(contains("GREEN"));
//            verify(member, times(1)).sendTitle(anyString(), anyString(), anyByte(), anyByte(), anyByte());
        }
    }

    @Test
    void oneone() {
        Player target = mockPlayer("baby", at(2, 2, 2));
        target.sendMessage("<1ajdaoijda>hi!<?adadadj>");

        verify(target, times(1)).sendMessage(contains("hi"));
    }
}
