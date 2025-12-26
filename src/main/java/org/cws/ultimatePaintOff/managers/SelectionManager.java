package org.cws.ultimatePaintOff.managers;

import org.bukkit.entity.Player;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.HashMap;
import java.util.Map;

public class SelectionManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public Map<Player, Integer> weapon = new HashMap<>();

    public void put(int num,String name, Player player) {
        weapon.remove(player);
        weapon.put(player, num);
        instance.messageManager.sendMessage(player,"You have selected §e" + name + " §7as your weapon!");
    }
}
