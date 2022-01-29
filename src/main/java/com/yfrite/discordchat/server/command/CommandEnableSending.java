package com.yfrite.discordchat.server.command;

import com.yfrite.discordchat.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class CommandEnableSending implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Main plugin = Main.getPlugin();
        String userName = sender.getName();
        List<String> users = plugin.getConfig().getStringList("disabledPlayers");

        if (!users.contains(userName))
            sender.sendMessage("Вы не отключили трансляцию ваших сообщений в чат дискорда! Чтобы выключить это, напишите /disableMe");
        else {
            users.remove(userName);
            plugin.getConfig().set("disabledPlayers", users);
            sender.sendMessage("Вы включили трансляцию ваших сообщений в чат дискорда! Чтобы выключить это снова, напишите /disableMe");
        }

        plugin.saveConfig();

        return true;
    }
}