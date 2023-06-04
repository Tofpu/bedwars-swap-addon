package io.tofpu.bedwarsswapaddon.model.meta.log;

import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogHandler {
    private static LogHandler instance;

    private Plugin plugin;
    private ExecutorService executorService;
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

    public void setPlugin(final Plugin plugin) {
        this.plugin = plugin;
    }

    public void setDebug(final boolean debug) {
        this.debug = debug;
        log("Debug mode has been " + (debug ? "enabled" : "disabled") + "!");
    }
}
