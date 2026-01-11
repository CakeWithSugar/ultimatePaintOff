package org.cws.ultimatePaintOff.arsenal.primaryWeapons.nova;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class NovaPuls {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final String name = "Nova Puls";
    public final String difficulty = "⭐⭐⭐☆☆";
    public final String ultimateName = instance.fokusBooster.name;
    public final Material material = Material.IRON_PICKAXE;
    public final int weaponNumber = 4;
    public final int cost = 18;
    public final int ultPoints = 320;
    public final int damage = 0;
    public final int explosionDamage = 1;

    private final double speedMultiplier = 1.5;
    private final double gravityLevel = 0.1;
    private final int destructionTime = 20;
    private final int paintLength = 1;
    private final int coolDown = 0;
    private final int explosionRadius = 1;
    private final boolean glowing = false;
    private final float yawOffset = 0;
    private final boolean randomizeYaw = false;

    public ItemStack item() {
        return instance.itemManager.createWeapon(material, name, difficulty, ultPoints, ultimateName, speedMultiplier, gravityLevel, damage, paintLength, cost, coolDown, destructionTime, explosionRadius, explosionDamage,yawOffset,null);
    }

    public ItemStack gameItem() {
        return instance.itemManager.createItem(material, name);
    }

    public boolean isItem(ItemStack item) {
        return UltimatePaintOff.getInstance().itemManager.isItem(item, material, name);
    }

    public void shoot(Player player) {
        instance.pointsManager.fuel.put(player, instance.pointsManager.fuel.get(player) - cost);
        instance.snowballManager.createSnowball(player, null, speedMultiplier, gravityLevel, name, destructionTime, glowing, paintLength, null, true, coolDown, explosionRadius, explosionDamage,yawOffset,randomizeYaw);
    }
}
