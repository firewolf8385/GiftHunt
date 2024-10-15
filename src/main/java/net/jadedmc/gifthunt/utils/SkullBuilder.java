package net.jadedmc.gifthunt.utils;

import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.ProfileInputType;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Makes textured player heads easier to created.
 */
public class SkullBuilder {
    private ItemStack item;

    /**
     * Creates a SkullBuilder with an empty skull.
     */
    public SkullBuilder() {
        this.item = new ItemStack(Material.PLAYER_HEAD);
    }

    /**
     * Creates a Skull from it's Base64 encoded texture.
     * @param base64 Texture of the skull to use.
     * @return SkullBuilder.
     */
    public SkullBuilder fromBase64(@NotNull final String base64) {
        this.item = XSkull.createItem().profile(Profileable.of(ProfileInputType.BASE64, base64)).apply();
        return this;
    }

    /**
     * Creates a Skull from a player.
     * @param offlinePlayer OfflinePlayer to create the Skull of.
     * @return SkullBuilder.
     */
    public SkullBuilder fromPlayer(@NotNull final OfflinePlayer offlinePlayer) {
        this.item = XSkull.createItem().profile(Profileable.of(offlinePlayer)).apply();
        return this;
    }

    /**
     * Creates a Skull from a player's UUID.
     * @param uuid UUID of the player to create the skull from.
     * @return SkullBuilder.
     */
    public SkullBuilder fromUUID(@NotNull final UUID uuid) {
        this.item = XSkull.createItem().profile(Profileable.of(uuid)).apply();
        return this;
    }

    /**
     * Converts the builder to an ItemBuilder.
     * @return ItemBuilder.
     */
    public ItemBuilder asItemBuilder() {
        return new ItemBuilder(item);
    }
}