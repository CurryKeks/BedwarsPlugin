package me.currycookie.builders;

import me.currycookie.BedwarsPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemBuilder {
    private ItemStack item;
    private ItemMeta meta;
    private HashMap<String, PersistentDataType> NBTTags;
    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        updateItemMeta(this.item.getItemMeta());
    }

    public ItemBuilder changeAmount(int newAmount) {
        this.item.setAmount(newAmount);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public Map<Enchantment, Integer> getAllEnchantments() {
        return this.item.getEnchantments();
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        updateItemMeta(this.meta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.meta.setLore(Arrays.asList(lore));
        updateItemMeta(this.meta);
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        this.meta.setLore(lore);
        updateItemMeta(this.meta);
        return this;
    }

    public List<String> getLore() {
        return this.meta.getLore();
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.meta.setDisplayName(displayName);
        updateItemMeta(this.meta);
        return this;
    }

    public String getDispayName() {
        return this.meta.getDisplayName();
    }

    /**
     *
     * @param type DataType
     * @param NBTTag Tag that gets searched
     * @param value Value that gets returned on search
     * @return ItemBuilder
     */
    public ItemBuilder addNBTTag(PersistentDataType type, String NBTTag, String value) {
        this.NBTTags.put(NBTTag, type);
        this.meta.getPersistentDataContainer().set(new NamespacedKey(BedwarsPlugin.getInstance(), NBTTag), type, value);
        updateItemMeta(this.meta);
        return this;
    }

    public ItemBuilder removeNBTTag(String NBTTag) {
        this.meta.getPersistentDataContainer().remove(new NamespacedKey(BedwarsPlugin.getInstance(), NBTTag));
        this.NBTTags.remove(NBTTag);
        updateItemMeta(this.meta);
        return this;
    }

    public boolean hasNBTTag(String NBTTag) {
        return this.meta.getPersistentDataContainer().has(new NamespacedKey(BedwarsPlugin.getInstance(), NBTTag));
    }

    public static boolean hasNBTTag(ItemStack item, String NBTTag) {
        return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(BedwarsPlugin.getInstance(), NBTTag));
    }

    public String getNBTTag(String NBTTag) {
        return (String) this.meta.getPersistentDataContainer().get(new NamespacedKey(BedwarsPlugin.getInstance(), NBTTag), this.NBTTags.get(NBTTag));
    }

    public static String getNBTTag(ItemStack item, String NBTTag) {
        return (String) item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(BedwarsPlugin.getInstance(), NBTTag), PersistentDataType.STRING);
    }

    public ItemStack build() {
        updateItemMeta(this.meta);
        return this.item;
    }

    private void updateItemMeta(ItemMeta newMeta) {
        this.item.setItemMeta(newMeta);
        this.meta = newMeta;
    }
}
