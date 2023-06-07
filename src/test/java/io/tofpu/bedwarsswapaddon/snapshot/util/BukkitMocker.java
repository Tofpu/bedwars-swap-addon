package io.tofpu.bedwarsswapaddon.snapshot.util;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class BukkitMocker {
    public static Player mockBasicPlayer(String name) {
        final Player player = mock(Player.class);
        doReturn(true).when(player).isOnline();

        when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        when(player.getName()).thenReturn(name);

        return player;
    }

    public static Server mockServer() {
        Server mock = mock(ServerTest.class);
        doReturn(Logger.getGlobal()).when(mock).getLogger();
        return mock;
    }

    public static Plugin mockPluginWithLogger() {
        Plugin mock = mock(Plugin.class);

        Server server = mockServer();
        doReturn(server.getLogger()).when(mock).getLogger();

        return mock;
    }

    // Fixes out of bounds exception on Bedwars
    interface ServerTest extends Server {
    }
}
