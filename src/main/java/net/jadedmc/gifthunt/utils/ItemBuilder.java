package net.jadedmc.gifthunt.utils;

import com.cryptomorin.xseries.XMaterial;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Makes ItemStacks easier to create.
 */
@SuppressWarnings("unused")
public class ItemBuilder {
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    /**
     * Creates an item builder using a base ItemStack.
     * @param itemStack Starting ItemStack.
     */
    public ItemBuilder(final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Creates an item builder using a specific material and item count.
     * @param material Material to use.
     * @param amount Number of items.
     */
    public ItemBuilder(Material material, final int amount) {
        this(new ItemStack(material, amount));
    }

    /**
     * Creates an item builder using a base material.
     * @param material Material to use.
     */
    public ItemBuilder(final Material material) {
        this(material, 1);
    }

    /**
     * Creates an item builder using a specific XMaterial and item count.
     * @param xMaterial XMaterial to use.
     * @param amount Number of items.
     */
    public ItemBuilder(final XMaterial xMaterial, final int amount) {
        this.itemStack = xMaterial.parseItem();
        itemStack.setAmount(amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Creates an item builder using a specific XMaterial.
     * @param xMaterial XMaterial to use.
     */
    public ItemBuilder(final XMaterial xMaterial) {
        this(xMaterial, 1);
    }

    /**
     * Adds an enchantment to the item.
     * @param enchantment Enchantment to add.
     * @param level Level of the enchantment.
     * @param ignore Whether the enchantment should ignore the enchantment cap.
     * @return ItemBuilder.
     */
    public ItemBuilder addEnchantment(final Enchantment enchantment, final int level, final boolean ignore) {
        itemMeta.addEnchant(enchantment, level, ignore);
        return this;
    }

    /**
     * Adds an enchantment to the item.
     * @param enchantment Enchantment to add.
     * @param level Level of the enchantment.
     * @return ItemBuilder.
     */
    public ItemBuilder addEnchantment(final Enchantment enchantment, int level) {
        this.addEnchantment(enchantment, level, true);
        return this;
    }

    /**
     * Add an ItemFlag to the item.
     * @param itemFlag ItemFlag to add.
     * @return ItemBuilder.
     */
    public ItemBuilder addFlag(final ItemFlag itemFlag) {
        this.itemMeta.addItemFlags(itemFlag);
        return this;
    }

    /**
     * Adds a String to the item lore.
     * Supports MiniMessage formatting, though you probably shouldn't use it.
     * @param line Line of lore to add to the item.
     * @return ItemBuilder.
     */
    public ItemBuilder addLore(final String line) {
        List<String> lore = this.itemMeta.getLore();

        if(lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(ChatColor.translateAlternateColorCodes('&', ChatUtils.toLegacy(line)));
        this.itemMeta.setLore(lore);

        return this;
    }

    /**
     * Add several lines of lore, each starting with the same value.
     * @param arr Array of Strings to add as lore.
     * @param starter Started string to be added before each line.
     * @return ItemBuilder.
     */
    public ItemBuilder addLore(String[] arr, String starter) {
        List<String> lore = this.itemMeta.getLore();

        if(lore == null) {
            lore = new ArrayList<>();
        }

        for(String str : arr) {
            lore.add(ChatUtils.translateLegacy(starter + str));
        }

        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     * Creates the ItemStack.
     * @return Built ItemStack.
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Set the number of items in the ItemStack.
     * @param amount New Number of items.
     * @return ItemBuilder.
     */
    public ItemBuilder setAmount(final int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Sets the item display name with a String.
     * Supports MiniMessage formatting, though you probably shouldn't use it.
     * @param displayName String to set display name to.
     * @return ItemBuilder.
     */
    public ItemBuilder setDisplayName(final String displayName) {
        this.itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ChatUtils.toLegacy(displayName)));
        return this;
    }

    /**
     * Replaces the ItemStack being used in the builder.
     * Probably doing something wrong if you are using this, but you can I guess.
     * @param itemStack New ItemStack to use.
     * @return ItemBuilder.
     */
    public ItemBuilder setItem(final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
        return this;
    }

    /**
     * Replaces the material being used in the builder.
     * @param material New material.
     * @return ItemBuilder.
     */
    public ItemBuilder setMaterial(final Material material) {
        this.itemStack.setType(material);
        return this;
    }

    /**
     * Set if the item should be unbreakable.
     * Items are unbreakable by default, so should only really be set to true.
     * @param unbreakable Whether the item should be unbreakable.
     * @return ItemBuilder.
     */
    public ItemBuilder setUnbreakable(final boolean unbreakable) {
        this.itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Replaces the XMaterial being used in the builder.
     * @param xMaterial New XMaterial.
     * @return ItemBuilder.
     */
    public ItemBuilder setXMaterial(final XMaterial xMaterial) {
        this.itemStack = xMaterial.parseItem();
        this.itemMeta = itemStack.getItemMeta();
        return this;
    }
}