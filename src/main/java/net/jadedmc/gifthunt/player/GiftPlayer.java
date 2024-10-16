/*
 * This file is part of GiftHunt, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package net.jadedmc.gifthunt.player;

import net.jadedmc.gifthunt.GiftHuntPlugin;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class GiftPlayer {
    private final GiftHuntPlugin plugin;
    private final Collection<String> foundGifts = new HashSet<>();
    private final UUID uuid;

    public GiftPlayer(@NotNull final GiftHuntPlugin plugin, @NotNull final Player player) {
        this.plugin = plugin;
        this.uuid = player.getUniqueId();

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                final PreparedStatement findGifts = plugin.getMySQL().getConnection().prepareStatement("SELECT * FROM gifthunt_gifts WHERE uuid = ?");
                findGifts.setString(1, this.uuid.toString());
                final ResultSet results = findGifts.executeQuery();

                while(results.next()) {
                    this.foundGifts.add(results.getString("location"));
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void findGift(@NotNull final Block block) {
        final String giftLocation = block.getWorld().getName() + "," +
                block.getX() + "," +
                block.getY() + "," +
                block.getZ();

        this.foundGifts.add(giftLocation);

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                final PreparedStatement findGifts = plugin.getMySQL().getConnection().prepareStatement("INSERT INTO gifthunt_gifts (uuid,location) VALUES(?,?)");
                findGifts.setString(1, this.uuid.toString());
                findGifts.setString(2, giftLocation);
                findGifts.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public int getFoundGifts() {
        return this.foundGifts.size();
    }

    public boolean hasFound(@NotNull final Block block) {
        final String giftLocation = block.getWorld().getName() + "," +
                block.getX() + "," +
                block.getY() + "," +
                block.getZ();

        return this.foundGifts.contains(giftLocation);
    }
}