package io.tofpu.bedwarsswapaddon.model.swap.rejoin;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MainRejoinProvider extends RejoinProviderBase {
    private final Map<IArena, ArenaTracker> arenaTrackerMap;

    public MainRejoinProvider() {
        this.arenaTrackerMap = new HashMap<>();
    }

    @Override
    public void track(final IArena arena) {
        this.arenaTrackerMap.putIfAbsent(arena, new ArenaTracker(arena, arena.getTeams()));
    }

    @Override
    public void untrack(final IArena arena) {
        this.arenaTrackerMap.remove(arena);
    }

    @Override
    public ArenaTracker get(final IArena arena) {
        return this.arenaTrackerMap.get(arena);
    }

    @Override
    public void rejoin(final RejoinContext context) {
        final Player player = context.getPlayer();

        final RejoinProviderBase.ArenaTracker arenaTracker = arenaTrackerMap.get(context.getArena());
        for (final RejoinProviderBase.ArenaTracker.TeamTracker teamTracker : arenaTracker.getTeamTrackers()) {
            if (!teamTracker.isInTeam(player)) {
                continue;
            }

            teamTracker.rejoin(player);
            break;
        }
    }
}
