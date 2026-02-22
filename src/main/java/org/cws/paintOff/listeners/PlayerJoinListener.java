package org.cws.paintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cws.paintOff.PaintOff;

public class PlayerJoinListener implements Listener {
    PaintOff instance = PaintOff.getInstance();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        instance.queueManager.joinQueue(player);
        instance.archiveManager.setup(player,false);
    }
}
