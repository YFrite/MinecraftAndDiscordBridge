package com.yfrite.discordchat.discord;

import com.yfrite.discordchat.Constants;
import net.dv8tion.jda.api.entities.TextChannel;

public class Sender {

    public static void send(String message, String fromUser){
        for(TextChannel channel: Constants.channels)
            channel.sendMessage("<" + fromUser + "> " + message).queue();
    }
}