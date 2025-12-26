package org.cws.ultimatePaintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class PlayerDropItemListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (instance.queueManager.checkIfInQueue(player) || instance.gameManager.checkIfInGame(player)) {
            event.setCancelled(true);
            instance.arsenalCoordination.castUltimate(player);
        }
    }
}
