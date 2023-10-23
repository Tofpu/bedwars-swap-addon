package io.tofpu.bedwarsswapaddon.message;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageServiceHolder {
    private static MessageServiceHolder instance;
    private final MessageService service;

    public MessageServiceHolder(final MessageService service) {
        this.service = service;
    }

    public static MessageServiceHolder get() {
        if (instance == null) {
            throw new IllegalStateException("AdventureHolder is not initialized");
        }
        return instance;
    }

    public static void init(final MessageService service) {
        instance = new MessageServiceHolder(service);
    }

    public void message(final CommandSender sender, final String message) {
        service.sendMessage(sender, message);
    }

    public void title(final Player sender, final String title, final String subtitle) {
        service.sendTitle(sender, title, subtitle);
    }

    public void close() {
        service.close();
    }
}
