package io.tofpu.bedwarsswapaddon;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BedwarsSwapBootstrap {
    private final JavaPlugin javaPlugin;
    private BedWars bedwarsAPI;

    public BedwarsSwapBootstrap(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public void onEnable() {
        bedwarsAPI = Bukkit.getServicesManager()
                .getRegistration(BedWars.class)
                .getProvider();

    }

    public void onDisable() {

    }
}
