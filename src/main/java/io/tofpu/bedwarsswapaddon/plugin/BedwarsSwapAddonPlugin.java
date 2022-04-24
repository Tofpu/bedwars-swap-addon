package io.tofpu.bedwarsswapaddon.plugin;

import io.tofpu.bedwarsswapaddon.BedwarsSwapBootstrap;
import org.bukkit.plugin.java.JavaPlugin;

public class BedwarsSwapAddonPlugin extends JavaPlugin {
    private final BedwarsSwapBootstrap bootstrap = new BedwarsSwapBootstrap(this);

    @Override
    public void onEnable() {
        bootstrap.onEnable();
    }

    @Override
    public void onDisable() {
        bootstrap.onDisable();
    }
}
