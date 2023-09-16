package io.tofpu.bedwarsswapaddon.util;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ColorUtil {
    public static Component deserializeMiniMessage(final String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static String serializeMiniMessage(final Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    public static String serializeToLegacy(final Component component) {
        return BukkitComponentSerializer.legacy().serialize(component);
    }

    public static TextComponent legacyToComponent(final String message) {
        return BukkitComponentSerializer.legacy().deserialize(message);
    }
}
