package org.cws.ultimatePaintOff.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class PlayerMoveListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (instance.gameManager.checkIfInGame(player)) {
            if (player.getLocation().getBlock().getType() == Material.WATER) {
                instance.damageManager.damagePlayer(player, null, 30);
            }
        }
    }
}
