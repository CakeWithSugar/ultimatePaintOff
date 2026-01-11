package org.cws.ultimatePaintOff.managers;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    public void endSequence(int queue, String arenaName) {
        World world = instance.getServer().getWorld("Po"+queue);
        for (Snowball snowball : world.getEntitiesByClass(Snowball.class)) {
            snowball.remove();
        }
        if (instance.gameManager.time[queue] != 0) {
            Bukkit.getScheduler().cancelTask(instance.gameManager.time[queue]);
            instance.gameManager.time[queue] = 0;
        }
        if (instance.gameManager.minuets[queue] != 0) {
            Bukkit.getScheduler().cancelTask(instance.gameManager.minuets[queue]);
            instance.gameManager.minuets[queue] = 0;
        }
        if (instance.pointsManager.fuelTimer[queue] != 0) {
            Bukkit.getScheduler().cancelTask(instance.pointsManager.fuelTimer[queue]);
            instance.pointsManager.fuelTimer[queue] = 0;
        }
        List<Player> players = new ArrayList<>(instance.gameManager.game.get(queue));
        for (Player player : players) {
            instance.gameManager.leaveGame(player, false);
        }

        instance.gameManager.game.get(queue).clear();
        instance.paintManager.deleteTeams(queue);
        instance.gameManager.teamA.get(queue).clear();
        instance.gameManager.teamB.get(queue).clear();
        instance.gameManager.arenaName[queue] = null;
        instance.arenaManager.editArena(arenaName, queue, true);
        instance.gameManager.inGame[queue] = false;
    }

    public void startEndingTimer(int game, String arena) {
        instance.gameManager.time[game] = Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
            instance.messageManager.finishSequence(game,arena);
            instance.gameManager.time[game] = 0;
        }, instance.basicValues.maxGameTimeTicks);
    }

    public int checkPaintedBlocks(int game, String arenaName, boolean teamA) {
        World world = Bukkit.getWorld("Po"+game);
        if (world == null) {
            return 0;
        }

        File arenaFolder = new File("PO-arenas/" + arenaName);
        File blocksFile = new File(arenaFolder, "arena.dat");

        int dirtCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(blocksFile))) {
            String firstLine = reader.readLine();
            if (firstLine == null) {
                instance.messageManager.sendError(null, "Ungültige Arena-Datei!" + arenaName);
                return dirtCount;
            }
            String[] referencePos = firstLine.split(",");
            int refX = Integer.parseInt(referencePos[0]);
            int refY = Integer.parseInt(referencePos[1]);
            int refZ = Integer.parseInt(referencePos[2]);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] blockData = line.split(",");
                if (blockData.length < 4) {
                    instance.messageManager.sendError(null, "Ungültige Blockdaten: " + line);
                    continue;
                }

                try {
                    int x = Integer.parseInt(blockData[0]);
                    int y = Integer.parseInt(blockData[1]);
                    int z = Integer.parseInt(blockData[2]);

                    Location pasteLoc = new Location(Bukkit.getWorld("Po"+game),
                            x - refX, y - refY + instance.basicValues.spawnHight, z - refZ);
                    Block block = pasteLoc.getBlock();
                    Block blockAbove = pasteLoc.add(0, 1, 0).getBlock();
                    Material blockType = block.getType();
                    Material blockAboveType = blockAbove.getType();

                    if ((blockType == Material.valueOf(instance.gameManager.colorA[game] + "_WOOL")) && teamA && blockAboveType == Material.AIR) {
                        dirtCount++;
                    }
                    if ((blockType == Material.valueOf(instance.gameManager.colorB[game] + "_WOOL")) && !teamA && blockAboveType == Material.AIR) {
                        dirtCount++;
                    }
                } catch (NumberFormatException e) {
                    instance.messageManager.sendError(null, "Ungültige Koordinaten: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dirtCount;
    }
}
