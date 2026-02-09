package org.cws.paintOff.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.cws.paintOff.PaintOff;

public class PlayerMoveListener implements Listener {
    PaintOff instance = PaintOff.getInstance();

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
