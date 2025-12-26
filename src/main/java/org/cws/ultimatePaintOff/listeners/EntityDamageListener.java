package org.cws.ultimatePaintOff.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class EntityDamageListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player player)) return;
        if (instance.queueManager.checkIfInQueue(player) || instance.gameManager.checkIfInGame(player)) {
            event.setCancelled(true);
        }
    }
}
