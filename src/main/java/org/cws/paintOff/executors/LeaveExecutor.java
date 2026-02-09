package org.cws.paintOff.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cws.paintOff.PaintOff;

public class LeaveExecutor implements CommandExecutor {
    PaintOff instance = PaintOff.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (instance.queueManager.checkIfInQueue(player)) {
                instance.queueManager.leaveQueue(player, true);
            } else if (instance.gameManager.checkIfInGame(player)) {
                instance.gameManager.leaveGame(player, true);
            } else {
                instance.messageManager.sendError(player,"You are not in a Queue or in a Game!");
            }
        }
        return false;
    }
}
