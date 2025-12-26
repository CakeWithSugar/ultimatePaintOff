package org.cws.ultimatePaintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class BlockPlaceListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (instance.queueManager.checkIfInQueue(player) || instance.gameManager.checkIfInGame(player)) {
            event.setCancelled(true);
        }
    }
}
