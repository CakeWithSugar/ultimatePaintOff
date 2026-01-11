package org.cws.ultimatePaintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class PlayerToggleSneakListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (instance.gameManager.checkIfInGame(player) && !instance.gameManager.sneakTask.containsKey(player)) {
            instance.gameManager.sneakEffects(player);
        }
    }
}
