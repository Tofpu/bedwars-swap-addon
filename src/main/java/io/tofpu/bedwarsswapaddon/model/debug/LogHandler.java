package io.tofpu.bedwarsswapaddon.model.debug;

import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import org.bukkit.plugin.Plugin;

public class LogHandler {
    private static LogHandler instance;

    private Plugin plugin;
    private boolean debug;

    public static LogHandler get() {
        if (instance == null) {
            throw new IllegalStateException("LogHandler is not initialized");
        }
        return instance;
    }

    private LogHandler() {
        debug = false;
    }

    public void reload() {
        init(plugin);
    }

    public static synchronized void init(final Plugin plugin) {
        instance = new LogHandler();

        instance.setPlugin(plugin);
        instance.setDebug(ConfigurationHandler.get()
                .getSettingsHolder()
                .isDebug());

        plugin.getLogger()
                .info("Debug mode has been " + (instance.debug ? "Enabled" : "Disabled"));
    }

    public void log(final String message) {
        this.plugin.getLogger()
                .info(message);
    }

    public void debug(final String message) {
        if (debug) {
            this.plugin.getLogger()
                    .info("[DEBUG] " + message);
        }
    }

    public void setPlugin(final Plugin plugin) {
        this.plugin = plugin;
    }

    public void setDebug(final boolean debug) {
        this.debug = debug;
    }
}
