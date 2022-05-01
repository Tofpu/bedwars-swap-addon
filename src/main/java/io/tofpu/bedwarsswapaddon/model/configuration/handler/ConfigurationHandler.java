package io.tofpu.bedwarsswapaddon.model.configuration.handler;

import io.tofpu.bedwarsswapaddon.model.configuration.ConfigurationHolder;
import io.tofpu.bedwarsswapaddon.model.debug.LogHandler;
import org.bukkit.plugin.Plugin;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ConfigurationHandler {
    private static ConfigurationHandler INSTANCE = new ConfigurationHandler();

    private Plugin plugin;
    private ConfigurationHolder holder;
    private ConfigurationLoader<?> loader;
    private ConfigurationNode node;

    private ConfigurationHandler() {}

    public static ConfigurationHandler get() {
        return INSTANCE;
    }

    public CompletableFuture<Void> load(final Plugin plugin) {
        this.plugin = plugin;

        final File file = new File(plugin.getDataFolder(), "settings.yml");
        if (!file.exists()) {
            plugin.saveResource("settings.yml", false);
        }

        this.loader = YamlConfigurationLoader.builder()
                .defaultOptions(options -> options.shouldCopyDefaults(true))
                .nodeStyle(NodeStyle.BLOCK)
                .file(file)
                .build();

        return CompletableFuture.runAsync(() -> {
            load();
            establishHolder();
            save();
        });
    }

    public CompletableFuture<Void> reload() {
        return load(plugin);
    }

    public ConfigurationHolder getSettingsHolder() {
        return holder;
    }

    private void load() {
        try {
            this.node = loader.load();
        } catch (ConfigurateException e) {
            throw new IllegalStateException("Failed to load configuration file", e);
        }
    }

    private void establishHolder() {
        try {
            this.holder = node.get(ConfigurationHolder.class);
        } catch (ConfigurateException e) {
            throw new IllegalStateException("Failed to deserialize configuration file", e);
        }
    }

    private void save() {
        if (node == null) {
            LogHandler.get().log("Configuration node is null");
            return;
        }
        try {
            loader.save(node);
        } catch (ConfigurateException e) {
            throw new IllegalStateException("Failed to save configuration file", e);
        }
    }
}
