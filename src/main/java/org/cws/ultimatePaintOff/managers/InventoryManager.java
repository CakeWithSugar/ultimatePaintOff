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
        int number = instance.selectionManager.weapon.get(player);
        ItemStack weapon = instance.arsenalCoordination.getPrimaryItemByWeaponNumber(number);
        player.getInventory().setItem(1,weapon);
        ItemStack ult = instance.arsenalCoordination.getUltInfoItemByWeaponNumber(number);
        player.getInventory().setItem(8,ult);
    }
}
