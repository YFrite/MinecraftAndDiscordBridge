package com.yfrite.discordchat;

import com.yfrite.discordchat.discord.ChannelListener;
import com.yfrite.discordchat.server.*;

import com.yfrite.discordchat.server.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;
        Utils.setupConfig(plugin);

        getServer().getPluginManager().registerEvents(new ChatListener(), plugin);

        this.getCommand("disableMe").setExecutor(new CommandDisableSending());
        this.getCommand("enableMe").setExecutor(new CommandEnableSending());
        //this.getCommand("block").setExecutor(new CommandBlockUser());
        this.getCommand("disableDiscord").setExecutor(new CommandDisableDiscord());
        this.getCommand("enableDiscord").setExecutor(new CommandEnableDiscord());

        //Command For setup Discord Bot
        this.getCommand("setup").setExecutor(new CommandSetup());

    }
}
