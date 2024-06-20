package me.currycookie.snakeThing;

import me.currycookie.BedwarsPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class UseListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getItem() != null) {
            if (e.getItem().getType() == Material.SLIME_BALL && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {

                if (player.getTicksLived() - BedwarsPlugin.lastBoost.get(player.getName()) <= 6) {
                    new BukkitRunnable() {
                        int i = 0;

                        public void run() {
                            if (i < 4) {
                                player.setVelocity(player.getLocation().getDirection().multiply(5));
                            } else
                                super.cancel();
                            i++;
                        }
                    }.runTaskTimer(BedwarsPlugin.getInstance(), 0, 1);

                } else {
                    player.setVelocity(player.getLocation().getDirection().multiply(5));
                }

                BedwarsPlugin.lastBoost.replace(player.getName(), player.getTicksLived());
            } else if (e.getItem().getType() == Material.BAMBOO) {
                if (e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//                    BedwarsPlugin.snakeBuilder.buildSnake(player.getLocation(), 20);

                    Vector direction = player.getLocation().getDirection();
                    int amount = 120;
                    for (int i = 0; i < amount; i++) {
                        BedwarsPlugin.snakeBuilder.buildSnake(player.getLocation().setDirection(direction.rotateAroundY(360.0/amount)), 50);
                    }
                }
            }
        }
    }
}
