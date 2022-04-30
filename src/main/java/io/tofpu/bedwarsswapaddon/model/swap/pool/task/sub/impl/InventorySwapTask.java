package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class InventorySwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final ITeam playerOneTeam = context.getPlayerOneTeam();
        final ITeam playerTwoTeam = context.getPlayerTwoTeam();

        final List<Player> teamOneMembers = playerOneTeam
                .getMembers();
        final List<Player> teamTwoMembers = playerTwoTeam
                .getMembers();

        teamOneMembers.forEach(member -> {
            if (isUnavailable(context.getArena(), member)) {
                return;
            }

            swapArmorContentsColor(member.getInventory(),
                    playerTwoTeam.getColor().bukkitColor());
        });

        teamTwoMembers.forEach(member -> {
            if (isUnavailable(context.getArena(), member)) {
                return;
            }

            swapArmorContentsColor(member.getInventory(),
                    playerOneTeam.getColor().bukkitColor());
        });
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
}