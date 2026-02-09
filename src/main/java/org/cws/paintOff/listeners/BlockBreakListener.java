package org.cws.paintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.cws.paintOff.PaintOff;

public class BlockBreakListener implements Listener {
    PaintOff instance = PaintOff.getInstance();

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (instance.queueManager.checkIfInQueue(player) || instance.gameManager.checkIfInGame(player)) {
            event.setCancelled(true);
        }
    }
}
