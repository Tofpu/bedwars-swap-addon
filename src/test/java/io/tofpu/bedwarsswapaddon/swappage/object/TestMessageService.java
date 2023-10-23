package io.tofpu.bedwarsswapaddon.swappage.object;

import io.tofpu.bedwarsswapaddon.message.MessageService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestMessageService implements MessageService {
    @Override
    public void sendMessage(CommandSender player, String message) {
        System.out.println("received: " + player.getName() + ": " + message);
        player.sendMessage(message);
    }

    @Override
    public void sendTitle(Player player, String title, String subtitle) {
        System.out.println("title received: " + player.getName() + ": [" + title + ":" + subtitle + "]");
        player.sendTitle(title, subtitle);
    }

    @Override
    public void close() {

    }
}
