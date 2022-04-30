package io.tofpu.bedwarsswapaddon.util;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.model.adventure.AdventureHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class TeamUtil {
    public static void broadcastMessageTo(final String message, final ITeam ...teams) {
        for (final ITeam team : teams) {
            for (final Player player : team.getMembers()) {
                AdventureHolder.get().message(player, message);
            }
        }
    }
}
