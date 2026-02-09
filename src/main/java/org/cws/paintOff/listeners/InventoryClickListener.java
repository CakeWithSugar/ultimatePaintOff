package org.cws.paintOff.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.cws.paintOff.PaintOff;

public class InventoryClickListener implements Listener {
    PaintOff instance = PaintOff.getInstance();

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (instance.queueManager.checkIfInQueue(player)|| instance.gameManager.checkIfInGame(player)) {
            event.setCancelled(true);
        }
        if (event.getInventory().equals(instance.arsenalInventory.ARSENAL)) {
            event.setCancelled(true);
            ItemStack item = event.getInventory().getItem(event.getSlot());
            if (item == null) {
                return;
            }
            instance.arsenalCoordination.arsenalSelection(player,item);
            event.getInventory().close();
        }
        if (event.getInventory().equals(instance.arsenalInventory.INFO)) {
            event.setCancelled(true);
        }
        if (event.getInventory().equals(instance.voteInventory.VOTE)) {
            event.setCancelled(true);
            int queue = instance.queueManager.getQueueNumber(player);
            int clicked = event.getSlot();
            if (queue == -1) {
                return;
            }
            if (!instance.queueManager.voted.get(player).equals(0)) {
                return;
            }
            if (clicked == 3) {
                instance.queueManager.votes1[queue]++;
                instance.queueManager.voted.put(player,1);
                instance.messageManager.sendMessage(player,"You voted for: " + instance.queueManager.arena1[queue]);
                player.closeInventory();
            }
            if (clicked == 5) {
                instance.queueManager.votes2[queue]++;
                instance.queueManager.voted.put(player,2);
                instance.messageManager.sendMessage(player,"You voted for: " + instance.queueManager.arena2[queue]);
                player.closeInventory();
            }
        }
        if (event.getInventory().equals(instance.inGameMenu.menu)) {
            event.setCancelled(true);
            int clicked = event.getSlot();
            if (clicked == 8) {
                player.openInventory(instance.arsenalInventory.ARSENAL);
                instance.arsenalCoordination.setupArsenalInventory();
            }
        }
    }
}
