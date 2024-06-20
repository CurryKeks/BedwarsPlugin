package me.currycookie.builders;

import me.currycookie.BedwarsPlugin;
import me.currycookie.enums.TeamSetting;
import me.currycookie.handler.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class InventoryBuilder {
    public void pregame_team_vote(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "§eWähle dein Team!");
        TeamSetting[] colors = BedwarsPlugin.getInstance().getBedwarsType().getTeamSettings();
        ItemStack[] teamBlocks = new ItemStack[colors.length];

        for(int i = 0; i < teamBlocks.length; i++) {
            teamBlocks[i] = new ItemBuilder(colors[i].getMaterial()).setDisplayName(colors[i].getName()).addNBTTag(PersistentDataType.STRING, "team", colors[i].getName()).setLore(BedwarsPlugin.getInstance().getServerHandler().currentPlayerOnTeam(colors[i])).build();
            inv.addItem(teamBlocks[i]);
        }
        EventManager manager = new EventManager() {
            @EventHandler
            public void onHandle(InventoryClickEvent event) {
                if(event.getCurrentItem() == null) return;
                for(ItemStack item : teamBlocks)
                    if(event.getCurrentItem() == item)
                        pregame_team_chosen(this, player, item);
            }
        };
        Bukkit.getPluginManager().registerEvents(manager, BedwarsPlugin.getInstance());
        player.openInventory(inv);
    }
    public void pregame_team_chosen(EventManager manager, Player player, ItemStack item) {
        manager = null;
        player.closeInventory();
        if(ItemBuilder.hasNBTTag(item, "team")) {
            TeamSetting teamsetting = (TeamSetting) Arrays.stream(TeamSetting.values()).filter(t -> t.getName().equalsIgnoreCase(ItemBuilder.getNBTTag(item, "team"))).toArray()[0];
            if(BedwarsPlugin.getInstance().getServerHandler().currentPlayerOnTeam(teamsetting).size() >= BedwarsPlugin.getInstance().getBedwarsType().getMaxPlayersPerTeam()) {
                player.sendMessage(BedwarsPlugin.getInstance().getPrefix() + "§cDas Team ist voll!");
            } else {
                BedwarsPlugin.getInstance().getServerHandler().addPlayerToTeam(player, teamsetting);
                player.sendMessage(BedwarsPlugin.getInstance().getPrefix() + "§eDu bist dem ausgewählten Team erfolgreich beigetreten!");
            }
        }
    }
}
