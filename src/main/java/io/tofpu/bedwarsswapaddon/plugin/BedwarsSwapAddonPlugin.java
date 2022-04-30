package io.tofpu.bedwarsswapaddon.plugin;

import io.tofpu.bedwarsswapaddon.BedwarsSwapBootstrap;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class BedwarsSwapAddonPlugin extends JavaPlugin {
    private final BedwarsSwapBootstrap bootstrap = new BedwarsSwapBootstrap(this);

    protected BedwarsSwapAddonPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        bootstrap.onEnable();
    }

    @Override
    public void onDisable() {
        bootstrap.onDisable();
    }

    public BedwarsSwapBootstrap getBootstrap() {
        return bootstrap;
    }
}
