package io.tofpu.bedwarsswapaddon.wrapper.snapshot;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.wrapper.PlayerSnapshot;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamMemberSnapshot {
    private final Location targetSpawn;
    private final ArrayList<PlayerSnapshot> members;
    private final ArrayList<PlayerSnapshot> cachedMembers;

    public TeamMemberSnapshot(ITeam team) {
        this.targetSpawn = team.getSpawn();

        this.members = new ArrayList<>();
        this.cachedMembers = new ArrayList<>();

        for (final Player player : team.getMembers()) {
            this.members.add(PlayerSnapshot.of(player));
        }

        for (Player player : team.getMembersCache()) {
            this.cachedMembers.add(PlayerSnapshot.of(player));
        }
    }

    public void apply(ITeam team) {
        applyLocation(team);

        team.getMembers().clear();
        team.getMembers().addAll(members.stream().map(PlayerSnapshot::getPlayer).collect(Collectors.toList()));

        team.getMembersCache().clear();
        team.getMembersCache().addAll(getCachedMembers());
    }

//    private void applyLocation(ITeam team) {
//        List<Player> teamMembers = team.getMembers();
//        for (int i = 0; i < members.size(); i++) {
//            Player player = members.get(i).getPlayer();
//            Player target = getSafety(teamMembers, i);
//
//            boolean playerSafe = player != null && isAvailable(team.getArena(), player);
//            boolean targetSafe = target != null && isAvailable(team.getArena(), target);
//
//            if (!targetSafe && playerSafe) {
////                player.teleport(team.getSpawn());
//                player.teleport(targetSpawn);
//                player.setVelocity(VectorUtil.EMPTY_VELOCITY);
//            }
//
//            if (targetSafe && playerSafe) {
//                System.out.println("Teleporting player " + player.getName() + " from " + player.getLocation() + " to " + target.getLocation());
//                player.teleport(target.getLocation());
//                player.setVelocity(VectorUtil.EMPTY_VELOCITY);
//            }
//        }
//    }

    private void applyLocation(ITeam team) {
        List<Player> teamMembers = team.getMembers();
        for (int i = 0; i < teamMembers.size(); i++) {
            Player player = teamMembers.get(i);
            PlayerSnapshot target = getSafety(members, i);

            boolean playerSafe = isAvailable(team.getArena(), player);
            boolean availableTarget = target != null && target.getPlayer() != null && target.getPlayer().isOnline();

            boolean targetSafe = false;
            if (availableTarget) {
                targetSafe = isAvailable(team.getArena(), target.getPlayer());
            }

            System.out.println(targetSafe + " " + playerSafe);
            Location location = null;
            if (!targetSafe && playerSafe) {
                location = targetSpawn;
            }

            if (targetSafe && playerSafe) {
                location = target.getLocation();
            }

            if (location != null) {
                System.out.println("Teleporting player " + player.getName() + " from " + player.getLocation() + " to " + location);
                player.teleport(location);
            }
        }
    }

    private boolean isAvailable(final IArena arena, final Player player) {
        return !isUnavailable(arena, player);
    }

    private boolean isUnavailable(final IArena arena, final Player player) {
        return arena.isSpectator(player) || arena.isReSpawning(player);
    }

    private <T> T getSafety(final List<T> list, final int index) {
        if (index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    public List<Player> getCachedMembers() {
        return cachedMembers.stream().map(PlayerSnapshot::getPlayer).collect(Collectors.toList());
    }
}
