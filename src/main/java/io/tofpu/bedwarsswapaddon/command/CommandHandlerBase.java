package io.tofpu.bedwarsswapaddon.command;

import io.tofpu.bedwarsswapaddon.reload.ReloadHandlerBase;
import org.bukkit.plugin.Plugin;

public abstract class CommandHandlerBase {
    /**
     * This method registers the command.
     *
     * @param plugin        The plugin instance
     * @param reloadHandler The reload handler
     */
    public abstract void create(final Plugin plugin, final ReloadHandlerBase reloadHandler);
}
