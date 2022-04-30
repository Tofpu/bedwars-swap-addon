package io.tofpu.bedwarsswapaddon.model.debug;

import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import org.bukkit.plugin.Plugin;

public class LogHandler {
    private static final LogHandler INSTANCE = new LogHandler();

    private Plugin plugin;
    private boolean debug;

    public static LogHandler get() {
        return INSTANCE;
    }

    private LogHandler() {
        debug = false;
    }

    public void reload() {
        init(plugin);
    }

    public void init(final Plugin plugin) {
        this.plugin = plugin;
        this.debug = ConfigurationHandler.get()
                .getSettingsHolder()
                .isDebug();

        this.plugin.getLogger()
                .info("Debug mode has been " + (debug ? "Enabled" : "Disabled"));
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
}
