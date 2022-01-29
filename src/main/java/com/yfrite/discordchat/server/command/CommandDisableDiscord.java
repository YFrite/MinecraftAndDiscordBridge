package com.yfrite.discordchat.server.command;

import com.yfrite.discordchat.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandDisableDiscord implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Main plugin = Main.getPlugin();
        String userName = sender.getName();
        List<String> users = plugin.getConfig().getStringList("disabledForSending");

        if (users.contains(userName))
            sender.sendMessage(
                    "Вы уже отключили просмотр сообщений с чата дискорда! Чтобы включить это, напишите /enableDiscord");
        else {
            users.add(userName);
            plugin.getConfig().set("disabledForSending", users);
            sender.sendMessage(
                    "Вы отключили просмотр сообщений с чата дискорда! Чтобы включить это снова, напишите /enableDiscord");
        }

        plugin.saveConfig();

        return true;
    }
}
