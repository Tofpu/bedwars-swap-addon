package io.tofpu.bedwarsswapaddon.wrapper;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class PlayerInventorySnapshot implements PlayerInventory {
    private final PlayerInventory inventory;

    private final ItemStack item;

    private final ItemStack[] contents;
    private final ItemStack[] armorContents;

    private PlayerInventorySnapshot(final PlayerInventory inventory) {
        this.inventory = inventory;
        this.contents = inventory.getContents();
        this.armorContents = inventory.getArmorContents();

        this.item = new ItemStack(inventory.getItemInHand());
    }

    public static PlayerInventorySnapshot of(final HumanEntity entity) {
        return new PlayerInventorySnapshot(entity.getInventory());
    }

    @Override
    public ItemStack[] getArmorContents() {
        return this.armorContents;
    }

    @Override
    public void setArmorContents(final ItemStack[] items) {
        throw new IllegalStateException("Cannot set armor contents of a snapshot " +
                                        "inventory");
    }

    @Override
    public ItemStack getHelmet() {
        return null;
    }

    @Override
    public void setHelmet(final ItemStack helmet) {
        throw new IllegalStateException("Cannot set helmet of a snapshot inventory");
    }

    @Override
    public ItemStack getChestplate() {
        return null;
    }

    @Override
    public void setChestplate(final ItemStack chestplate) {
        throw new IllegalStateException("Cannot set chestplate of a snapshot inventory");
    }

    @Override
    public ItemStack getLeggings() {
        return null;
    }

    @Override
    public void setLeggings(final ItemStack leggings) {
        throw new IllegalStateException("Cannot set leggings of a snapshot inventory");
    }

    @Override
    public ItemStack getBoots() {
        return null;
    }

    @Override
    public void setBoots(final ItemStack boots) {
        throw new IllegalStateException("Cannot set boots of a snapshot inventory");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getMaxStackSize() {
        return 0;
    }

    @Override
    public void setMaxStackSize(final int size) {
        throw new UnsupportedOperationException("You can't set max stack size of inventory snapshot");
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ItemStack getItem(final int index) {
        return null;
    }

    @Override
    public void setItem(final int index, final ItemStack item) {
        throw new UnsupportedOperationException("You can't set item of inventory snapshot");
    }

    @Override
    public HashMap<Integer, ItemStack> addItem(final ItemStack... items) throws IllegalArgumentException {
        return null;
    }

    @Override
    public HashMap<Integer, ItemStack> removeItem(final ItemStack... items) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ItemStack[] getContents() {
        return this.contents;
    }

    @Override
    public void setContents(final ItemStack[] items) throws IllegalArgumentException {
        throw new UnsupportedOperationException("You can't set contents of inventory snapshot");
    }

    @Override
    public boolean contains(final int materialId) {
        return false;
    }

    @Override
    public boolean contains(final Material material) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean contains(final ItemStack item) {
        return false;
    }

    @Override
    public boolean contains(final int materialId, final int amount) {
        return false;
    }

    @Override
    public boolean contains(final Material material, final int amount) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean contains(final ItemStack item, final int amount) {
        return false;
    }

    @Override
    public boolean containsAtLeast(final ItemStack item, final int amount) {
        return false;
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(final int materialId) {
        return null;
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(final Material material) throws IllegalArgumentException {
        return null;
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(final ItemStack item) {
        return null;
    }

    @Override
    public int first(final int materialId) {
        return 0;
    }

    @Override
    public int first(final Material material) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public int first(final ItemStack item) {
        return 0;
    }

    @Override
    public int firstEmpty() {
        return 0;
    }

    @Override
    public void remove(final int materialId) {

    }

    @Override
    public void remove(final Material material) throws IllegalArgumentException {

    }

    @Override
    public void remove(final ItemStack item) {

    }

    @Override
    public void clear(final int index) {

    }

    @Override
    public void clear() {

    }

    @Override
    public List<HumanEntity> getViewers() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public InventoryType getType() {
        return null;
    }

    @Override
    public ItemStack getItemInHand() {
        return this.item;
    }

    @Override
    public void setItemInHand(final ItemStack stack) {
        throw new IllegalStateException("Cannot set item in hand of a snapshot inventory");
    }

    @Override
    public int getHeldItemSlot() {
        return 0;
    }

    @Override
    public void setHeldItemSlot(final int slot) {
        throw new IllegalStateException("Cannot set held item slot of a snapshot inventory");
    }

    @Override
    public int clear(final int id, final int data) {
        return 0;
    }

    @Override
    public HumanEntity getHolder() {
        return null;
    }

    @Override
    public ListIterator<ItemStack> iterator() {
        return null;
    }

    @Override
    public ListIterator<ItemStack> iterator(final int index) {
        return null;
    }
}
