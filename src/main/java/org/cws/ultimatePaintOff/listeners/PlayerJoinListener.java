package org.cws.ultimatePaintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class PlayerJoinListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        instance.queueManager.joinQueue(player);
    }
}
