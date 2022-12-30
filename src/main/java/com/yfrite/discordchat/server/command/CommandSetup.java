package com.yfrite.discordchat.server.command;

import com.yfrite.discordchat.Constants;
import com.yfrite.discordchat.Main;
import com.yfrite.discordchat.discord.ChannelListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class CommandSetup implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.isOp()) return false;

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

        return true;
    }
}
