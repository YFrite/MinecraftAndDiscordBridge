package com.yfrite.discordchat.discord;

import com.yfrite.discordchat.Constants;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Sender {

    public static void send(String message, String fromUser){
        for(String channelId: Constants.channels)
            Constants.jda.getTextChannelById(channelId).sendMessage("<" + fromUser + "> " + message).queue();
    }
}