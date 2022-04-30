package io.tofpu.bedwarsswapaddon.util;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;

public class TeamUtil {
    public static void broadcastMessageTo(final String message, final ITeam ...teams) {
        final String coloredMessage = MessageUtil.colorize(message);
        for (final ITeam team : teams) {
            for (final Player player : team.getMembers()) {
                player.sendMessage(coloredMessage);
            }
        }
    }
}
