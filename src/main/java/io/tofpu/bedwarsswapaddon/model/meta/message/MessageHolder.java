package io.tofpu.bedwarsswapaddon.model.meta.message;

import io.tofpu.dynamicmessage.DynamicMessage;
import io.tofpu.dynamicmessage.holder.meta.SkipMessage;

import java.io.File;

import static io.tofpu.bedwarsswapaddon.BedwarsSwapBootstrap.ADDON_DIRECTORY;

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

    public String swapTitleAnnouncement =
            wrap("<yellow>", wrap("<obf>", "00 ")) + wrap("<green>", "SWAPPAGE ") +
            wrap("<yellow>", wrap("<obf>", "00")) + "\n" + wrap("<gold>", "New team") + ": %team%";

    public String swapMessageAnnouncement =
            wrap("<yellow>", "Your team swapped to ") + "%team%<yellow>!";

    // commands
    public String defaultCommand =
            "<yellow>This is the default command. Type " + command("/swap help") +
            " for more info!";

    public String awaitReload = "<yellow>Reloading the plugin...";
    public String reload = "<yellow>The plugin has been reloaded!";
    public String reloadError = "<red>Something went wrong while reloading the " +
                                "configuration! Check the console for more information.";

    public MessageHolder() {
        super(new File(ADDON_DIRECTORY, "messages.yml"));
    }

    private String command(final String command) {
        return "<hover:show_text:'<yellow>Click to run " +
               "%command%'><click:run_command:'%command%'><gold>%command%".replace(
                       "%command" + "%", command);
    }

    private String wrap(final String with, final String format) {
        return with + format + with.replace("<", "</");
    }
}
