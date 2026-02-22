package org.cws.paintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.cws.paintOff.PaintOff;

public class PlayerQuitListener implements Listener {
    PaintOff instance = PaintOff.getInstance();

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        instance.queueManager.leaveQueue(player,false);
        instance.gameManager.leaveGame(player,false);
        instance.archiveManager.setup(player,true);
    }
}
