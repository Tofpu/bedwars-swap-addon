package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.debug.LogHandler;
import io.tofpu.bedwarsswapaddon.model.message.MessageHolder;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.InventorySwapTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.LocationSwapTask;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl.TeamSwapTask;
import io.tofpu.bedwarsswapaddon.model.wrapper.TeamWrapper;
import io.tofpu.bedwarsswapaddon.util.TeamUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SwapPoolTaskGame extends SwapPoolTaskBase {

    private static final String AFTER_FORMAT_DEBUG =
            "\nAfter result: \n" + "From: %s\n" + "  Size: %s : %s members\n" +
            "  Members: %s : %s\n" + "To: %s\n" + "  Size: %s : %s members\n" +
            "  Members: %s : %s";

    private static final String FOUND_TEAM_DEBUG =
            "\n" + "Iteration Item:\n " + " Team: %s\n" + "  Size: %s : %s\n" +
            "  Members: %s : %s";

    private static final String NEXT_FOUND_TEAM_DEBUG =
            "\n" + "Next Item:\n " + " Team: %s\n" + "  Size: %s : %s\n" +
            "  Members: %s : %s";

    private static final String SWAPPING_WITH_DEBUG = "Swapping %s with %s";

    @Override
    public void run(final SwapPoolTaskContext context) {
        LogHandler.get()
                .debug("SwapPoolTaskGame#run has been called for " + context.getArena()
                        .getArenaName() + " arena");

        final IArena arena = context.getArena();

        final List<TeamWrapper> filteredTeams = arena.getTeams()
                .stream()
                .filter(team -> team.getMembers()
                                        .size() != 0)
                .map(TeamWrapper::new)
                .collect(Collectors.toList());

        LogHandler.get()
                .debug("Filtered teams: " + filteredTeams);


        int index = 1;
        for (final TeamWrapper team : filteredTeams) {

            LogHandler.get()
                    .debug(String.format(FOUND_TEAM_DEBUG, team.getColor(),
                            team.getSize(),
                            team.getLiveMembers().size(),
                            TeamUtil.toString(team.getLiveMembers()),
                            TeamUtil.toString(team.getLiveMembers())));

            final TeamWrapper nextTeam;

            if ((index) == filteredTeams.size()) {
                nextTeam = filteredTeams.get(0);

                if (nextTeam.getColor() == team.getColor()) {
                    LogHandler.get()
                            .debug("Nothing to swap with. Exiting.");
                    break;
                }
            } else {
                nextTeam = filteredTeams.get(index);
            }

            if (nextTeam == null) {
                LogHandler.get()
                        .debug("No more teams to swap. Exiting.");
                break;
            }

            LogHandler.get()
                    .debug(String.format(NEXT_FOUND_TEAM_DEBUG, team.getColor(),
                            team.getSize(),
                            team.getLiveMembers().size(),
                            TeamUtil.toString(team.getLiveMembers()),
                            TeamUtil.toString(team.getLiveMembers())));

            LogHandler.get().debug(String.format(SWAPPING_WITH_DEBUG, team.getColor(), nextTeam.getColor()));

            final MessageHolder messageHolder = MessageHolder.get();
            TeamUtil.broadcastMessageTo(messageHolder.swapMessageAnnouncement.replace("%team%", TeamUtil.teamOf(nextTeam.getColor())), team);
            TeamUtil.broadcastTitleTo(messageHolder.swapTitleAnnouncement.replace("%team%", TeamUtil.teamOf(nextTeam.getColor())), team);

            subTasksList().forEach(subTask -> subTask.run(new SubTask.SubTaskContext(subTask, arena, team, nextTeam)));

            LogHandler.get().debug(String.format(AFTER_FORMAT_DEBUG,
                    team.getColor(), team.getMembers().size(), team.getLiveMembers().size(),
                    TeamUtil.toString(team.getMembers()), TeamUtil.toString(team.getLiveMembers()),
                    nextTeam.getColor(), nextTeam.getMembers().size(), nextTeam.getLiveMembers().size(),
                    TeamUtil.toString(nextTeam.getMembers()), TeamUtil.toString(nextTeam.getLiveMembers())));

            index++;
        }
    }

    @Override
    public List<SubTask> subTasksList() {
        return Arrays.asList(new LocationSwapTask(), new InventorySwapTask(), new TeamSwapTask());
    }
}
