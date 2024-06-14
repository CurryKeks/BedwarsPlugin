package me.currycookie.commands;

import me.currycookie.world.VoidGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateVoidCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        World world = Bukkit.createWorld(new VoidGenerator(strings[0]));

        world.setType(world.getSpawnLocation().add(0,-3,0), Material.BEDROCK);
        if(commandSender instanceof Player) {
            ((Player) commandSender).teleport(world.getSpawnLocation());
        }
        return true;
    }
}
