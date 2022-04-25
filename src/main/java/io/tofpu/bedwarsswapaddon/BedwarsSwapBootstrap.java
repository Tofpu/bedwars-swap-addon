package io.tofpu.bedwarsswapaddon;

import com.andrei1058.bedwars.api.BedWars;
import io.tofpu.bedwarsswapaddon.model.listener.BedwarsListener;
import io.tofpu.bedwarsswapaddon.model.swap.SwapHandlerGame;
import io.tofpu.bedwarsswapaddon.model.swap.pool.SwapPoolHandlerBase;
import io.tofpu.bedwarsswapaddon.model.swap.pool.SwapPoolHandlerGame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BedwarsSwapBootstrap {
    private final JavaPlugin javaPlugin;
    private SwapHandlerGame swapHandler;
    private SwapPoolHandlerBase swapPoolhandler;
    private BedWars bedwarsAPI;

    public BedwarsSwapBootstrap(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public void onEnable() {

        this.bedwarsAPI = Bukkit.getServicesManager()
                .getRegistration(BedWars.class)
                .getProvider();
        this.swapPoolhandler = new SwapPoolHandlerGame(javaPlugin, bedwarsAPI);
        this.swapHandler = new SwapHandlerGame(swapPoolhandler);

        this.swapPoolhandler.init();

        registerListeners();

//        Bukkit.getScheduler()
//                .runTaskTimer(javaPlugin, () -> {
//                    bedwarsAPI.getArenaUtil()
//                            .getArenas()
//                            .forEach(arena -> {
//                                if (arena.getStatus() != GameState.playing) {
//                                    return;
//                                }
//
//                                final List<Player> players = new ArrayList<>(arena.getPlayers());
//                                System.out.println("Players: " + players.size());
//                                if (players.size() < 2) {
//                                    return;
//                                }
//
//                                final List<Player> availablePlayers = new ArrayList<>(players);
//                                for (int i = 0; i < players.size() - 1; i ++) {
//                                    final Player player = players.get(i);
//                                    availablePlayers.remove(player);
//
////                                    final Player otherPlayer = players.get((i + 1) % players.size());
//                                    final Player otherPlayer = availablePlayers.get(0);
//                                    availablePlayers.remove(otherPlayer);
//
//                                    System.out.println("Player: " + player.getName());
//                                    System.out.println("OtherPlayer: " + otherPlayer.getName());
//
//                                    final ITeam firstPlayerTeam = arena.getTeam(player);
//                                    final ITeam secondPlayerTeam = arena.getTeam(otherPlayer);
//
//                                    if (firstPlayerTeam.getName().equals(secondPlayerTeam.getName())) {
//                                        continue;
//                                    }
//
//                                    // Swap locations
//                                    final Location firstPlayerLocation =
//                                            player.getLocation().clone();
//
//                                    player.teleport(otherPlayer.getLocation());
//                                    otherPlayer.teleport(firstPlayerLocation);
//
//                                    // Swap items
//                                    final ItemStack[] firstPlayerInventory =
//                                            Arrays.copyOf(player.getInventory().getContents(), player.getInventory().getSize());
//                                    player.getInventory().setContents(otherPlayer.getInventory().getContents());
//
//                                    otherPlayer.getInventory().setContents(firstPlayerInventory);
//
//                                    // Swap teams
//                                    firstPlayerTeam.getMembers().remove(player);
//                                    secondPlayerTeam.getMembers().remove(otherPlayer);
//
//                                    firstPlayerTeam.getMembers().add(otherPlayer);
//                                    secondPlayerTeam.getMembers().add(player);
//
//                                    // Swap tiers
//                                    final ConcurrentHashMap<String, Integer> firstTiers = new ConcurrentHashMap<>(firstPlayerTeam.getTeamUpgradeTiers());
//                                    final ConcurrentHashMap<String, Integer> secondTiers =
//                                            new ConcurrentHashMap<>(secondPlayerTeam.getTeamUpgradeTiers());
//
//                                    firstPlayerTeam.getTeamUpgradeTiers().clear();
//                                    secondPlayerTeam.getTeamUpgradeTiers().clear();
//
//                                    firstPlayerTeam.getTeamUpgradeTiers().putAll(secondTiers);
//                                    secondPlayerTeam.getTeamUpgradeTiers().putAll(firstTiers);
//                                }
//                            });
//                }, 60, 60);
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BedwarsListener(swapHandler), javaPlugin);
    }

    public void onDisable() {

    }
}
