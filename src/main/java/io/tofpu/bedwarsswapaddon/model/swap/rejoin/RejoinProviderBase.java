package io.tofpu.bedwarsswapaddon.model.swap.rejoin;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import io.tofpu.bedwarsswapaddon.wrapper.RejoinArenaWrapper;
import io.tofpu.bedwarsswapaddon.wrapper.TeamSnapshot;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class RejoinProviderBase {
    public abstract void track(final IArena arena);
    public abstract void untrack(final IArena arena);
    public abstract ArenaTracker get(final IArena arena);

    public abstract void rejoin(final RejoinContext context);

    public static class RejoinContext {
        private final Player player;
        private final Arena arena;

        public RejoinContext(final Player player, final IArena arena) {
            this.player = player;
            this.arena = (Arena) arena;
        }

        public Player getPlayer() {
            return this.player;
        }

        public Arena getArena() {
            return this.arena;
        }
    }

    public static class ArenaTracker {
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

        public void swapTeams(final TeamSnapshot from, final TeamSnapshot to) {
            for (final TeamTracker teamTracker : this.teamTrackers) {
                for (final Player player : from.getCachedMembers()) {
                    if (teamTracker.isInTeam(player)) {
                        teamTracker.setCurrentTeam(to.getLive());
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
