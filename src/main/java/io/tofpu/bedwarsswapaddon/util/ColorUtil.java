package io.tofpu.bedwarsswapaddon.util;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ColorUtil {
    public static Component translate(final String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static TextComponent translateLegacy(final String message) {
        return BukkitComponentSerializer.legacy().deserialize(message);
    }
}
