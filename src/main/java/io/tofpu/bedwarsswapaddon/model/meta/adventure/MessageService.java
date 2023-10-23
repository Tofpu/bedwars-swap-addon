package io.tofpu.bedwarsswapaddon.model.meta.adventure;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface MessageService {
    void sendMessage(final CommandSender player, final String message);

    void sendTitle(final Player player, final String title, final String subtitle);

    void close();
}
