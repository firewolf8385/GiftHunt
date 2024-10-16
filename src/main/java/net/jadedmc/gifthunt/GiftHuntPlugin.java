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
package net.jadedmc.gifthunt;

import net.jadedmc.gifthunt.commands.GiftHuntCMD;
import net.jadedmc.gifthunt.databases.MySQL;
import net.jadedmc.gifthunt.gifts.GiftManager;
import net.jadedmc.gifthunt.listeners.*;
import net.jadedmc.gifthunt.player.GiftPlayerManager;
import net.jadedmc.gifthunt.settings.ConfigManager;
import net.jadedmc.gifthunt.settings.HookManager;
import net.jadedmc.gifthunt.utils.ChatUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class GiftHuntPlugin extends JavaPlugin {
    private ConfigManager configManager;
    private GiftManager giftManager;
    private GiftPlayerManager giftPlayerManager;
    private HookManager hookManager;
    private MySQL mySQL;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ChatUtils.initialize(this);
        configManager = new ConfigManager(this);
        hookManager = new HookManager(this);
        giftPlayerManager = new GiftPlayerManager(this);
        giftManager = new GiftManager(this);
        mySQL = new MySQL(this);

        if(mySQL.isEnabled()) {
            mySQL.openConnection();
        }
        else {
            getLogger().warning("MySQL is required! Please enable it in config.yml.");
            getServer().getPluginManager().disablePlugin(this);
        }

        registerListeners();
        getCommand("gifthunt").setExecutor(new GiftHuntCMD());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ChatUtils.disable();
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public GiftManager getGiftManager() {
        return giftManager;
    }

    public GiftPlayerManager getGiftPlayerManager() {
        return this.giftPlayerManager;
    }

    public HookManager getHookManager() {
        return this.hookManager;
    }

    public MySQL getMySQL() {
        return this.mySQL;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
    }

    public void reload() {
        this.configManager.reloadConfig();
    }
}