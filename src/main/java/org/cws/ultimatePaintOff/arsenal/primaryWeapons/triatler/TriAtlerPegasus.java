package org.cws.ultimatePaintOff.arsenal.primaryWeapons.triatler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class TriAtlerPegasus {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final String name = "Tri-Atler Pegasus";
    public final String classification = "Painting/Supporting";
    public final String ultimateName = instance.sonnenschutz.name;
    public final Material material = Material.BOW;
    public final int weaponNumber = 8;
    public final int cost = 10;
    public final int costSecondary = cost*2;

    public final int ultPoints = 260;
    public final int damage = 2;
    public final int explosionDamage = 0;

    private final double speedMultiplier = 1.25;
    private final double gravityLevel = 0;
    private final int destructionTime = 5;
    private final int paintLength = 1;
    private final int coolDown = 0;
    private final int explosionRadius = 0;
    private final boolean glowing = false;
    private final float yawOffset = 15f;
    private final float yawOffsetTwo = -15f;
    private final String secondaryAbility = "Longshot";

    public ItemStack item() {
        return instance.itemManager.createWeapon(material,name,classification,ultPoints,ultimateName,speedMultiplier,gravityLevel,damage,paintLength,cost,coolDown,destructionTime,explosionRadius,explosionDamage,yawOffset,secondaryAbility);
    }

    public ItemStack gameItem() {
        return instance.itemManager.createItem(material,name);
    }

    public boolean isItem(ItemStack item) {
        return instance.itemManager.isItem(item, material, name);
    }
    public void shoot(Player player) {
        instance.pointsManager.fuel.put(player, instance.pointsManager.fuel.get(player) - cost);
        instance.snowballManager.createSnowball(player,null,speedMultiplier,gravityLevel,name,destructionTime,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,0);
        instance.snowballManager.createSnowball(player,null,speedMultiplier,gravityLevel,name,destructionTime,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,yawOffset);
        instance.snowballManager.createSnowball(player,null,speedMultiplier,gravityLevel,name,destructionTime,glowing,paintLength,null,true,coolDown,explosionRadius,explosionDamage,yawOffsetTwo);
    }

    public void shootSecondary(Player player) {
        instance.pointsManager.fuel.put(player, instance.pointsManager.fuel.get(player) - costSecondary);
        instance.snowballManager.createSnowball(player,null,speedMultiplier+0.5,gravityLevel/3,name,destructionTime*2,glowing,paintLength,null,true,coolDown+10,explosionRadius,explosionDamage,0);
        instance.snowballManager.createSnowball(player,null,speedMultiplier+0.25,gravityLevel/3,name,destructionTime*2,glowing,paintLength,null,true,coolDown+10,explosionRadius,explosionDamage,yawOffset/6);
        instance.snowballManager.createSnowball(player,null,speedMultiplier+0.25,gravityLevel/3,name,destructionTime*2,glowing,paintLength,null,true,coolDown+10,explosionRadius,explosionDamage,yawOffsetTwo/6);
    }
}
