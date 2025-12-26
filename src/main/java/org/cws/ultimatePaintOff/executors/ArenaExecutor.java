package org.cws.ultimatePaintOff.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class ArenaExecutor implements CommandExecutor {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                instance.messageManager.sendError(player, "Usage: /arena <pos1|pos2|spawn|lobby|create [name]>");
                return true;
            }
            if (args.length == 2 && args[0].equals("create")) {
                instance.arenaManager.setLocation(player,player.getLocation(), args[0],args[1]);
            } else if (args.length == 2 && args[0].equals("delete")) {
                instance.arenaManager.deleteFunction(player,args[1]);
            } else if (args.length == 1) {
                instance.arenaManager.setLocation(player,player.getLocation(), args[0],null);
            } else {
                instance.messageManager.sendError(player, "Usage: /arena <pos1|pos2|spawn|lobby|create [name]>");
            }
        }
        return false;
    }
}
