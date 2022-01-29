package com.yfrite.discordchat.server.command;

import com.yfrite.discordchat.Constants;
import com.yfrite.discordchat.Main;
import com.yfrite.discordchat.discord.ChannelListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

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

        return true;
    }
}
