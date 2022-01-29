package com.yfrite.discordchat.server;

import com.yfrite.discordchat.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;


public class Utils {

    public static void setupConfig(Main plugin){
        FileConfiguration config = plugin.getConfig();

        config.addDefault("discordApiKey", "#Where your bot API key");
        config.addDefault("channels", new ArrayList<String>());

        config.options().copyDefaults(false);
        plugin.saveConfig();
    }
}
