package io.tofpu.bedwarsswapaddon.configuration.handler;

import io.tofpu.bedwarsswapaddon.LogHandler;
import io.tofpu.bedwarsswapaddon.configuration.ConfigurationHolder;
import org.bukkit.plugin.Plugin;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static io.tofpu.bedwarsswapaddon.BedwarsSwapBootstrap.ADDON_DIRECTORY;

public class ConfigurationHandler {
    private static ConfigurationHandler INSTANCE = new ConfigurationHandler();

    private Plugin plugin;
    private ConfigurationHolder holder;
    private ConfigurationLoader<?> loader;
    private ConfigurationNode node;

    private ConfigurationHandler() {
    }

    public static ConfigurationHandler get() {
        return INSTANCE;
    }

    public CompletableFuture<Void> load(final Plugin plugin) {
        this.plugin = plugin;

        final File file = new File(ADDON_DIRECTORY, "settings.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create configuration file", e);
            }
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
