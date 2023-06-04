package io.tofpu.bedwarsswapaddon.wrapper;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class PlayerSnapshot {
    private final Player player;
    private final Location location;
    private final Vector velocity;


    private PlayerSnapshot(final Player player) {
        this.player = player;
        this.location = player.getLocation();
        this.velocity = player.getVelocity().clone();
    }

    public static PlayerSnapshot of(Player player) {
        return new PlayerSnapshot(player);
    }

    public String getName() {
        return player.getName();
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    public Location getLocation() {
        return location;
    }

    public Player getPlayer() {
        return player;
    }

    public Vector getVelocity() {
        return this.velocity;
    }
}
