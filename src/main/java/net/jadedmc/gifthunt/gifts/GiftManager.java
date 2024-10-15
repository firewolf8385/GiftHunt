package net.jadedmc.gifthunt.gifts;

import net.jadedmc.gifthunt.GiftHuntPlugin;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GiftManager {
    private final GiftHuntPlugin plugin;

    public GiftManager(@NotNull final GiftHuntPlugin plugin) {
        this.plugin = plugin;
    }

    public void addGift(@NotNull final Block block) {
        final List<String> storedGifts = plugin.getConfigManager().getGifts().getStringList("gifts");

        // Forms the storage string for the gift location.
        final String giftLocation = block.getWorld().getName() + "," +
                block.getX() + "," +
                block.getY() + "," +
                block.getZ();

        // Skips the present if it is already
        if(storedGifts.contains(giftLocation)) {
            return;
        }

        // Adds the gift to the stored gifts lists.
        storedGifts.add(giftLocation);

        // Updates gifts.yml.
        plugin.getConfigManager().getGifts().set("gifts", storedGifts);
        plugin.getConfigManager().saveGifts();
        plugin.getConfigManager().reloadGifts();
    }

    public boolean isGift(@NotNull final Block block) {
        // Load gift locations from config.yml
        final List<String> storedGifts = plugin.getConfigManager().getGifts().getStringList("gifts");

        // Get the String location of the block.
        final String giftLocation = block.getWorld().getName() + "," +
                block.getX() + "," +
                block.getY() + "," +
                block.getZ();

        // Check if that location is a stored gift.
        return storedGifts.contains(giftLocation);
    }

    public void removeGift(@NotNull final Block block) {
        // Load gift locations from config.yml
        final List<String> storedGifts = plugin.getConfigManager().getGifts().getStringList("gifts");

        // Get the String location of the block.
        final String giftLocation = block.getWorld().getName() + "," +
                block.getX() + "," +
                block.getY() + "," +
                block.getZ();

        // Exit if the location is not a gift.
        if(!storedGifts.contains(giftLocation)) {
            return;
        }

        // Removes the gift from the list.
        storedGifts.remove(giftLocation);

        // Updates gifts.yml.
        plugin.getConfigManager().getGifts().set("gifts", storedGifts);
        plugin.getConfigManager().saveGifts();
        plugin.getConfigManager().reloadGifts();
    }
}
