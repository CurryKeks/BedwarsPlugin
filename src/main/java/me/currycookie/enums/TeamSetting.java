package me.currycookie.enums;

import org.bukkit.Material;

public enum TeamSetting {
    RED("ROT",Material.RED_DYE),BLUE("BLAU", Material.BLUE_DYE),
    YELLOW("GELB", Material.YELLOW_DYE),GREEN("GRUEN", Material.GREEN_DYE),
    PURPLE("LILA", Material.PURPLE_DYE),ORANGE("ORANGE", Material.ORANGE_DYE),
    WHITE("WEIß", Material.WHITE_DYE),BLACK("SCHWARZ", Material.BLACK_DYE);

    private Material material;
    private String name;

    TeamSetting(String name, Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }

    public String getName() {
        return this.name;
    }
}
