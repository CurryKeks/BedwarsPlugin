package me.currycookie;

import me.currycookie.commands.CreateVoidCMD;
import me.currycookie.handler.ServerHandler;
import me.currycookie.libs.BedwarsType;
import me.currycookie.snakeThing.SnakeBuilder;
import me.currycookie.snakeThing.UseListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class BedwarsPlugin extends JavaPlugin {

    private BedwarsType bedwarsType;
    private ServerHandler serverHandler;
    private static BedwarsPlugin instance;
    public static HashMap<String, Player> players;
    public static HashMap<String, Integer> lastBoost;

    public static SnakeBuilder snakeBuilder;

    public void onEnable() {
        // normally config read, now hardcoding
        this.bedwarsType = BedwarsType.CLASSIC8x1;
        this.serverHandler = new ServerHandler();

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
        Bukkit.setMaxPlayers(this.bedwarsType.getPlayersNeeded());
        Bukkit.setMotd("BEDWARS: MAX-PLAYERS: " + this.bedwarsType.getPlayersNeeded());

        this.serverHandler.startPreGamePhase();
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

    public BedwarsType getBedwarsType() {
        return this.bedwarsType;
    }

    public ServerHandler getServerHandler() {
        return this.serverHandler;
    }

    public String getPrefix() {
        return "§4[Bedwars] §7";
    }
}
