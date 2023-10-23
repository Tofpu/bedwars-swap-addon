package io.tofpu.bedwarsswapaddon.swap.snapshot.team;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import io.tofpu.bedwarsswapaddon.swap.snapshot.Snapshot;
import io.tofpu.bedwarsswapaddon.swap.snapshot.player.PlayerSnapshot;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class TeamSnapshot implements Snapshot {
    private final ITeam team;

    private final TeamMemberSnapshot member;
    private final TeamUpgradeSnapshot upgrade;
    private final TeamEffectSnapshot effect;
    private final boolean isDestroyed;

    public TeamSnapshot(final ITeam team) {
        this.team = team;

        this.member = new TeamMemberSnapshot(team);
        this.upgrade = new TeamUpgradeSnapshot(team);
        this.effect = new TeamEffectSnapshot(team);

        this.isDestroyed = team.isBedDestroyed();
    }

//    private static void updateScoreboard(ITeam team) {
//        for (Player player : team.getMembers()) {
////            final BedWarsScoreboard scoreboard = BedWarsScoreboard.getScoreboards()
////                    .get(player.getUniqueId());
//
//            ISidebar sidebar = SidebarService.getInstance().getSidebar(player);
//
//            if (sidebar == null) {
//                continue;
//            }
//
////            scoreboard.handlePlayerList();
//            sidebar.giveUpdateTabFormat(player, true);
////            SidebarService.getInstance().giveSidebar();
//        }
////        SidebarService.getInstance().refreshTabList();
//    }

    public String getName() {
        return getLive().getName();
    }

    public TeamColor getColor() {
        return getLive().getColor();
    }

    public boolean isBedDestroyed() {
        return isDestroyed;
    }

    public ITeam getLive() {
        return team;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public void to(final TeamSnapshot read, final ITeam write) {
        write.setBedDestroyed(this.isBedDestroyed());

        upgrade.to(read, write);
        effect.to(read, write);
        member.to(read, write);

        updateCloth(write);
//        updateScoreboard(write);
    }

    private void updateCloth(ITeam team) {
        for (Player player : team.getMembersCache()) {
            if (isUnavailable(team.getArena(), player)) {
                continue;
            }

            swapArmorContentsColor(player.getInventory(), team.getColor().bukkitColor());
        }
    }

    private void swapArmorContentsColor(final PlayerInventory playerInventory,
                                        final Color color) {
        final ItemStack[] armorContents = playerInventory.getArmorContents();

        for (int i = 0; i < 4; i++) {
            final ItemStack armorContent = armorContents[i];

            if (!isItemValid(armorContent) || !armorContent.getType().name()
                    .contains("LEATHER")) {
                continue;
            }

            final LeatherArmorMeta armorMeta = (LeatherArmorMeta) armorContent.getItemMeta();
            armorMeta.setColor(color);
            armorContent.setItemMeta(armorMeta);
        }
    }

    private boolean isUnavailable(final IArena arena, final Player player) {
        return arena.isSpectator(player) || arena.isReSpawning(player);
    }

    private boolean isItemValid(final ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR;
    }

    public List<Player> getCachedMembers() {
        return this.member.getCachedMembers();
    }

    public List<PlayerSnapshot> getSnapshotMembers() {
        return this.member.getSnapshotMembers();
    }

    public List<Player> getMemberPlayers() {
        return this.member.getMemberPlayers();
    }
}
