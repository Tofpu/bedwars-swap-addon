package io.tofpu.bedwarsswapaddon.model.meta.adventure;

import io.tofpu.bedwarsswapaddon.util.ColorUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class BukkitMessageService implements MessageService {
    private final BukkitAudiences bukkitAudiences;

    public BukkitMessageService(Plugin plugin) {
        this.bukkitAudiences = BukkitAudiences.create(plugin);
    }

    @Override
    public void sendMessage(CommandSender sender, String message) {
        bukkitAudiences.sender(sender).sendMessage(ColorUtil.deserializeMiniMessage(message));
    }

    @Override
    public void close() {
        bukkitAudiences.close();
    }
}
