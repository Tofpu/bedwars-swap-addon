package io.tofpu.bedwarsswapaddon.model.command;

import io.tofpu.bedwarsswapaddon.model.reload.ReloadHandlerBase;
import org.bukkit.plugin.Plugin;

public abstract class CommandHandlerBase {
    /**
     * This method registers the command.
     *
     * @param plugin The plugin instance
     * @param reloadHandler The reload handler
     */
    public abstract void create(final Plugin plugin, final ReloadHandlerBase reloadHandler);
}
