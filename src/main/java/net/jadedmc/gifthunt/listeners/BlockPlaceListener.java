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
package net.jadedmc.gifthunt.listeners;

import net.jadedmc.gifthunt.GiftHuntPlugin;
import net.jadedmc.gifthunt.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlockPlaceListener implements Listener {
    private final GiftHuntPlugin plugin;

    public BlockPlaceListener(@NotNull final GiftHuntPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(@NotNull final BlockPlaceEvent event) {
        final Player player = event.getPlayer();

        final ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getType() == Material.AIR) {
            return;
        }

        if(itemStack.getItemMeta() == null) {
            return;
        }

        if(!itemStack.getItemMeta().getDisplayName().contains("Placeable Gift")) {
            return;
        }

        if(!player.hasPermission("gifthunt.admin")) {
            ChatUtils.chat(player, "<red>You do not have permission to do that!");
            event.setCancelled(true);
            return;
        }

        plugin.getGiftManager().addGift(event.getBlock());
        ChatUtils.chat(player, "<green>Present added!");
    }
}
