package io.tofpu.bedwarsswapaddon.swappage.object;

import io.tofpu.bedwarsswapaddon.model.meta.adventure.MessageService;
import org.bukkit.command.CommandSender;

public class TestMessageService implements MessageService {
    @Override
    public void sendMessage(CommandSender player, String message) {
        System.out.println("received: " + player.getName() + ": " + message);
        player.sendMessage(message);
    }

    @Override
    public void close() {

    }
}
