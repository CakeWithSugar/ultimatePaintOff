package org.cws.ultimatePaintOff.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class StartExecutor implements CommandExecutor {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!instance.queueManager.checkIfInQueue(player)) {
                instance.messageManager.sendError(player,"You are not in a queue!");
                return true;
            }
            if (instance.gameManager.checkIfInGame(player)) {
                instance.messageManager.sendError(player,"You are already in a game!");
                return true;
            }
            int queue = instance.queueManager.getQueueNumber(player);
            instance.queueManager.active[queue] = true;
            String arenaName;
            if (instance.queueManager.votes1[queue] > instance.queueManager.votes2[queue]) {
                arenaName = instance.queueManager.arena1[queue];
            } else if (instance.queueManager.votes2[queue] > instance.queueManager.votes1[queue]) {
                arenaName = instance.queueManager.arena2[queue];
            } else {
                arenaName = instance.arenaManager.randomArenaName();
            }
            instance.arenaManager.editArena(arenaName,queue,false);
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                instance.gameManager.startGame(instance.queueManager.getQueueNumber(player), arenaName);
            }, 20);
            return true;
        }
        return false;
    }
}
