package org.cws.ultimatePaintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class BlockBreakListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (instance.queueManager.checkIfInQueue(player) || instance.gameManager.checkIfInGame(player)) {
            event.setCancelled(true);
        }
    }
}
