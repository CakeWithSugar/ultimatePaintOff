package org.cws.paintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.cws.paintOff.PaintOff;

public class PlayerAttemptPickupItemListener implements Listener {
    PaintOff instance = PaintOff.getInstance();

    @EventHandler
    public void onPlayerAttemptPickupItemEvent(PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();
        if (instance.queueManager.checkIfInQueue(player)|| instance.gameManager.checkIfInGame(player)) {
            event.setCancelled(true);
        }
    }
}
