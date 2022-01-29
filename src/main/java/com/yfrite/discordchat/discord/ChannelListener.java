package com.yfrite.discordchat.discord;

import com.yfrite.discordchat.Constants;
import com.yfrite.discordchat.Main;
import com.yfrite.discordchat.server.Sender;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.bukkit.ChatColor;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChannelListener extends ListenerAdapter {

    Main plugin;

    {
        plugin = Main.getPlugin();
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getGuild() == null) return;
        if (!event.getName().equals("add")) return;

        if (event.getChannelType().isMessage()) {
            if (true) {
                List<String> channelIds = plugin.getConfig().getStringList("channels");
                List<TextChannel> channels = new ArrayList<>();

                channelIds.add(event.getTextChannel().getId());

                plugin.getConfig().set("channels", channelIds);

                for (String id : channelIds){
                    channels.add(Constants.jda.getTextChannelById(Long.parseLong(id)));
                    //server.getLogger().info(id);

                }

                Constants.channels = channels;

                plugin.saveConfig();

                plugin.getLogger().info("Channels updated!");
                event.reply("Чат добавлен.").queue();

            }
       }

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (Constants.channels.contains(event.getTextChannel())) {
            if(event.getAuthor().isBot()) return;
            String message = event.getMessage().getContentDisplay();
            if (message.equals("")) return;

            String userName = event.getAuthor().getName();

            //server.getLogger().info(event.getAuthor().getAsTag());
            String content = ChatColor.BLUE + "<" + userName + "> " + ChatColor.WHITE + message;

            Sender.sendMessage(false, content);
        }
    }

}


