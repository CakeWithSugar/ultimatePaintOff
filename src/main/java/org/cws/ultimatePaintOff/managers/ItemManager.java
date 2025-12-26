package org.cws.ultimatePaintOff.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6" + name);
            List<String> lore = new ArrayList<>();
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createVote(String name, int votes) {
        ItemStack item = new ItemStack(Material.MAP);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6" + name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Click to vote");
            lore.add("§7Voted: §e" + votes);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createWeapon(Material material, String name, String classification,int ultPoints,String ultimateName,double speed, double gravity, int damage,int paintDepth, int cost, double coolDown,double destructionTime, int explosionRadius, int explosionDamage, float yawOffset, String secondaryAbility) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        double cool = coolDown/20;
        double destr = destructionTime/20;
        if (meta != null) {
            meta.setDisplayName("§6" + name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Ultimate: §e§6" + ultimateName);
            lore.add("§7Ultimate Points needed: §e" + ultPoints);
            lore.add("§7Classification: §e" + classification);
            lore.add("");
            lore.add("§7Bullet speed: §e" + speed);
            lore.add("§7Cost per shot: §e" + cost);
            lore.add("§7Damage per Projectile: §e" + damage);
            lore.add("§7Painting depth: §e" + paintDepth + " §5blocks");
            lore.add("");
            if (gravity == 0) {
                lore.add("§7No Gravity");
            } else {
                lore.add("§7Gravity multiplication: §e" + gravity);
            }
            if (destr != 0) {
                lore.add("§7Self destruction time: §e" + destr + " §5s");
            }
            if (cool != 0) {
                lore.add("§7Cool-Down: §e" + cool + " §5s");
            }
            if (explosionRadius != 0) {
                lore.add("§7Explosion radius: §e" + explosionRadius + " §5blocks");
            }
            if (explosionDamage != 0) {
                lore.add("§7Explosion damage: §e" + explosionDamage);
            }
            if (yawOffset != 0) {
                lore.add("§7Projectile offset: §e" + yawOffset*2 + "°");
            }
            if (secondaryAbility != null) {
                lore.add("");
                lore.add("§7Secondary ability: §a" + secondaryAbility);
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public boolean isItem(ItemStack item, Material material, String name) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        return item.getType() == material && item.getItemMeta().getDisplayName().equals("§6"+name);
    }
}
