package io.tofpu.bedwarsswapaddon;

import io.tofpu.bedwarsswapaddon.util.ColorUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorUtilTest {
    @Test
    void deserialize_mini_message_test() {
        String input = "<red>hi</red>";
        Component expected = MiniMessage.miniMessage().deserialize(input);
        Component against = ColorUtil.deserializeMiniMessage(input);
        assertEquals(expected, against);
    }

    @Test
    void serialize_mini_message_test() {
        Component input = ColorUtil.deserializeMiniMessage("<red>hi");
        assertEquals("<red>hi", ColorUtil.serializeMiniMessage(input));
    }

    @Test
    void mini_message_to_legacy_conversion_test() {
        assertEquals("§chi", ColorUtil.miniMessageToLegacy("<red>hi</red>"));
    }

    @Test
    void deserialize_legacy_test() {
        TextComponent expected = Component.text("hi", NamedTextColor.RED);
        assertEquals(expected, ColorUtil.legacyToComponent("§chi"));
    }

    @Test
    void serialize_legacy_test() {
        TextComponent input = Component.text("hi", NamedTextColor.RED);
        String against = ColorUtil.serializeToLegacy(input);
        assertEquals("§chi", against);
    }
}
