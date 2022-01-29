package com.yfrite.discordchat.server;

import com.yfrite.discordchat.Main;
import com.yfrite.discordchat.discord.Sender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(Main.getPlugin().getConfig().getStringList("disabledPlayers").contains(event.getPlayer().getName())) return;
        String message = event.getMessage();
        String fromUser = event.getPlayer().getDisplayName();
        Sender.send(message, fromUser);
    }
}
