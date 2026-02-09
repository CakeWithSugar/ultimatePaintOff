package org.cws.paintOff.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cws.paintOff.PaintOff;

public class QueueExecutor implements CommandExecutor {
    PaintOff instance = PaintOff.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            instance.queueManager.joinQueue(player);
        }
        return false;
    }
}
