package org.cws.ultimatePaintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.cws.ultimatePaintOff.UltimatePaintOff;
import org.bukkit.event.block.Action;

public class PlayerInteractListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null) {
            return;
        }
        if (instance.queueManager.checkIfInQueue(player)|| instance.gameManager.checkIfInGame(player)) {
            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                ItemStack item = event.getItem();
                instance.arsenalCoordination.shootPrimary(player,item);
            }
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
                ItemStack item = event.getItem();
                instance.arsenalCoordination.shootSecondary(player,item);
            }
        }
    }
}
