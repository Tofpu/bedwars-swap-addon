package io.tofpu.bedwarsswapaddon.util;

import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.cryptomorin.xseries.messages.Titles;
import io.tofpu.bedwarsswapaddon.model.meta.adventure.AdventureHolder;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeamUtil {
    public static void broadcastMessageTo(final String message, final TeamSnapshot... teams) {
        for (final TeamSnapshot team : teams) {
            for (final Player player : team.getCachedMembers()) {
                try {
                    AdventureHolder.get()
                            .message(player, message);
                } catch (IllegalStateException exception) {
                    player.sendMessage(message);
                }
            }
        }
    }

    public static void broadcastTitleTo(final String message, final TeamSnapshot... teams) {
        final String[] split = getSplit(message);
        final String title = split.length > 0 ? split[0] : message;
        final String subtitle = split.length > 1 ? split[1] : "";

        for (final TeamSnapshot team : teams) {
            for (final Player player : team.getCachedMembers()) {
                sendTitle(title, subtitle, player);
            }
        }
    }

    private static void sendTitle(final String title, final String subtitle, final Player player) {
        try {
            Titles.sendTitle(player, title, subtitle);
        } catch (ExceptionInInitializerError | NoClassDefFoundError ignored) {
            LogHandler.get().debug("Failed to sent title to " + player.getName() + ": " + title + " - " + subtitle);
        }
    }

    @NotNull
    private static String[] getSplit(final String message) {
        try {
            return BukkitComponentSerializer.legacy()
                    .serialize(ColorUtil.translate(message))
                    .split("\n");
        } catch (NoClassDefFoundError ignored) {
            return message.split("\n");
        }
    }

    public static String teamOf(final TeamColor color) {
        try {
            final TextComponent component = (TextComponent) ColorUtil.translateLegacy(
                    "§" + color.chat().getChar() + color.name()
            ).applyFallbackStyle(TextDecoration.ITALIC.withState(false));

            return MiniMessage.miniMessage().serialize(component);
        } catch (ExceptionInInitializerError | NoClassDefFoundError exception) {
            return color.chat().getChar() + color.name();
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
