package me.currycookie.builders;

import me.currycookie.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SnakeBuilder {

    public void buildSnake(Location location, int size) {
//        Bukkit.broadcastMessage(location.getDirection().toString());
        Vector direction = location.getDirection();

//        for (int i = 0; i < size; i++) {
//            location.subtract(direction);
//            if (location.getBlock().getType() != Material.AIR && location.getBlock().getType() != Material.GLASS) {
//                break;
//            }
//            location.getBlock().setType(Material.GLASS);
//        }

        new BukkitRunnable() {
            int i = 0;
            Location[] oldBlockLocations = new Location[size];
            public void run() {
                boolean collided = false;
                if (i < size) {
                    Location tempLocation = location.clone().add(direction.clone().multiply(i));
                    oldBlockLocations[i] = (tempLocation);
                    if (tempLocation.getBlock().getType() != Material.AIR && tempLocation.getBlock().getType() != Material.GLASS) {
                        collided = true;
                    } else {
                        tempLocation.getBlock().setType(Material.GLASS);
                    }

                } else if (i < size*2) {
                    if (location.getWorld().getBlockAt(oldBlockLocations[i-size]).getType() == Material.GLASS) {
                        location.getWorld().getBlockAt(oldBlockLocations[i-size]).setType(Material.AIR);
                        FallingBlock fallingBlock = oldBlockLocations[i-size].getWorld().spawnFallingBlock(location.clone().add(direction.clone().multiply(i-size)), Material.GLASS.createBlockData());
                        fallingBlock.setCancelDrop(true);
                        fallingBlock.setGlowing(true);
                        fallingBlock.setDamagePerBlock(20000);
                        fallingBlock.setGravity(true);
                    }
                } else {
                    super.cancel();
                }
                if (collided) {
                    i = size;
                    collided = false;
                } else {
                    i++;
                }
            }
        }.runTaskTimer(BedwarsPlugin.getInstance(), 0, 1);
    }
}
