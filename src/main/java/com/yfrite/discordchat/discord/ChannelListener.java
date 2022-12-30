package com.yfrite.discordchat.discord;

import com.yfrite.discordchat.Constants;
import com.yfrite.discordchat.Main;
import com.yfrite.discordchat.server.Sender;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChannelListener extends ListenerAdapter {

    Main plugin;

    {
        plugin = Main.getPlugin();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) return;
        if (!event.getName().equals("add")) return;

        if (event.getChannelType() == ChannelType.TEXT) {
            Constants.channels.add(event.getChannel().getId());

            plugin.getConfig().set("channels", Constants.channels);
            plugin.saveConfig();

            plugin.getLogger().info("Channels updated!");
            event.reply("Чат добавлен.").queue();
        }

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        var channel = event.getChannel();

        if (Constants.channels.contains(channel.getId())) {
            if(event.getAuthor().isBot()) return;
            String message = event.getMessage().getContentDisplay();
            if (message.equals("")) return;

            String userName = event.getAuthor().getName();

            String content = ChatColor.BLUE + userName + ": " + ChatColor.WHITE + message;

            Sender.sendMessage(false, content);
        }
    }

}


