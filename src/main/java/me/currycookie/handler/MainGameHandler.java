package me.currycookie.handler;

import me.currycookie.enums.SpawnerType;
import me.currycookie.enums.TeamSetting;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MainGameHandler {
    /*
    GLOBAL:
        - Spawner
        - Zeit
        - Shop (Villager)
        - Scoreboard (Teams (Bed<YES/NO>, Time)
    CUSTOM:
        - Teamchat
     */
    private HashMap<Block, SpawnerType> spawner;
    private HashMap<Player, TeamSetting> playerTeamColor;
}
