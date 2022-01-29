package com.yfrite.discordchat.server.command;

import com.yfrite.discordchat.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandDisableSending implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Main plugin = Main.getPlugin();
        String userName = sender.getName();
        List<String> users = plugin.getConfig().getStringList("disabledPlayers");

        if (users.contains(userName))
            sender.sendMessage("Вы уже отключили трансляцию ваших сообщений в чат дискорда! Чтобы включить это, напишите /enableMe");
        else {
            users.add(userName);
            plugin.getConfig().set("disabledPlayers", users);
            sender.sendMessage("Вы отключили трансляцию ваших сообщений в чат дискорда! Чтобы включить это снова, напишите /enableMe");
        }

        plugin.saveConfig();

        return true;
    }
}