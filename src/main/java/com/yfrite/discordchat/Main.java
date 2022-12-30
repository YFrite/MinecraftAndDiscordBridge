package com.yfrite.discordchat;

import com.yfrite.discordchat.discord.ChannelListener;
import com.yfrite.discordchat.server.ChatListener;
import com.yfrite.discordchat.server.Utils;
import com.yfrite.discordchat.server.command.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
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

        try{
            Main plugin = Main.getPlugin();
            String apiKey = plugin.getConfig().getString("discordApiKey");

            Constants.DISCORD_API_KEY = apiKey;

            try {
                var jda = JDABuilder.createDefault(apiKey)
                        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                        .build()
                        .awaitReady();

                Constants.jda = jda;

                Constants.channels = plugin.getConfig().getStringList("channels");

                plugin.saveConfig();

                plugin.getLogger().info("Channels updated!");

                jda.addEventListener(new ChannelListener());

                jda.updateCommands().addCommands(
                        Commands.slash("add", "Add channel for bot.")
                ).queue();

            } catch (InterruptedException e) {
                plugin.getLogger().throwing("CommandSetup", "onCommand", e);
            }
        } catch (Exception exception){
            plugin.getLogger().warning("Please specify bot token!");
            plugin.getServer().shutdown();
        }

        this.getCommand("disableMe").setExecutor(new CommandDisableSending());
        this.getCommand("enableMe").setExecutor(new CommandEnableSending());
        //this.getCommand("block").setExecutor(new CommandBlockUser());
        this.getCommand("disableDiscord").setExecutor(new CommandDisableDiscord());
        this.getCommand("enableDiscord").setExecutor(new CommandEnableDiscord());

        //Command For setup Discord Bot
        this.getCommand("setup").setExecutor(new CommandSetup());

    }

    @Override
    public void onDisable() {

        Constants.jda.cancelRequests();
        Constants.jda.shutdownNow();
    }
}
