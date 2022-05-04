package io.tofpu.bedwarsswapaddon.model.adventure;

import io.tofpu.bedwarsswapaddon.util.ColorUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AdventureHolder {
    private static AdventureHolder instance;

    public static AdventureHolder get() {
        if (instance == null) {
            throw new IllegalStateException("AdventureHolder is not initialized");
        }
        return instance;
    }

    public static void init(final Plugin plugin) {
        instance = new AdventureHolder(BukkitAudiences.create(plugin));
    }

    private final BukkitAudiences adventure;

    public AdventureHolder(final BukkitAudiences adventure) {
        this.adventure = adventure;
    }

    public void message(final CommandSender player, final String message) {
        message(player, ColorUtil.translate(message));
    }

    public void message(final Player player, final String message) {
        message(player, ColorUtil.translate(message));
    }

    public void message(final CommandSender sender, final Component message) {
        this.adventure.sender(sender).sendMessage(message);
    }

    public void close() {
        if (this.adventure != null) {
            this.adventure.close();
        }
    }
}
