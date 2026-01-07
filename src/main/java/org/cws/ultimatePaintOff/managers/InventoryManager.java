package org.cws.ultimatePaintOff.managers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class InventoryManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    public void clearInventory(Player player) {
        player.getInventory().clear();
    }

    public void getPrimaryItem(Player player) {
        if (instance.selectionManager.weapon.get(player) == null) {
            instance.messageManager.sendInfo(player, "No weapon chosen, defaulting to pistol!");
            instance.selectionManager.weapon.put(player, instance.snap.weaponNumber);
        }
        int weapon = instance.selectionManager.weapon.get(player);
        ItemStack item = instance.arsenalCoordination.getPrimaryItemByWeaponNumber(weapon);
        player.getInventory().setItem(1,item);
    }
}
