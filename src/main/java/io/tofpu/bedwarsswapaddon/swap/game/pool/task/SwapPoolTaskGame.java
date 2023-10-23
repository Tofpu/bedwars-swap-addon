package io.tofpu.bedwarsswapaddon.swap.game.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.sidebar.SidebarService;
import io.tofpu.bedwarsswapaddon.LogHandler;
import io.tofpu.bedwarsswapaddon.MessageHolder;
import io.tofpu.bedwarsswapaddon.swap.snapshot.team.TeamSnapshot;
import io.tofpu.bedwarsswapaddon.swap.wrapper.TeamWrapper;
import io.tofpu.bedwarsswapaddon.util.TeamUtil;
import org.bukkit.entity.Player;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SwapPoolTaskGame extends SwapPoolTaskBase {
    private final SwappageAlgorithm algorithm;

    public SwapPoolTaskGame(SwappageAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public SwapPoolTaskGame() {
        this(new SwappageAlgorithm());
    }

    @Override
    public void run(final SwapPoolTaskContext context) {
        LogHandler.get()
                .debug("SwapPoolTaskGame#run has been called for " + context.getArena()
                        .getArenaName() + " arena");

        final IArena arena = context.getArena();

        final List<TeamSnapshot> filteredTeams = arena.getTeams()
                .stream()
                // excludes teams that have no members
                .filter(team -> team.getMembers().size() != 0)
                // snaps a copy (therefore, snapshot) of the team
                .map(TeamSnapshot::new)
                .collect(Collectors.toList());
        LogHandler.get()
                .debug("Filtered teams: " + filteredTeams.stream().map(TeamSnapshot::getName)
                        .collect(Collectors.joining(", ")));

        // determines a list of teams that shall be swapped
        final List<Map.Entry<TeamSnapshot, TeamSnapshot>> swapMap = algorithm.find(filteredTeams)
                .stream().map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        LogHandler.get()
                .debug("selected teams: " + swapMap.stream().map(entry -> entry.getKey().getColor() + " <- " + entry.getValue().getColor())
                        .collect(Collectors.joining(", ")));

        for (final Map.Entry<TeamSnapshot, TeamSnapshot> entry : swapMap) {
            TeamSnapshot currentSnapshot = entry.getKey();
            ITeam currentTeam = currentSnapshot.getLive();
            TeamWrapper currentWrapper = TeamWrapper.of(currentSnapshot, currentSnapshot.getLive());

            TeamSnapshot swapTarget = entry.getValue();

            LogHandler.get()
                    .debug("Teleporting " + swapTarget.getColor() + " to " + currentTeam.getColor());

            // notifies members belonging to `next` that they are swapping to `current`
            String currentTeamName = currentTeam.getColor().chat() + currentTeam.getName();
            final MessageHolder messageHolder = MessageHolder.get();
            TeamUtil.broadcastMessageTo(messageHolder.swapMessageAnnouncement.replace(
                    "%team%", currentTeamName), swapTarget);
            TeamUtil.broadcastTitleTo(messageHolder.swapTitleAnnouncement.replace(
                    "%team%", currentTeamName), swapTarget);

            // applies the snapshot belonging to the team they are swapping to
            context.getArenaTracker().swapTeams(currentSnapshot, swapTarget.getLive());
            currentWrapper.use(swapTarget);
        }

        SidebarService instance = SidebarService.getInstance();
        if (instance == null) return;

        // updates the tab list with the new teams
        arena.getTeams().forEach(team -> {
            for (Player player : team.getMembers()) {
                instance.remove(player);
                instance.giveSidebar(player, arena, false);
            }
        });
    }
}
