package io.tofpu.bedwarsswapaddon.command;

import io.tofpu.bedwarsswapaddon.LogHandler;
import io.tofpu.bedwarsswapaddon.MessageHolder;
import io.tofpu.bedwarsswapaddon.command.presenter.HelpPresenterHolder;
import io.tofpu.bedwarsswapaddon.configuration.ConfigurationHolder;
import io.tofpu.bedwarsswapaddon.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.message.MessageServiceHolder;
import io.tofpu.bedwarsswapaddon.reload.ReloadHandlerBase;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("swap")
@CommandPermission("swap.admin")
public class CommandHolder {
    private static final String RELOAD_DEBUG =
            "Reload Debug:\n" + "Previous:\n" + "  - debug-mode: %s\n" +
            "  - minimum-swap-interval: %s\n" + "  - maximum-swap-interval: %s\n" +
            "After:\n" + "  - debug-mode: %s\n" + "  - minimum-swap-interval: %s\n" +
            "  - maximum-swap-interval: %s";

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
        final ConfigurationHolder copyOfSettings = ConfigurationHandler.get()
                .getSettingsHolder()
                .copy();

        reloadHandler.reload()
                .whenComplete((result, error) -> {
                    if (error != null) {
                        if (error.getMessage() != null) {
                            player.sendMessage(error.getMessage());
                        }
                        MessageServiceHolder.get()
                                .message(player, MessageHolder.get().reloadError);

                        error.printStackTrace();
                        return;
                    }

                    final ConfigurationHolder settings = ConfigurationHandler.get()
                            .getSettingsHolder();

                    if (settings.equals(copyOfSettings)) {
                        LogHandler.get()
                                .debug("No changes were detected when the reloading " +
                                       "process was performed.");
                    } else {
                        LogHandler.get()
                                .debug(String.format(RELOAD_DEBUG,
                                        copyOfSettings.isDebug(),
                                        copyOfSettings.getMinimumInterval(),
                                        copyOfSettings.getMaximumInterval(),
                                        settings.isDebug(),
                                        settings.getMinimumInterval(),
                                        settings.getMaximumInterval()));
                    }

                    MessageServiceHolder.get()
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
