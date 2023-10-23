package io.tofpu.bedwarsswapaddon.model.swap.rejoin;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import io.tofpu.bedwarsswapaddon.wrapper.RejoinArenaWrapper;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.tofpu.bedwarsswapaddon.util.ProgramCorrectnessUtil.requireState;

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

        public void swapTeams(final TeamSnapshot from, final ITeam to) {
            requireState(!from.getColor().equals(to.getColor()), "Cannot swap teams with same color");
            for (final TeamTracker teamTracker : this.teamTrackers) {
                if (!teamTracker.getCurrentTeam().getColor().equals(from.getColor())) {
                    continue;
                }

                teamTracker.setPlayers(to.getMembersCache());
                return;
            }
        }

        public static class TeamTracker {
            private final List<UUID> players;
            private final ITeam currentTeam;

            public TeamTracker(final List<Player> players, final ITeam currentTeam) {
                this.players = players.stream().map(Entity::getUniqueId).collect(Collectors.toList());
                this.currentTeam = currentTeam;
            }

            public boolean isInTeam(final Player player) {
                return this.players.contains(player.getUniqueId());
            }

            public ITeam getCurrentTeam() {
                return this.currentTeam;
            }

            public void rejoin(final Player player) {
                new RejoinArenaWrapper((Arena) this.currentTeam.getArena()).rejoin(player, currentTeam);
            }

            public void setPlayers(List<Player> newPlayerList) {
                List<UUID> uuidList = newPlayerList.stream().map(Entity::getUniqueId).collect(Collectors.toList());
                this.players.clear();

                System.out.println(this.currentTeam.getColor() + ": previous: " + this.players + " and now: " + uuidList);
                this.players.addAll(uuidList);
            }
        }
    }
}
