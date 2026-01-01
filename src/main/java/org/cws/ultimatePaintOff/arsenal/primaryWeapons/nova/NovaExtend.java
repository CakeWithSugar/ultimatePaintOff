package org.cws.ultimatePaintOff.arsenal.primaryWeapons.nova;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class NovaExtend {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final String name = "Nova Extend";
    public final String classification = "Pressureing";
    public final String ultimateName = instance.heliTornedo.name;
    public final Material material = Material.GOLDEN_PICKAXE;
    public final int weaponNumber = 5;
    public final int cost = 20;
    public final int ultPoints = 300;
    public final int damage = 1;
    public final int explosionDamage = 4;

    private final double speedMultiplier = 1.5;
    private final double gravityLevel = 0;
    private final int destructionTime = 10;
    private final int paintLength = 1;
    private final int coolDown = 10;
    private final int explosionRadius = 1;
    private final boolean glowing = false;
    private final float yawOffset = 0;
    private final boolean randomizeYaw = false;

    public ItemStack item() {
        return instance.itemManager.createWeapon(material, name, classification, ultPoints, ultimateName, speedMultiplier, gravityLevel, damage, paintLength, cost, coolDown, destructionTime, explosionRadius, explosionDamage,yawOffset,null);
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
