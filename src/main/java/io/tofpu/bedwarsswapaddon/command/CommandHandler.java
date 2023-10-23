package io.tofpu.bedwarsswapaddon.command;

import io.tofpu.bedwarsswapaddon.message.MessageServiceHolder;
import io.tofpu.bedwarsswapaddon.reload.ReloadHandlerBase;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.bukkit.core.BukkitActor;

public class CommandHandler extends CommandHandlerBase {
    private BukkitCommandHandler bukkitHandler;

    public static void init(final Plugin plugin, final ReloadHandlerBase reloadHandler) {
        new CommandHandler().create(plugin, reloadHandler);
    }

    @Override
    public void create(final Plugin plugin, final ReloadHandlerBase reloadHandler) {
        this.bukkitHandler = BukkitCommandHandler.create(plugin);

        this.bukkitHandler.registerResponseHandler(String.class, (response, actor, command) -> {
            if (response.isEmpty()) {
                return;
            }

            final BukkitActor bukkitActor = (BukkitActor) actor;

            MessageServiceHolder.get()
                    .message(bukkitActor.getSender(), response);
        });

        this.bukkitHandler.register(new CommandHolder(reloadHandler));
    }
}
