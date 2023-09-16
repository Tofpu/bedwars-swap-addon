package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.sidebar.SidebarService;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.model.meta.message.MessageHolder;
import io.tofpu.bedwarsswapaddon.util.TeamUtil;
import io.tofpu.bedwarsswapaddon.wrapper.LiveObject;
import io.tofpu.bedwarsswapaddon.wrapper.TeamWrapper;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;
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
                .filter(team -> team.getMembers()
                                        .size() != 0)
                .map(TeamSnapshot::new)
                .collect(Collectors.toList());

        LogHandler.get()
                .debug("Filtered teams: " + filteredTeams.stream().map(TeamSnapshot::getName)
                        .collect(Collectors.joining(", ")));

        final List<Map.Entry<TeamSnapshot, TeamSnapshot>> swapMap = algorithm.find(filteredTeams)
                .stream().map(entry -> {
//                    TeamSnapshot value = entry.getValue();
//                    LiveObject<ITeam> wrapper = TeamWrapper.of(entry.getKey().getLive());
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue());
                })
                .collect(Collectors.toList());

        LogHandler.get()
                .debug("selected teams: " + swapMap.stream().map(entry -> entry.getKey().getColor() + " <- " + entry.getValue().getColor())
                        .collect(Collectors.joining(", ")));

        for (final Map.Entry<TeamSnapshot, TeamSnapshot> entry : swapMap) {
            TeamSnapshot currentTeamSnapshot = entry.getKey();
            LiveObject<ITeam> current = TeamWrapper.of(currentTeamSnapshot, currentTeamSnapshot.getLive());
            ITeam currentTeam = current.object();
            TeamSnapshot nextSnapshot = entry.getValue();

            System.out.println("Teleporting " + nextSnapshot.getColor() + " to " + currentTeam.getColor());

            String currentTeamName = currentTeam.getColor().chat() + currentTeam.getName();
            final MessageHolder messageHolder = MessageHolder.get();
            TeamUtil.broadcastMessageTo(messageHolder.swapMessageAnnouncement.replace(
                    "%team%", currentTeamName), nextSnapshot);

            TeamUtil.broadcastTitleTo(messageHolder.swapTitleAnnouncement.replace(
                    "%team%", currentTeamName), nextSnapshot);

            context.getArenaTracker().swapTeams(currentTeamSnapshot, nextSnapshot.getLive());

            current.use(nextSnapshot);
//            nextTeam.use(currentTeam);
//            snapshot.use(target);
//            team.apply(target.getLive());
        }

        // updates the tab list with the new teams
        arena.getTeams().forEach(team -> {
            for (Player player : team.getMembers()) {
                SidebarService instance = SidebarService.getInstance();
                instance.remove(player);
                instance.giveSidebar(player, arena, false);
            }
        });
    }
}
