package io.tofpu.bedwarsswapaddon.model.meta.adventure;

import io.tofpu.bedwarsswapaddon.util.ColorUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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

    public void close() {
        service.close();
    }
}
