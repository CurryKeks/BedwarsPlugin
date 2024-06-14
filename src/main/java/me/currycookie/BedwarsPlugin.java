package me.currycookie;

import me.currycookie.builders.SnakeBuilder;
import me.currycookie.commands.CreateVoidCMD;
import me.currycookie.listeners.UseListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class BedwarsPlugin extends JavaPlugin {

    private static BedwarsPlugin instance;
    public static HashMap<String, Player> players;
    public static HashMap<String, Integer> lastBoost;

    public static SnakeBuilder snakeBuilder;

    public void onEnable() {
        instance = this;
        players = new HashMap<>();
        lastBoost = new HashMap<>();

        snakeBuilder = new SnakeBuilder();

        registerListeners();
        registerCommands();

        for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
            currentPlayer.sendMessage("myPlugin reloaded successfully");

            lastBoost.put(currentPlayer.getName(), currentPlayer.getTicksLived());
            players.put(currentPlayer.getName(), currentPlayer);
        }
    }

    public void registerListeners() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new UseListener(), this);

    }

    public void registerCommands() {
        getCommand("createvoid").setExecutor(new CreateVoidCMD());
    }


    @Override
    public void onDisable() {
        Bukkit.broadcastMessage("Bye! :_)");
        // Plugin shutdown logic
    }

    public static BedwarsPlugin getInstance() {
        return instance;
    }

    public String getPrefix() {
        return "§4[Bedwars] §7";
    }
}
