package org.cws.ultimatePaintOff.managers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.HashMap;
import java.util.Map;

public class SelectionManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public Map<Player, Integer> weapon = new HashMap<>();
    public Map<Player, Integer> nextWeapon = new HashMap<>();

    public void put(int num,String name, Player player) {
        if (instance.gameManager.checkIfInGame(player)) {
            nextWeapon.remove(player);
            nextWeapon.put(player, num);
            instance.messageManager.sendMessage(player,"You have selected §e" + name + " §7as your next weapon!");
            return;
        }
        weapon.remove(player);
        weapon.put(player, num);
        instance.messageManager.sendMessage(player,"You have selected §e" + name + " §7as your weapon!");
    }

    public void changeWeapon(Player player) {
        if (nextWeapon.containsKey(player)) {
            weapon.put(player, nextWeapon.get(player));
            nextWeapon.remove(player);
            instance.pointsManager.ultPoint.put(player, 0);
            instance.messageManager.sendMessage(player, "You have changed your weapon!");

            int number = instance.selectionManager.weapon.get(player);
            ItemStack weapon = instance.arsenalCoordination.getPrimaryItemByWeaponNumber(number);
            player.getInventory().setItem(1,weapon);
            ItemStack ult = instance.arsenalCoordination.getUltInfoItemByWeaponNumber(number);
            player.getInventory().setItem(8,ult);
        }
    }
}
