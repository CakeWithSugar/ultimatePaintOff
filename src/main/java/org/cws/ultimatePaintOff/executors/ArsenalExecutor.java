package org.cws.ultimatePaintOff.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class ArsenalExecutor implements CommandExecutor {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            player.openInventory(instance.arsenalInventory.ARSENAL);
            instance.arsenalCoordination.setupArsenalInventory();
        }
        return false;
    }
}
