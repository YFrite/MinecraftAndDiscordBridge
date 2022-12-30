package com.yfrite.discordchat.server;

import com.yfrite.discordchat.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Sender {

    public static void sendMessage(boolean isGlobal, String text) {
        Main plugin = Main.getPlugin();

        if (isGlobal)
            plugin.getServer().broadcast(Component.text(text));
        else {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

            List<String> blockedForSending = plugin.getConfig().getStringList("disabledForSending");

            for (Player player : players) {
                if(blockedForSending.contains(player.getName())) continue;
                player.sendMessage(text);
            }
        }
    }

}
