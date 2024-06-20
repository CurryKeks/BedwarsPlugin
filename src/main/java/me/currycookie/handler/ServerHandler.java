package me.currycookie.handler;

import me.currycookie.BedwarsPlugin;
import me.currycookie.enums.TeamSetting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ServerHandler {

    private ArrayList<UUID> activePlayers = new ArrayList<>();
    private HashMap<UUID, TeamSetting> playerTeams = new HashMap<>();
    private ArrayList<Player> activeSpectators = new ArrayList<>();
    private EventManager eventManager;
    private PreGameHandler pregame;
    private MainGameHandler maingame;

    public void startPreGamePhase() {
        this.pregame = new PreGameHandler();
    }
    public void startMainGamePhase() {
        Bukkit.getOnlinePlayers().stream().forEach(player -> activePlayers.add(player.getUniqueId()));
        setJoinSettings();

        this.maingame = new MainGameHandler();
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

    public void addPlayerToTeam(Player player, TeamSetting setting) {
        this.playerTeams.put(player.getUniqueId(),setting);
    }

    public void removePlayerFromTeam(Player player) {
        this.playerTeams.remove(player.getUniqueId());
    }

    public ArrayList<String> currentPlayerOnTeam(TeamSetting team) {
        ArrayList<String> list = new ArrayList<>();
        for(UUID uuid : playerTeams.keySet())
            list.add(Bukkit.getPlayer(uuid).getName());
        return list;
    }

}
