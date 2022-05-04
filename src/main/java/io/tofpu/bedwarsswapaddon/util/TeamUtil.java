package io.tofpu.bedwarsswapaddon.util;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.cryptomorin.xseries.messages.Titles;
import io.tofpu.bedwarsswapaddon.model.meta.adventure.AdventureHolder;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamUtil {
    public static void broadcastMessageTo(final String message, final ITeam... teams) {
        for (final ITeam team : teams) {
            for (final Player player : team.getMembers()) {
                AdventureHolder.get()
                        .message(player, message);
            }
        }
    }

    public static void broadcastTitleTo(final String message, final ITeam... teams) {
        final String[] split = BukkitComponentSerializer.legacy().serialize(ColorUtil.translate(message)).split("\n");
        final String title = split.length > 0 ? split[0] : message;
        final String subtitle = split.length > 1 ? split[1] : "";

        for (final ITeam team : teams) {
            for (final Player player : team.getMembers()) {
                Titles.sendTitle(player, title, subtitle);
            }
        }
    }

    public static String teamOf(final TeamColor color) {
        final TextComponent component = (TextComponent) ColorUtil.translateLegacy(
                "§" + color.chat().getChar() + color.name()
                ).applyFallbackStyle(TextDecoration.ITALIC.withState(false));

        return MiniMessage.miniMessage().serialize(component);
    }

    public static String toString(final List<Player> players) {
        final StringBuilder builder = new StringBuilder();
        for (final Player player : players) {
            builder.append(player.getName()).append(", ");
        }
        return builder.substring(0, Math.max(0, builder.length() - 2));
    }
}
