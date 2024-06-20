package me.currycookie.handler;

import me.currycookie.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PreGameHandler {


    private ArrayList<Player> activePlayers;
    private boolean countdown = false, playerLeft = false;
    private EventManager eventManager;
    private int counter = 60;

    public PreGameHandler() {
        Bukkit.getOnlinePlayers().stream().forEach(player -> {
            this.activePlayers.add(player);
        });
        serialize();
    }

    private void serialize() {
        this.eventManager = new EventManager() {
            @EventHandler
            public void blockbreak(BlockBreakEvent event) {
                event.setCancelled(true);
            }

            @EventHandler
            public void blockplace(BlockPlaceEvent event) {
                event.setCancelled(true);
            }

            @EventHandler
            public void playerdamage(EntityDamageEvent event) {
                event.setDamage(0);
                event.setCancelled(true);
            }

            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                activePlayers.add(event.getPlayer());
                if(activePlayers.size() == BedwarsPlugin.getInstance().getBedwarsType().getPlayersNeeded()) {
                    if(!countdown)
                        startCountdown();

                }
            }

            @EventHandler
            public void onLeave(PlayerQuitEvent event) {
                activePlayers.remove(event.getPlayer());
                if(countdown)
                    playerLeft = true;
            }
        };

        Bukkit.getPluginManager().registerEvents(this.eventManager, BedwarsPlugin.getInstance());
    }


    private void startCountdown() {
        this.countdown = true;
        Bukkit.setMotd("§4§lROUND STARTING!!!");
        BedwarsPlugin.getInstance().getServerHandler().startMainGamePhase();
        int counter = this.counter;
        new BukkitRunnable() {
            int count = counter;
            public void run() {
                if(playerLeft) {
                    Bukkit.getOnlinePlayers().stream().forEach(player -> {
                        player.sendMessage(BedwarsPlugin.getInstance().getPrefix() + "§cDer Countdown wurde abgebrochen, da eine Person das Spiel verlassen hat.");
                    });
                    playerLeft = false;
                    super.cancel();
                }
                switch(count) {
                    case 0 -> {
                        Bukkit.getOnlinePlayers().stream().forEach(player -> {
                            player.sendMessage(BedwarsPlugin.getInstance().getPrefix() + "§cDie Runde beginnt jetzt!");
                            player.setLevel(0);
                        });
                        BedwarsPlugin.getInstance().getServerHandler().startMainGamePhase();
                        eventManager = null;
                        super.cancel();
                        break;
                    }
                    case 1,2,3,4,5,6,7,8,9,10 -> {
                        Bukkit.getOnlinePlayers().stream().forEach(player -> {
                            player.sendMessage(BedwarsPlugin.getInstance().getPrefix() + "Die Runde beginnt in §e" + count + "§7 Sekunden");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.5f, 0.5f);
                            player.setLevel(count);
                        });
                        count--;
                        break;
                    }
                    default -> {
                        Bukkit.getOnlinePlayers().stream().forEach(player -> {
                            player.setLevel(count);
                        });
                        count--;
                        break;
                    }
                }
            }
        };
    }
}
