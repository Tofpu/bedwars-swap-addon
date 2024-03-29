package io.tofpu.bedwarsswapaddon;

import com.andrei1058.bedwars.api.BedWars;
import io.tofpu.bedwarsswapaddon.command.CommandHandler;
import io.tofpu.bedwarsswapaddon.command.presenter.HelpPresenterHolder;
import io.tofpu.bedwarsswapaddon.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.listener.BedwarsListener;
import io.tofpu.bedwarsswapaddon.message.BukkitMessageService;
import io.tofpu.bedwarsswapaddon.message.MessageServiceHolder;
import io.tofpu.bedwarsswapaddon.reload.MainReloadHandler;
import io.tofpu.bedwarsswapaddon.reload.ReloadHandlerBase;
import io.tofpu.bedwarsswapaddon.swap.game.SwapHandlerGame;
import io.tofpu.bedwarsswapaddon.swap.game.pool.SwapPoolHandlerBase;
import io.tofpu.bedwarsswapaddon.swap.game.pool.SwapPoolHandlerGame;
import io.tofpu.bedwarsswapaddon.swap.game.rejoin.MainRejoinProvider;
import io.tofpu.bedwarsswapaddon.swap.game.rejoin.RejoinProviderBase;
import io.tofpu.bedwarsswapaddon.util.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BedwarsSwapBootstrap {
    public static final File ADDON_DIRECTORY = new File(
            "plugins/BedWars1058/Addons/Swap");
    private static final int METRIC_ID = 15454;

    private final JavaPlugin javaPlugin;
    private SwapHandlerGame swapHandler;
    private SwapPoolHandlerBase<?> swapPoolhandler;
    private RejoinProviderBase rejoinProvider;
    private BedWars bedwarsAPI;
    private ReloadHandlerBase reloadHandler;
    private boolean unitTest = false;

    public BedwarsSwapBootstrap(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public void onEnable() {
        if (!ADDON_DIRECTORY.exists()) {
            ADDON_DIRECTORY.mkdirs();
        }

        LogHandler.init(javaPlugin);

        if (!unitTest) {
            LogHandler.get().log("Hooking into the Bedwars API...");
            this.bedwarsAPI = Bukkit.getServicesManager()
                    .getRegistration(BedWars.class)
                    .getProvider();
        }

        this.rejoinProvider = new MainRejoinProvider();
        this.swapPoolhandler = new SwapPoolHandlerGame(javaPlugin, bedwarsAPI, rejoinProvider);
        this.swapHandler = new SwapHandlerGame(swapPoolhandler, rejoinProvider);
        this.reloadHandler = new MainReloadHandler(swapPoolhandler);

        LogHandler.get().log("Loading the configuration...");
        ConfigurationHandler.get().load(javaPlugin).whenComplete((configuration, throwable) -> {
            if (throwable != null) {
                LogHandler.get().log("Failed to load the configuration: " + throwable.getMessage());
            } else {
                LogHandler.get().log("The configuration has been loaded.");
            }
            LogHandler.get().load();
            this.swapPoolhandler.init();
        });

        // TODO: this is temporally until dependencies are added to DynamicMessage
        if (!unitTest) {
            LogHandler.get().log("Initializing the messages...");
            MessageHolder.init();
        }

        LogHandler.get().log("Hooking into adventure...");
        MessageServiceHolder.init(new BukkitMessageService(javaPlugin));

        LogHandler.get().log("Initializing the commands...");
        CommandHandler.init(javaPlugin, reloadHandler);

        LogHandler.get().log("Generating the help message...");
        HelpPresenterHolder.generatePresenter(javaPlugin.getDescription());

        LogHandler.get().log("Checking for an update...");
        UpdateChecker.init(javaPlugin, 100619)
                .requestUpdateCheck()
                .whenComplete((updateResult, throwable) -> {
                    if (throwable != null) {
                        LogHandler.get().log("Couldn't check for an update...");
                        return;
                    }

                    if (updateResult.requiresUpdate()) {
                        LogHandler.get().log("You're using an outdated version of BedwarsSwapAddon!");
                        LogHandler.get().log("You can download the latest version at https://www.spigotmc.org/resources/.102551/");
                    } else {
                        LogHandler.get().log("You're using the latest version!");
                    }
                });

        registerBstats();
        registerListeners();
    }

    private void registerBstats() {
        new Metrics(javaPlugin, METRIC_ID);
    }

    private void registerListeners() {
        LogHandler.get().log("Initializing the listeners...");
        Bukkit.getPluginManager().registerEvents(new BedwarsListener(swapHandler), javaPlugin);
    }

    public void onDisable() {
        LogHandler.get().log("Unhooking from adventure...");
        try {
            MessageServiceHolder.get().close();
        } catch (Exception e) {
            // ignore
        }
        LogHandler.get().log("Goodbye!");
    }

    public void setUnitTest(final boolean unitTest) {
        this.unitTest = unitTest;
    }
}
