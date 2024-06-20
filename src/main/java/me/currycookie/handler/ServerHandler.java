package me.currycookie.handler;

import me.currycookie.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.UUID;

public class ServerHandler {

    private ArrayList<UUID> activePlayers = new ArrayList<>();
    private ArrayList<Player> activeSpectators = new ArrayList<>();
    private EventManager eventManager;
    private PreGameHandler pregame;
    public void startPreGamePhase() {
        this.pregame = new PreGameHandler();
    }
    public void startMainGamePhase() {
        Bukkit.getOnlinePlayers().stream().forEach(player -> activePlayers.add(player.getUniqueId()));
        setJoinSettings();
    }
    private void setJoinSettings() {
        Bukkit.getOnlinePlayers().stream().forEach(player -> {
            this.activePlayers.add(player.getUniqueId());
        });
        this.eventManager = new EventManager() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                if(activePlayers.contains(event.getPlayer().getUniqueId())) {
                    // TODO PLAYER IS GAMER
                } else {
                    // TODO PLAYER IS NOT GAMER
                }
            }
        };
        Bukkit.getPluginManager().registerEvents(this.eventManager, BedwarsPlugin.getInstance());
    }
}
