package io.tofpu.bedwarsswapaddon.model.message;

import io.tofpu.bedwarsswapaddon.plugin.BedwarsSwapAddonPlugin;
import io.tofpu.dynamicmessage.DynamicMessage;

import java.io.File;

public class MessageHolder extends io.tofpu.dynamicmessage.holder.MessageHolder {
    private static MessageHolder instance;

    public static void init() {
        instance = DynamicMessage.get()
                .create(MessageHolder.class);
    }

    public static MessageHolder get() {
        return instance;
    }

    public final String swapTitleAnnounce =
            "&k00 &3SWAPPAGE &k00\n<gold>New " + "team:</gold>" + " %team%";
    public final String swapMessageAnnouncement =
            "<yellow>Your team swapped to " + "<gold>{team}<yellow>!";

    protected MessageHolder() {
        super(new File(BedwarsSwapAddonPlugin.getPlugin(BedwarsSwapAddonPlugin.class)
                .getDataFolder(), "messages.yml"));
    }
}
