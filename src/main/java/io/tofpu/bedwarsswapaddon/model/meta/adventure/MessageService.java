package io.tofpu.bedwarsswapaddon.model.meta.adventure;

import org.bukkit.command.CommandSender;

public interface MessageService {
    void sendMessage(final CommandSender player, final String message);
    void close();
}
