package io.tofpu.bedwarsswapaddon.model.command;

import io.tofpu.bedwarsswapaddon.model.reload.ReloadHandlerBase;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class CommandHandler extends CommandHandlerBase {
    private BukkitCommandHandler bukkitHandler;

    public static void init(final Plugin plugin, final ReloadHandlerBase reloadHandler) {
        new CommandHandler().create(plugin, reloadHandler);
    }

    @Override
    public void create(final Plugin plugin, final ReloadHandlerBase reloadHandler) {
        this.bukkitHandler = BukkitCommandHandler.create(plugin);

        this.bukkitHandler.registerResponseHandler(String.class,
                (response, actor, command) -> {
            if (response.isEmpty()) {
                return;
            }
            actor.reply(response);
        });

        this.bukkitHandler.register(new CommandHolder(reloadHandler));
    }
}
