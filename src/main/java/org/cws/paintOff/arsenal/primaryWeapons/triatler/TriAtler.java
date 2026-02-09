package org.cws.paintOff.arsenal.primaryWeapons.triatler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.paintOff.PaintOff;

public class TriAtler {
    PaintOff instance = PaintOff.getInstance();
    public final String name = "Tri-Atler";
    public final String difficulty = "⭐⭐⭐☆☆";
    public final String ultimateName = instance.jettBlaster.name;
    public final Material material = Material.BOW;
    public final int weaponNumber = 6;
    public final int cost = 12;
    public final int costSecondary = cost*3;

    public final int ultPoints = 320;
    public final int damage = 2;
    public final int explosionDamage = 0;

    private final double speedMultiplier = 1.25;
    private final double gravityLevel = 0.15;
    private final int destructionTime = 0;
    private final int paintLength = 2;
    private final int coolDown = 5;
    private final int explosionRadius = 0;
    private final boolean glowing = false;
    private final float yawOffset = 15f;
    private final float yawOffsetTwo = -15f;
    private final String secondaryAbility = "Longshot";
    private final boolean randomizeYaw = false;

    public ItemStack item() {
        return instance.itemManager.createWeapon(material,name, difficulty,ultPoints,ultimateName,speedMultiplier,gravityLevel,damage,paintLength,cost,coolDown,destructionTime,explosionRadius,explosionDamage,yawOffset,secondaryAbility);
    }

    public ItemStack gameItem() {
        return instance.itemManager.createItem(material,name);
    }

    public boolean isItem(ItemStack item) {
        return instance.itemManager.isItem(item, material, name);
    }
    public void shoot(Player player) {
        instance.pointsManager.fuel.put(player, instance.pointsManager.fuel.get(player) - cost);
        instance.snowballManager.createSnowball(player,null,speedMultiplier,gravityLevel,name,destructionTime,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,0,randomizeYaw);
        instance.snowballManager.createSnowball(player,null,speedMultiplier,gravityLevel,name,destructionTime,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,yawOffset,randomizeYaw);
        instance.snowballManager.createSnowball(player,null,speedMultiplier,gravityLevel,name,destructionTime,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,yawOffsetTwo,randomizeYaw);
    }

    public void shootSecondary(Player player) {
        instance.pointsManager.fuel.put(player, instance.pointsManager.fuel.get(player) - costSecondary);
        instance.snowballManager.createSnowball(player,null,speedMultiplier+0.5,gravityLevel/2,name,destructionTime+10,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,0,randomizeYaw);
        instance.snowballManager.createSnowball(player,null,speedMultiplier+0.25,gravityLevel/2,name,destructionTime+10,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,yawOffset/6,randomizeYaw);
        instance.snowballManager.createSnowball(player,null,speedMultiplier+0.25,gravityLevel/2,name,destructionTime+10,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,yawOffsetTwo/6,randomizeYaw);
    }
}
