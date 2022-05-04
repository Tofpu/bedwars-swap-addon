package io.tofpu.bedwarsswapaddon.wrapper;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class PlayerSnapshot {
    private final Player player;
    private final Location location;

    private final PlayerInventorySnapshot inventorySnapshot;

    public static PlayerSnapshot of(Player player) {
        return new PlayerSnapshot(player);
    }

    private PlayerSnapshot(final Player player) {
        this.player = player;
        this.location = player.getLocation();

        this.inventorySnapshot = PlayerInventorySnapshot.of(player);
    }

    public String getName() {
        return player.getName();
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    public Location getLocation() {
        return location;
    }

    public Player getPlayer() {
        return player;
    }
}
