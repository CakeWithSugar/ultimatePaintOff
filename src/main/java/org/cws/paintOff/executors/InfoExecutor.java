package org.cws.paintOff.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cws.paintOff.PaintOff;

public class InfoExecutor implements CommandExecutor {
    PaintOff instance = PaintOff.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            int queue = instance.queueManager.getQueueNumber(player);
            int game = instance.gameManager.getGameNumber(player);
            if (queue != -1) {
                instance.messageManager.sendMessage(player, "You are in queue: " + queue);
                instance.messageManager.sendMessage(player,"Players: " + instance.queueManager.queued.get(queue).size());
            } else {
                instance.messageManager.sendError(player, "You are not in a queue!");
            }
            if (game != -1) {
                instance.messageManager.sendMessage(player, "You are in game: " + game);
                instance.messageManager.sendMessage(player,"Players: " + instance.gameManager.game.get(game).size());
            } else {
                instance.messageManager.sendError(player, "You are not in a game!");
            }
        }
        return false;
    }
}
