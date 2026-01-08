package org.cws.ultimatePaintOff.arsenal.primaryWeapons.snap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class SnapComplex {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final String name = "Snap Complex";
    public final String classification = "Painting";
    public final String ultimateName = instance.krawumKreisel.name;
    public final Material material = Material.GOLDEN_HOE;
    public final int weaponNumber = 9;
    public final int cost = 8;
    public final int ultPoints = 300;
    public final int damage = 4;
    public final int explosionDamage = 0;

    private final double speedMultiplier = 2.5;
    private final double gravityLevel = 0;
    private final int destructionTime = 5;
    private final int paintLength = 2;
    private final int coolDown = 0;
    private final int explosionRadius = 0;
    private final boolean glowing = false;
    private final float yawOffset = 15;
    private final boolean randomizeYaw = true;

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
