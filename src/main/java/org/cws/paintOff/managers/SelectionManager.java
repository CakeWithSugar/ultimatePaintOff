package org.cws.paintOff.managers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.paintOff.PaintOff;

import java.util.HashMap;
import java.util.Map;

public class SelectionManager {
    PaintOff instance = PaintOff.getInstance();
    public Map<Player, String> weapon = new HashMap<>();
    public Map<Player, String> nextWeapon = new HashMap<>();

    public void put(String name, Player player) {
        if (instance.gameManager.checkIfInGame(player)) {
            nextWeapon.remove(player);
            nextWeapon.put(player, name);
            instance.messageManager.sendMessage(player,"You have selected §e" + name + " §7as your next weapon!");
            return;
        }
        weapon.remove(player);
        weapon.put(player, name);
        instance.messageManager.sendMessage(player,"You have selected §e" + name + " §7as your weapon!");
    }

    public void changeWeapon(Player player) {
        if (nextWeapon.containsKey(player)) {
            weapon.put(player, nextWeapon.get(player));
            nextWeapon.remove(player);
            instance.pointsManager.ultPoint.put(player, 0);
            instance.messageManager.sendMessage(player, "You have changed your weapon!");

            String name = instance.selectionManager.weapon.get(player);
            ItemStack weapon = instance.arsenalCoordination.getPrimaryItemByWeaponName(name);
            player.getInventory().setItem(1,weapon);
            ItemStack ult = instance.arsenalCoordination.getUltInfoItemByWeaponNumber(name);
            player.getInventory().setItem(8,ult);
        }
    }
}
