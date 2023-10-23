package io.tofpu.bedwarsswapaddon.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ColorUtil {
    public static Component deserializeMiniMessage(final String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static String serializeMiniMessage(final Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    public static String serializeToLegacy(final Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static TextComponent legacyToComponent(final String message) {
        return LegacyComponentSerializer.legacySection().deserialize(message);
    }

    public static String miniMessageToLegacy(String message) {
        return LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize(message));
    }
}
