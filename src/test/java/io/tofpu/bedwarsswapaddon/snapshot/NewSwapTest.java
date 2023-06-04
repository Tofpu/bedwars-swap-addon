package io.tofpu.bedwarsswapaddon.snapshot;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import io.tofpu.bedwarsswapaddon.snapshot.helper.BedwarsHelper;
import io.tofpu.bedwarsswapaddon.snapshot.util.ArenaSpectator;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.tofpu.bedwarsswapaddon.snapshot.util.BedwarsMocker.bedWarsTeam;
import static io.tofpu.bedwarsswapaddon.snapshot.util.BedwarsMocker.mockPlayer;
import static org.mockito.Mockito.*;

public class NewSwapTest extends BedwarsHelper {

    public static final TeamEnchant TEAM_ENCHANT = new TeamEnchant() {
        @Override
        public Enchantment getEnchantment() {
            return Enchantment.LURE;
        }

        @Override
        public int getAmplifier() {
            return 1;
        }
    };

    @BeforeEach
    void setUp() {
        ArenaSpectator.clear();
    }

    @Test
    void basic_swap_test() {
        Player redPlayer = mockPlayer("red", at(5, 5, 5));
        ITeam redTeam = bedWarsTeam(TeamColor.RED, players(redPlayer), false);
        TeamSnapshot redSnapshot = snapshot(redTeam);
        LiveObject red = live(redTeam);

        Player bluePlayer = mockPlayer("blue", at(10, 10, 10));
        ITeam blueTeam = bedWarsTeam(TeamColor.BLUE, players(bluePlayer), false);
        TeamSnapshot blueSnapshot = snapshot(blueTeam);
        LiveObject blue = live(blueTeam);

        // red snapshot is applied to blue team
        blue.use(redSnapshot);
        verify(bluePlayer, times(1)).teleport(eq(at(5, 5, 5)));

        // blue snapshot is applied to red team
        red.use(blueSnapshot);
        verify(redPlayer, times(1)).teleport(eq(at(10, 10, 10)));
    }

    @Test
    void snapshot_test() {
        Player redPlayer = mockPlayer("red", at(5, 5, 5));
        ITeam redTeam = bedWarsTeam(TeamColor.RED, players(redPlayer), false);
        TeamSnapshot redSnapshot = snapshot(redTeam);

        Player bluePlayer = mockPlayer("blue", at(10, 10, 10));
        ITeam blueTeam = bedWarsTeam(TeamColor.BLUE, players(bluePlayer), false);
        LiveObject blue = live(blueTeam);

        // red snapshot is applied to blue team
        blue.use(redSnapshot);

        // verifies that blue player equals red player
        Assertions.assertEquals(redPlayer, blueTeam.getMembers().get(0));
        // verifies blue player was teleported to red player spot
        Assertions.assertEquals(redPlayer.getLocation(), bluePlayer.getLocation());
    }

    @Test
    void safe_player_teleport_test() {
        Player redPlayer = mockPlayer("red", at(5, 5, 5));
        LiveObject redTeam = live(bedWarsTeam(TeamColor.RED, players(redPlayer), at(2, 2, 2)));
        TeamSnapshot redSnapshot = snapshot(redTeam.object());

        Player bluePlayer = mockPlayer("blue", at(10, 10, 10));
        LiveObject blue = live(bedWarsTeam(TeamColor.BLUE, players(bluePlayer), at(1, 1, 1)));

        ArenaSpectator.add(bluePlayer);
        blue.use(redSnapshot); // blue -> red

        verify(bluePlayer, never()).teleport(eq(at(5, 5, 5)));
    }

    @Test
    void unsafe_player_teleport_test() {
        Player redPlayer = mockPlayer("red", at(5, 5, 5));
        LiveObject redTeam = live(bedWarsTeam(TeamColor.RED, players(redPlayer), at(2, 2, 2)));
        TeamSnapshot redSnapshot = snapshot(redTeam.object());

        Player bluePlayer = mockPlayer("blue", at(10, 10, 10));
        LiveObject blue = live(bedWarsTeam(TeamColor.BLUE, players(bluePlayer), at(1, 1, 1)));

        ArenaSpectator.add(bluePlayer);
        blue.use(redSnapshot); // blue -> red

        verify(bluePlayer, never()).teleport(any(Location.class));
    }

    @Test
    void safe_target_teleport_test() {
        Player redPlayer = mockPlayer("red", at(5, 5, 5));
        LiveObject redTeam = live(bedWarsTeam(TeamColor.RED, players(redPlayer), at(2, 2, 2)));
        TeamSnapshot redSnapshot = snapshot(redTeam.object());

        Player bluePlayer = mockPlayer("blue", at(10, 10, 10));
        LiveObject blue = live(bedWarsTeam(TeamColor.BLUE, players(bluePlayer), at(1, 1, 1)));

        blue.use(redSnapshot); // blue -> red

        verify(bluePlayer, times(1)).teleport(eq(at(5, 5, 5)));
    }

    @Test
    void unsafe_target_teleport_test() {
        Player redPlayer = mockPlayer("red", at(5, 5, 5));
        LiveObject redTeam = live(bedWarsTeam(TeamColor.RED, players(redPlayer), at(2, 2, 2)));
        TeamSnapshot redSnapshot = snapshot(redTeam.object());

        Player bluePlayer = mockPlayer("blue", at(10, 10, 10));
        LiveObject blue = live(bedWarsTeam(TeamColor.BLUE, players(bluePlayer), at(1, 1, 1)));

        ArenaSpectator.add(redPlayer);
        blue.use(redSnapshot); // blue -> red

        verify(bluePlayer, times(1)).teleport(eq(at(2, 2, 2)));
    }

    @Test
    void no_target_teleport_test() {
        Player redPlayer = mockPlayer("red", at(5, 5, 5));
        LiveObject redTeam = live(bedWarsTeam(TeamColor.RED, players(redPlayer), at(2, 2, 2)));
        TeamSnapshot redSnapshot = snapshot(redTeam.object());

        Player bluePlayer = mockPlayer("blue", at(10, 10, 10));
        LiveObject blue = live(bedWarsTeam(TeamColor.BLUE, players(bluePlayer), at(1, 1, 1)));

        ArenaSpectator.add(redPlayer);
        blue.use(redSnapshot); // blue -> red

        verify(bluePlayer, times(1)).teleport(eq(at(2, 2, 2)));
    }

    @Test
    void team_effects_enchants_test() {
        BedWarsTeam redTeam = bedWarsTeam(TeamColor.RED, false);
        redTeam.getTeamUpgradeTiers().put("test", 1);
        redTeam.getTeamEffects().add(potionEffect(PotionEffectType.HEAL));
        redTeam.getBaseEffects().add(potionEffect(PotionEffectType.HEAL));
        redTeam.getArmorsEnchantments().add(teamEnchant());
        redTeam.getBowsEnchantments().add(teamEnchant());
        redTeam.getSwordsEnchantments().add(teamEnchant());

        TeamSnapshot redSnapshot = snapshot(redTeam);

        BedWarsTeam blueTeam = bedWarsTeam(TeamColor.BLUE, players(), false);

        // red snapshot is applied to blue team
        redSnapshot.apply(blueTeam);

        Assertions.assertEquals(1, blueTeam.getTeamUpgradeTiers().get("test"));
        Assertions.assertEquals(potionEffect(PotionEffectType.HEAL), blueTeam.getTeamEffects().get(0));
        Assertions.assertEquals(potionEffect(PotionEffectType.HEAL), blueTeam.getBaseEffects().get(0));
        Assertions.assertEquals(teamEnchant(), blueTeam.getArmorsEnchantments().get(0));
        Assertions.assertEquals(teamEnchant(), blueTeam.getBowsEnchantments().get(0));
        Assertions.assertEquals(teamEnchant(), blueTeam.getSwordsEnchantments().get(0));
    }

    @Test
    void player_test() {
        Player red = mockPlayer("red", at(1, 1, 1));

        red.teleport(at(2, 2, 2));
        Assertions.assertEquals(at(2, 2, 2), red.getLocation());

        red.teleport(at(5, 5, 5));
        Assertions.assertEquals(at(5, 5, 5), red.getLocation());
    }

    @Test
    void bed_snapshot_test() {
        LiveObject redTeam = live(bedWarsTeam(TeamColor.RED, 1, false));
        TeamSnapshot redSnapshot = snapshot(redTeam.object());

        BedWarsTeam blueTeam = bedWarsTeam(TeamColor.BLUE, 1, true);
        LiveObject blue = live(blueTeam);

        Assertions.assertTrue(blueTeam.isBedDestroyed());
        blue.use(redSnapshot); // blue -> red
        Assertions.assertFalse(blueTeam.isBedDestroyed());
    }

    @NotNull
    private static TeamEnchant teamEnchant() {
        return TEAM_ENCHANT;
    }
}
