package org.cws.ultimatePaintOff.arsenal.primaryWeapons.nova;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class Nova {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final String name = "Nova";
    public final String classification = "Controlling";
    public final String ultimateName = instance.sonnenschutz.name;
    public final Material material = Material.COPPER_PICKAXE;
    public final int weaponNumber = 3;
    public final int cost = 16;
    public final int ultPoints = 310;
    public final int damage = 4;
    public final int explosionDamage = 2;

    private final double speedMultiplier = 1.75;
    private final double gravityLevel = 0;
    private final int destructionTime = 5;
    private final int paintLength = 2;
    private final int coolDown = 10;
    private final int explosionRadius = 1;
    private final boolean glowing = false;
    private final float yawOffset = 0;
    private final boolean randomizeYaw = false;

    public ItemStack item() {
        return instance.itemManager.createWeapon(material,name,classification,ultPoints,ultimateName,speedMultiplier,gravityLevel,damage,paintLength,cost,coolDown,destructionTime,explosionRadius,explosionDamage,yawOffset,null);
    }

    public ItemStack gameItem() {
        return instance.itemManager.createItem(material,name);
    }

    public boolean isItem(ItemStack item) {
        return UltimatePaintOff.getInstance().itemManager.isItem(item, material, name);
    }
    public void shoot(Player player) {
        instance.pointsManager.fuel.put(player, instance.pointsManager.fuel.get(player) - cost);
        instance.snowballManager.createSnowball(player,null,speedMultiplier,gravityLevel,name,destructionTime,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,yawOffset,randomizeYaw);
    }
}
