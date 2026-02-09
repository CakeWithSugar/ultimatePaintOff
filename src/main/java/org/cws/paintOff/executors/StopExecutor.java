package org.cws.paintOff.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cws.paintOff.PaintOff;

public class StopExecutor implements CommandExecutor {
    PaintOff instance = PaintOff.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (instance.queueManager.checkIfInQueue(player)) {
                instance.messageManager.sendError(player,"You are already in a started game!");
                return true;
            }
            if (!instance.gameManager.checkIfInGame(player)) {
                instance.messageManager.sendError(player,"You are not in a game!");
                return true;
            }
            int game = instance.gameManager.getGameNumber(player);
            if (game == -1) {
                instance.messageManager.sendError(player, "You are not in a game!" + game);
                return true;
            }
            String arenaName = instance.gameManager.arenaName[game];
            if (arenaName == null) {
                instance.messageManager.sendError(player, "Game not found!");
                return true;
            }
            instance.messageManager.finishSequence(game, arenaName);
            return true;
        }
        return false;
    }
}
