package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.model.meta.message.MessageHolder;
import io.tofpu.bedwarsswapaddon.util.TeamUtil;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SwapPoolTaskGame extends SwapPoolTaskBase {
    private static final String AFTER_FORMAT_DEBUG =
            "\nAfter result: \n" + "From: %s\n" + "  Size: %s : %s members\n" +
            "  Members: %s : %s\n" + "To: %s\n" + "  Size: %s : %s members\n" +
            "  Members: %s : %s";

    private static final String FOUND_TEAM_DEBUG =
            "\n" + "Iteration Item[%s]:\n " + " Team: %s\n" + "  Size: %s : %s\n" +
            "  Members: %s : %s";

    private static final String NEXT_FOUND_TEAM_DEBUG =
            "\n" + "Next Item:\n " + " Team: %s\n" + "  Size: %s : %s\n" +
            "  Members: %s : %s";

    private static final String SWAPPING_WITH_DEBUG = "Swapping both %s and %s";

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

        // Rules:
        // 1. Use the live data for write, and use the copy for read.

        final Map<TeamSnapshot, TeamSnapshot> swapMap = new HashMap<>();

        for (int i = 0; i < filteredTeams.size(); i++) {
            boolean pauseSearch = false;
            final TeamSnapshot team = filteredTeams.get(i);

            // if the next team is the last team
            if (i == filteredTeams.size() - 2) {
                final TeamSnapshot nextTeam = filteredTeams.get(i + 1);
                // do not swap teams that have different bed destroyed states
                if (nextTeam.isBedDestroyed() != team.isBedDestroyed()) {
                    LogHandler.get().debug("Nothing to swap with. Exiting...");
                    return;
                }
                swapMap.put(team, nextTeam);
                pauseSearch = true;
            }

            // if the search was complete, do not continue
            if (pauseSearch) {
                break;
            }

            for (int j = i + 1; j < filteredTeams.size(); j++) {
                final TeamSnapshot nextTeam = filteredTeams.get(j);

                if (swapMap.containsValue(nextTeam) || nextTeam.getColor().equals(team.getColor()) || nextTeam.isBedDestroyed() != team.isBedDestroyed()) {
                    continue;
                }

                swapMap.put(team, nextTeam);
                break;
            }
        }


        LogHandler.get().debug("swapMap: " + swapMap.entrySet().stream().map(entry -> entry.getKey().getName() + " " +
                                                                                      "<-> " + entry.getValue().getName()).collect(Collectors.joining(", ")));
        for (final Map.Entry<TeamSnapshot, TeamSnapshot> entry : swapMap.entrySet()) {
            final TeamSnapshot from = entry.getKey();
            final TeamSnapshot to = entry.getValue();
            LogHandler.get().debug(String.format(SWAPPING_WITH_DEBUG, from.getColor(), to.getColor()));

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
