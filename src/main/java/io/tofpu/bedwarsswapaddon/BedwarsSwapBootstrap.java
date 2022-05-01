package io.tofpu.bedwarsswapaddon;

import com.andrei1058.bedwars.api.BedWars;
import io.tofpu.bedwarsswapaddon.model.adventure.AdventureHolder;
import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.model.debug.LogHandler;
import io.tofpu.bedwarsswapaddon.model.listener.BedwarsListener;
import io.tofpu.bedwarsswapaddon.model.message.MessageHolder;
import io.tofpu.bedwarsswapaddon.model.swap.SwapHandlerGame;
import io.tofpu.bedwarsswapaddon.model.swap.pool.SwapPoolHandlerBase;
import io.tofpu.bedwarsswapaddon.model.swap.pool.SwapPoolHandlerGame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BedwarsSwapBootstrap {
    public static final File ADDON_DIRECTORY = new File(
            "plugins/Bedwars1058/Addons" + "/Swap");

    private final JavaPlugin javaPlugin;
    private SwapHandlerGame swapHandler;
    private SwapPoolHandlerBase swapPoolhandler;
    private BedWars bedwarsAPI;
    private boolean unitTest = false;

    public BedwarsSwapBootstrap(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public void onEnable() {
        if (!ADDON_DIRECTORY.exists()) {
            ADDON_DIRECTORY.mkdirs();
        }

        if (!unitTest) {
            this.bedwarsAPI = Bukkit.getServicesManager()
                    .getRegistration(BedWars.class)
                    .getProvider();
        }
        this.swapPoolhandler = new SwapPoolHandlerGame(javaPlugin, bedwarsAPI);
        this.swapHandler = new SwapHandlerGame(swapPoolhandler);

        ConfigurationHandler.get().load(javaPlugin).whenComplete((configuration, throwable) -> {
            LogHandler.init(javaPlugin);
            this.swapPoolhandler.init();
        });

        // TODO: this is temporally until dependencies are added to DynamicMessage
        if (!unitTest) {
            MessageHolder.init();
        }
        AdventureHolder.init(javaPlugin);

        registerListeners();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BedwarsListener(swapHandler), javaPlugin);
    }

    public void onDisable() {
        try {
            AdventureHolder.get().close();
        } catch (Exception e) {
            // ignore
        }
    }

    public void setUnitTest(final boolean unitTest) {
        this.unitTest = unitTest;
    }
}
