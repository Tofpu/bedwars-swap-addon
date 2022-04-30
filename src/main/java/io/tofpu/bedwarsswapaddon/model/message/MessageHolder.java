package io.tofpu.bedwarsswapaddon.model.message;

import io.tofpu.bedwarsswapaddon.plugin.BedwarsSwapAddonPlugin;
import io.tofpu.dynamicmessage.DynamicMessage;
import io.tofpu.dynamicmessage.holder.meta.SkipMessage;

import java.io.File;

public class MessageHolder extends io.tofpu.dynamicmessage.holder.MessageHolder {
    @SkipMessage
    private static MessageHolder instance;

    public static void init() {
        instance = DynamicMessage.get()
                .create(MessageHolder.class);
    }

    public static MessageHolder get() {
        return instance;
    }

    public final String swapTitleAnnounce =
            wrap("<obf>", "00") + " " + wrap("<green>", "SWAPPAGE") + wrap("<obf>", "00") + "\n" +
                    wrap("<gold>", "New team") + ": %team%";

    public final String swapMessageAnnouncement =
            "<yellow>Your team swapped to " + "<gold>%team%<yellow>!";

    public MessageHolder() {
        super(new File(BedwarsSwapAddonPlugin.getPlugin(BedwarsSwapAddonPlugin.class)
                .getDataFolder(), "messages.yml"));
    }

    private String wrap(final String with, final String format) {
        return with + format + with.replace("<", "</");
    }
}
