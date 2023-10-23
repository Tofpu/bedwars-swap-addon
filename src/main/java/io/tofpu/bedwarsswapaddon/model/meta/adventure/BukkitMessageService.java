package io.tofpu.bedwarsswapaddon.model.meta.adventure;

import com.cryptomorin.xseries.messages.Titles;
import io.tofpu.bedwarsswapaddon.model.meta.log.LogHandler;
import io.tofpu.bedwarsswapaddon.util.ColorUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BukkitMessageService implements MessageService {
    private final BukkitAudiences bukkitAudiences;

    public BukkitMessageService(Plugin plugin) {
        this.bukkitAudiences = BukkitAudiences.create(plugin);
    }

    @Override
    public void sendMessage(CommandSender sender, String message) {
        try {
            Component component = ColorUtil.deserializeMiniMessage(message);
            bukkitAudiences.sender(sender).sendMessage(component);
        } catch (Exception e) {
            LogHandler.get().warn("Failed to send a message using audiences. Proceeding to try out our fallback approach.", e);

            message = ColorUtil.miniMessageToLegacy(message);
            fallbackSendMessage(sender, message);
        }
    }

    private void fallbackSendMessage(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    @Override
    public void sendTitle(Player sender, String title, String subtitle) {
        try {
            Component titleComp = ColorUtil.deserializeMiniMessage(title);
            Component subtitleComp = ColorUtil.deserializeMiniMessage(subtitle);
            bukkitAudiences.sender(sender).showTitle(Title.title(titleComp, subtitleComp));
        } catch (Exception e) {
            LogHandler.get().warn("Failed to send a title using audiences. Proceeding to try out our fallback approach.", e);

            title = ColorUtil.miniMessageToLegacy(title);
            subtitle = ColorUtil.miniMessageToLegacy(subtitle);
            fallbackSendTitle(sender, title, subtitle);
        }
    }

    private void fallbackSendTitle(final Player player, final String title, final String subtitle) {
        try {
            Titles.sendTitle(player, title, subtitle);
        } catch (ExceptionInInitializerError | NoClassDefFoundError ignored) {
            LogHandler.get().debug("Failed to sent title to " + player.getName() + ": " + title + " - " + subtitle);
        }
    }

    @Override
    public void close() {
        bukkitAudiences.close();
    }
}
