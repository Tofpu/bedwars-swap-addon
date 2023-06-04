package io.tofpu.bedwarsswapaddon.snapshot.util;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArenaSpectator {
    private static final List<Player> SPECTATOR_LIST = new ArrayList<>();

    public static void add(final Player player) {
        SPECTATOR_LIST.add(player);
    }

    public static void remove(final Player player) {
        SPECTATOR_LIST.remove(player);
    }

    public static void clear() {
        SPECTATOR_LIST.clear();
    }

    public static List<Player> list() {
        return Collections.unmodifiableList(SPECTATOR_LIST);
    }
}
