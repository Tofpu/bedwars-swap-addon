package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class InventorySwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final PlayerInventory playerOneInventory = context.getPlayerOne()
                .getInventory();
        final PlayerInventory playerTwoInventory = context.getPlayerTwo()
                .getInventory();

        swapArmorContents(playerOneInventory, playerTwoInventory);
    }

    private void swapArmorContents(final PlayerInventory playerOneInventory, final PlayerInventory playerTwoInventory) {
        final ItemStack[] firstPlayerArmorContents =
                playerOneInventory.getArmorContents();
        final ItemStack[] secondPlayerArmorContents = playerTwoInventory.getArmorContents();

        for (int i = 0; i < 4; i++) {
            final ItemStack firstPlayerArmorContent = firstPlayerArmorContents[i];
            final ItemStack secondPlayerArmorContent = secondPlayerArmorContents[i];

            final boolean isBothItemValid = isItemValid(firstPlayerArmorContent) && isItemValid(secondPlayerArmorContent);

            if (!isBothItemValid || !"LEATHER".contains(firstPlayerArmorContent.getType().name())) {
                continue;
            }

            swapItemColor(firstPlayerArmorContent, secondPlayerArmorContent);
        }
    }

    private void swapItemColor(final ItemStack firstPlayerItem, final ItemStack secondPlayerItem) {
        final LeatherArmorMeta firstPlayerArmorMeta = (LeatherArmorMeta) firstPlayerItem.getItemMeta();
        final LeatherArmorMeta secondPlayerArmorMeta = (LeatherArmorMeta) secondPlayerItem.getItemMeta();

        final Color firstPlayerArmorColor =
                Color.fromBGR(firstPlayerArmorMeta.getColor().asBGR());

        firstPlayerArmorMeta.setColor(secondPlayerArmorMeta.getColor());
        secondPlayerArmorMeta.setColor(firstPlayerArmorColor);

        firstPlayerItem.setItemMeta(firstPlayerArmorMeta);
        secondPlayerItem.setItemMeta(secondPlayerArmorMeta);
    }

    private boolean isItemValid(final ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR;
    }
}
