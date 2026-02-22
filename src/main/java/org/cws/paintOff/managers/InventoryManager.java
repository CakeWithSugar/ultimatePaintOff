package org.cws.paintOff.managers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.paintOff.PaintOff;

public class InventoryManager {
    PaintOff instance = PaintOff.getInstance();

    public void clearInventory(Player player) {
        player.getInventory().clear();
    }

    public void getPrimaryItem(Player player) {
        if (instance.selectionManager.weapon.get(player) == null) {
            instance.messageManager.sendInfo(player, "No weapon chosen, defaulting to pistol!");
            instance.selectionManager.weapon.put(player, instance.snap.name);
        }
        String name = instance.selectionManager.weapon.get(player);
        ItemStack weapon = instance.arsenalCoordination.getPrimaryItemByWeaponName(name);
        player.getInventory().setItem(1,weapon);
        ItemStack ult = instance.arsenalCoordination.getUltInfoItemByWeaponNumber(name);
        player.getInventory().setItem(8,ult);
    }
}
