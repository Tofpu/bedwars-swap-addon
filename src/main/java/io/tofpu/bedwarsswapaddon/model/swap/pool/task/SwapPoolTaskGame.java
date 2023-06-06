package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.model.meta.message.MessageHolder;
import io.tofpu.bedwarsswapaddon.util.TeamUtil;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;

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

        final List<Map.Entry<TeamSnapshot, TeamSnapshot>> swapMap = algorithm.find(filteredTeams);

        for (final Map.Entry<TeamSnapshot, TeamSnapshot> entry : swapMap) {
            final TeamSnapshot from = entry.getKey();
            final TeamSnapshot to = entry.getValue();

            final MessageHolder messageHolder = MessageHolder.get();
            TeamUtil.broadcastMessageTo(messageHolder.swapMessageAnnouncement.replace(
                    "%team%", TeamUtil.teamOf(from.getColor())), to);
            TeamUtil.broadcastMessageTo(messageHolder.swapMessageAnnouncement.replace(
                    "%team%", TeamUtil.teamOf(to.getColor())), from);

            TeamUtil.broadcastTitleTo(messageHolder.swapTitleAnnouncement.replace(
                    "%team%", TeamUtil.teamOf(to.getColor())), from);
            TeamUtil.broadcastTitleTo(messageHolder.swapTitleAnnouncement.replace(
                    "%team%", TeamUtil.teamOf(from.getColor())), to);

            context.getArenaTracker().swapTeams(from, to);
            from.apply(to.getLive());
            to.apply(from.getLive());
        }
    }
}
