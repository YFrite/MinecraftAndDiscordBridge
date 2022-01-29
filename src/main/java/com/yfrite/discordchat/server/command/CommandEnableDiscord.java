package com.yfrite.discordchat.server.command;

import com.yfrite.discordchat.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandEnableDiscord implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Main plugin = Main.getPlugin();
        String userName = sender.getName();
        List<String> users = plugin.getConfig().getStringList("disabledForSending");

        if (!users.contains(userName))
            sender.sendMessage(
                    "Вы не отключили просмотр сообщений с чата дискорда! Чтобы отключить это, напишите /disableDiscord"
            );
        else {
            users.remove(userName);
            plugin.getConfig().set("disabledForSending", users);
            sender.sendMessage(
                    "Вы включили просмотр сообщений с чата дискорда! Чтобы выключить это, напишите /enableDiscord"
            );
        }

        plugin.saveConfig();


        return true;
    }
}
