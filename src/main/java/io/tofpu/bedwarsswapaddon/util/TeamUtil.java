package io.tofpu.bedwarsswapaddon.util;

import io.tofpu.bedwarsswapaddon.message.MessageServiceHolder;
import io.tofpu.bedwarsswapaddon.swap.snapshot.team.TeamSnapshot;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeamUtil {
    public static void broadcastMessageTo(String message, final TeamSnapshot... teams) {
        message = legacyToMiniMessage(message);

        for (final TeamSnapshot team : teams) {
            for (final Player player : team.getMemberPlayers()) {
                try {
                    MessageServiceHolder.get()
                            .message(player, message);
                } catch (IllegalStateException exception) {
                    player.sendMessage(message);
                }
            }
        }
    }

    @NotNull
    private static String legacyToMiniMessage(String message) {
        // legacy -> component -> serialized mini-message
        Component component = ColorUtil.legacyToComponent(message);
        // replaces `\<` with `<`
        // because for whatever reason it's being inserted before each color tag (i.e: \<yellow>)
        message = ColorUtil.serializeMiniMessage(component).replace("\\<", "<");
        return message;
    }

    public static void broadcastTitleTo(String message, final TeamSnapshot... teams) {
        message = legacyToMiniMessage(message);

        final String[] split = getSplit(message);
        final String title = split.length > 0 ? split[0] : message;
        final String subtitle = split.length > 1 ? split[1] : "";


        for (final TeamSnapshot team : teams) {
            for (final Player player : team.getCachedMembers()) {
                MessageServiceHolder.get().title(player, title, subtitle);
            }
        }
    }

    @NotNull
    private static String[] getSplit(String message) {
        try {
//            Component component = ColorUtil.deserializeMiniMessage(message);
//            message = ColorUtil.serializeToLegacy(component);
            return message.split("\n");
        } catch (NoClassDefFoundError ignored) {
            return message.split("\n");
        }
    }

    public static String toString(final List<Player> players) {
        final StringBuilder builder = new StringBuilder();
        for (final Player player : players) {
            builder.append(player.getName()).append(", ");
        }
        return builder.substring(0, Math.max(0, builder.length() - 2));
    }
}
