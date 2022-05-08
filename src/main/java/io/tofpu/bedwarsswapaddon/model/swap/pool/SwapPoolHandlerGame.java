package io.tofpu.bedwarsswapaddon.model.swap.pool;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ReJoin;
import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskBase;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.SwapPoolTaskGame;
import io.tofpu.bedwarsswapaddon.util.TimeUtil;
import io.tofpu.bedwarsswapaddon.wrapper.RejoinArenaWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class SwapPoolHandlerGame extends SwapPoolHandlerBase<Map<IArena, Long>> {
    private static final String FOUND_ARENA_DEBUG = "Found arena: %s with elapsed time:" +
                                                    " %s seconds and target time: %s seconds";
    private static final String RUNNING_TASK_DEBUG = "Running task: %s";
    public static final String LESS_THAN_TWO_TEAMS_DEBUG = "Arena %s has less than 2 " +
                                                           "teams, removing...";

    private final Map<IArena, Long> arenaMap;
    private final Map<IArena, ArenaTracker> playerTeamTrackerMap;
    private int minimumInterval, maximumInterval = -1;
    private BukkitTask task;

    public SwapPoolHandlerGame(final JavaPlugin plugin, final BedWars bedwarsApi) {
        super(plugin, bedwarsApi);
        this.arenaMap = new HashMap<>();
        this.playerTeamTrackerMap = new HashMap<>();
    }

    @Override
    public void init() {
        LogHandler.get().log("Initializing SwapPoolHandlerGame tasks...");

        this.minimumInterval =
                ConfigurationHandler.get().getSettingsHolder().getMinimumInterval();
        this.maximumInterval = ConfigurationHandler.get()
                .getSettingsHolder()
                .getMaximumInterval();

        this.task = Bukkit.getScheduler().runTaskTimer(this.getPlugin(), () -> {
                    for (final Map.Entry<IArena, Long> entry : getArenas().entrySet()) {
                        final IArena arena = entry.getKey();
                        final Long lastSwap = entry.getValue();

                        if (arena.getStatus() != GameState.playing) {
                            this.arenaMap.remove(arena);
                            continue;
                        }

                        final long elapsedSeconds = TimeUtil.timeElapsedSeconds(lastSwap);
                        final long randomizedInterval = ThreadLocalRandom.current()
                                .nextInt(this.minimumInterval, this.maximumInterval);

                        LogHandler.get()
                                .debug(String.format(FOUND_ARENA_DEBUG, arena.getArenaName(), elapsedSeconds,
                                        randomizedInterval));

                        final List<ITeam> filteredTeams = arena.getTeams()
                                .stream()
                                .filter(team -> team.getSize() != 0)
                                .collect(Collectors.toList());

                        if (filteredTeams.size() < 2) {
                            LogHandler.get()
                                    .debug(String.format(LESS_THAN_TWO_TEAMS_DEBUG, arena.getArenaName()));
                            this.arenaMap.remove(arena);
                            continue;
                        }

                        this.playerTeamTrackerMap.putIfAbsent(arena, new ArenaTracker(arena, filteredTeams));

                        if (elapsedSeconds >= randomizedInterval) {
                            LogHandler.get()
                                    .debug(String.format(RUNNING_TASK_DEBUG, arena.getArenaName()));

                            executeTask(arena);
                            this.arenaMap.put(arena, System.currentTimeMillis());
                        }
                    }
                }, 80L, 10L);
    }

    public void executeTask(final IArena arena) {
        super.task.run(new SwapPoolTaskBase.SwapPoolTaskContext(arena, this.playerTeamTrackerMap.get(arena)));
    }

    @Override
    public void reload() {
        if (this.task != null) {
            this.task.cancel();
        }

        init();
    }

    @Override
    public SwapPoolTaskBase establishPoolTask() {
        return new SwapPoolTaskGame();
    }

    @Override
    public void registerArena(final IArena arena) {
        if (this.arenaMap.putIfAbsent(arena, System.currentTimeMillis()) == null) {
            LogHandler.get()
                    .debug("Registering arena " + arena.getArenaName());
        }
    }

    @Override
    public void unregisterArena(final IArena arena) {
        if (this.arenaMap.remove(arena) != null) {
            LogHandler.get()
                    .debug("Unregistering arena " + arena.getArenaName());
        }
    }

    @Override
    public void handleRejoin(final Player player, final IArena arena) {
        final ArenaTracker arenaTracker = playerTeamTrackerMap.get(arena);
        for (final ArenaTracker.TeamTracker teamTracker : arenaTracker.getTeamTrackers()) {
            if (!teamTracker.isInTeam(player)) {
                continue;
            }

            teamTracker.rejoin(player);
            break;
        }
    }

    @Override
    protected Map<IArena, Long> getArenas() {
        return Collections.unmodifiableMap(this.arenaMap);
    }

    public class ArenaTracker {
        private final IArena arena;
        private final List<TeamTracker> teamTrackers;

        public ArenaTracker(final IArena arena, final List<ITeam> teams) {
            this.arena = arena;
            this.teamTrackers = new ArrayList<>();

            for (final ITeam team : teams) {
                this.teamTrackers.add(new TeamTracker(team.getMembers(), team));
            }
        }

        public List<TeamTracker> getTeamTrackers() {
            return Collections.unmodifiableList(this.teamTrackers);
        }

        @Override
        public int hashCode() {
            return getArena().hashCode();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final ArenaTracker that = (ArenaTracker) o;

            return getArena().getArenaName()
                    .equals(that.getArena()
                            .getArenaName());
        }

        public IArena getArena() {
            return this.arena;
        }

        public void swapTeams(final ITeam from, final ITeam to) {
            for (final TeamTracker teamTracker : this.teamTrackers) {
                for (final Player player : from.getMembers()) {
                    if (teamTracker.isInTeam(player)) {
                        teamTracker.setCurrentTeam(to);
                    }
                }
            }
        }

        public class TeamTracker {
            private final Map<UUID, UUID> playerMap;
            private ITeam currentTeam;

            public TeamTracker(final List<Player> players, final ITeam currentTeam) {
                this.playerMap = new HashMap<>();
                this.currentTeam = currentTeam;

                players.forEach(player -> this.playerMap.put(player.getUniqueId(), player.getUniqueId()));
            }

            public boolean isInTeam(final Player player) {
                return this.playerMap.containsKey(player.getUniqueId());
            }

            public ITeam getCurrentTeam() {
                return this.currentTeam;
            }

            public void setCurrentTeam(final ITeam currentTeam) {
                this.currentTeam = currentTeam;
            }

            public void rejoin(final Player player) {
                new RejoinArenaWrapper((Arena) this.currentTeam.getArena()).rejoin(player, currentTeam);
            }
        }
    }
}
