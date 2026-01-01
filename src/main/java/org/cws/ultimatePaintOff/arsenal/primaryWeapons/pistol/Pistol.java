package org.cws.ultimatePaintOff.arsenal.primaryWeapons.pistol;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class Pistol {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final String name = "Snap";
    public final String classification = "Atacking";
    public final String ultimateName = instance.heliTornedo.name;
    public final Material material = Material.IRON_HOE;
    public final int weaponNumber = 1;
    public final int cost = 6;
    public final int ultPoints = 200;
    public final int damage = 6;
    public final int explosionDamage = 0;

    private final double speedMultiplier = 3.0;
    private final double gravityLevel = 0;
    private final int destructionTime = 5;
    private final int paintLength = 1;
    private final int coolDown = 0;
    private final int explosionRadius = 0;
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
        return instance.itemManager.isItem(item, material, name);
    }
    public void shoot(Player player) {
        instance.pointsManager.fuel.put(player, instance.pointsManager.fuel.get(player) - cost);
        instance.snowballManager.createSnowball(player,null,speedMultiplier,gravityLevel,name,destructionTime,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,yawOffset,randomizeYaw);
    }
}
