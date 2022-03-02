package com.yfrite.discordchat;

import com.yfrite.discordchat.discord.ChannelListener;
import com.yfrite.discordchat.server.*;

import com.yfrite.discordchat.server.command.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

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
            JDA jda;

            Constants.DISCORD_API_KEY = apiKey;

            try {
                Constants.jda = JDABuilder.createDefault(apiKey)
                        .setActivity(Activity.playing("ZeroWorld"))
                        .build();

                jda = Constants.jda;

                jda.awaitReady();
                List<String> channelIds = plugin.getConfig().getStringList("channels");
                List<TextChannel> channels = new ArrayList<>();

                plugin.getConfig().set("channels", channelIds);

                for (String id : channelIds){
                    channels.add(jda.getTextChannelById(Long.parseLong(id)));
                    //server.getLogger().info(id);

                }

                Constants.channels = channels;

                plugin.saveConfig();

                plugin.getLogger().info("Channels updated!");

                jda.addEventListener(new ChannelListener());

                CommandListUpdateAction commands = jda.updateCommands();
                commands.addCommands(
                        new CommandData("add", "Add channel for bot.")
                );

                commands.queue();
            } catch (LoginException | InterruptedException e) {
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
}
