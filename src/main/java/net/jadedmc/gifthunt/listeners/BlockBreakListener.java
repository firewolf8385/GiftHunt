package net.jadedmc.gifthunt.listeners;

import net.jadedmc.gifthunt.GiftHuntPlugin;
import net.jadedmc.gifthunt.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakListener implements Listener {
    private final GiftHuntPlugin plugin;

    public BlockBreakListener(@NotNull final GiftHuntPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(@NotNull final BlockBreakEvent event) {
        final Player player = event.getPlayer();

        if(!plugin.getGiftManager().isGift(event.getBlock())) {
            return;
        }

        if(!player.hasPermission("gifthunt.admin")) {
            ChatUtils.chat(player, "<red>You do not have permission to do that!");
            event.setCancelled(true);
            return;
        }

        plugin.getGiftManager().removeGift(event.getBlock());
        ChatUtils.chat(player, "<red>Gift removed!");
    }
}