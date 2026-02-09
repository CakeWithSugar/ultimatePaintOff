package org.cws.paintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.cws.paintOff.PaintOff;

public class PlayerToggleSneakListener implements Listener {
    PaintOff instance = PaintOff.getInstance();

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (instance.gameManager.checkIfInGame(player) && !instance.gameManager.sneakTask.containsKey(player)) {
            instance.gameManager.sneakEffects(player);
        }
    }
}
