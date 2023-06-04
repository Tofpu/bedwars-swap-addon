package io.tofpu.bedwarsswapaddon.snapshot.helper;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BukkitHelper {
    protected Location at(int x, int y, int z) {
        return new Location(null, x, y, z);
    }

    @NotNull
    protected PotionEffect potionEffect(PotionEffectType heal) {
        return new PotionEffect(heal, 1, 1);
    }

    protected List<Player> players(Player... players) {
        return Arrays.stream(players).collect(Collectors.toList());
    }
}
