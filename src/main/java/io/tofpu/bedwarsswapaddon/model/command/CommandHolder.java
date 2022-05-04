package io.tofpu.bedwarsswapaddon.model.command;

import io.tofpu.bedwarsswapaddon.model.adventure.AdventureHolder;
import io.tofpu.bedwarsswapaddon.model.command.presenter.HelpPresenterHolder;
import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.model.message.MessageHolder;
import io.tofpu.bedwarsswapaddon.model.reload.ReloadHandlerBase;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("swap")
@CommandPermission("swap.admin")
public class CommandHolder {
    private final ReloadHandlerBase reloadHandler;

    public CommandHolder(final ReloadHandlerBase reloadHandler) {
        this.reloadHandler = reloadHandler;
    }

    @Default
    public String defaultCommand() {
        return MessageHolder.get().defaultCommand;
    }

    @Subcommand("reload")
    @Description("Reloads the configuration")
    public String reload(final Player player) {
        reloadHandler.reload()
                .whenComplete((result, error) -> {
                    if (error != null) {
                        if (error.getMessage() != null) {
                            player.sendMessage(error.getMessage());
                        }
                        AdventureHolder.get()
                                .message(player, MessageHolder.get().reloadError);

                        error.printStackTrace();
                        return;
                    }

                    AdventureHolder.get()
                            .message(player, MessageHolder.get().reload);
                });
        return MessageHolder.get().awaitReload;
    }

    @Subcommand("help")
    @Description("This help message")
    public String help() {
        return HelpPresenterHolder.get()
                .result();
    }
}
