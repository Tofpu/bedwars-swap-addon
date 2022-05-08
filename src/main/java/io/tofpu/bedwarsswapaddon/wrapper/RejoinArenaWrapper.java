package io.tofpu.bedwarsswapaddon.wrapper;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ReJoin;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.lang.reflect.Field;
import java.util.HashMap;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class RejoinArenaWrapper {
    private static final HashMap<Player, Location> PLAYER_LOCATION_MAP;

    static {
        try {
            final Field playerLocationField = Arena.class.getDeclaredField("playerLocation");

            playerLocationField.setAccessible(true);

            PLAYER_LOCATION_MAP = (HashMap<Player, Location>) playerLocationField
                    .get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Unable to access playerLocation field", e);
        }
    }

    private final Arena arena;

    public RejoinArenaWrapper(final Arena arena) {
        this.arena = arena;
    }

    public void rejoin(final Player player, final ITeam targetTeam) {
        ReJoin reJoin = ReJoin.getPlayer(player);
        if (reJoin == null) {
            return;
        }
        if (reJoin.getArena() != arena) {
            return;
        }

        if (arena == null || this.arena.getStatus() == GameState.restarting || targetTeam == null || targetTeam.isBedDestroyed()) {
            return;
        }

        if (reJoin.getTask() != null) {
            reJoin.getTask()
                    .destroy();
        }

        for (Player on : Bukkit.getOnlinePlayers()) {
            if (on.equals(player)) {
                continue;
            }
            if (!Arena.isInArena(on)) {
                BedWars.nms.spigotHidePlayer(on, player);
                BedWars.nms.spigotHidePlayer(player, on);
            }
        }

        player.closeInventory();
        arena.getPlayers()
                .add(player);
        for (Player on : arena.getPlayers()) {
            on.sendMessage(getMsg(on, Messages.COMMAND_REJOIN_PLAYER_RECONNECTED).replace("{playername}",
                            player.getName())
                    .replace("{player}", player.getDisplayName())
                    .replace("{on}", String.valueOf(arena.getPlayers()
                            .size()))
                    .replace("{max}", String.valueOf(arena.getMaxPlayers())));
        }
        for (Player on : arena.getSpectators()) {
            on.sendMessage(getMsg(on, Messages.COMMAND_REJOIN_PLAYER_RECONNECTED).replace("{playername}",
                            player.getName())
                    .replace("{player}", player.getDisplayName())
                    .replace("{on}", String.valueOf(arena.getPlayers()
                            .size()))
                    .replace("{max}", String.valueOf(arena.getMaxPlayers())));
        }
        Arena.setArenaByPlayer(player, arena);
        /* save player inventory etc */
        if (BedWars.getServerType() != ServerType.BUNGEE) {
            // no need to backup inventory because it's empty
            //new PlayerGoods(p, true, true);
            PLAYER_LOCATION_MAP.put(player, player.getLocation());
        }

        player.teleport(arena.getSpectatorLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.getInventory()
                .clear();

        //restore items before re-spawning in team
        ShopCache sc = ShopCache.getShopCache(player.getUniqueId());
        if (sc != null) {
            sc.destroy();
        }
        sc = new ShopCache(player.getUniqueId());
        for (ShopCache.CachedItem ci : reJoin.getPermanentsAndNonDowngradables()) {
            sc.getCachedItems()
                    .add(ci);
        }

        targetTeam.reJoin(player, BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_RE_SPAWN_COUNTDOWN));
        reJoin.destroy(false);

        BedWarsScoreboard.giveScoreboard(player, arena, true);
    }
}
