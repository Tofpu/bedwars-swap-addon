package io.tofpu.bedwarsswapaddon;

import io.tofpu.bedwarsswapaddon.configuration.handler.ConfigurationHandler;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class LogHandler {
    private static LogHandler instance;

    private Plugin plugin;
    private final ExecutorService executorService;
    private boolean debug;

    private LogHandler() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.debug = false;
    }

    public static LogHandler get() {
        if (instance == null) {
            throw new IllegalStateException("LogHandler is not initialized");
        }
        return instance;
    }

    public static synchronized void init(final Plugin plugin) {
        instance = new LogHandler();
        instance.setPlugin(plugin);
    }

    public void reload() {
        init(plugin);
    }

    public void load() {
        instance.setDebug(ConfigurationHandler.get()
                .getSettingsHolder()
                .isDebug());
    }

    public void log(final String message) {
        this.executorService.submit(() -> this.plugin.getLogger()
                .info(message));
    }

    public void debug(final String message) {
        if (!debug) {
            return;
        }

        log("[DEBUG] " + message);
    }

    public void warn(final String message, Exception exception) {
        this.executorService.submit(() -> this.plugin.getLogger()
                .log(Level.WARNING, message, exception));
    }

    public void setPlugin(final Plugin plugin) {
        this.plugin = plugin;
    }

    public void setDebug(final boolean debug) {
        this.debug = debug;
        log("Debug mode has been " + (debug ? "enabled" : "disabled") + "!");
    }
}
