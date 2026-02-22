package org.cws.paintOff.managers;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cws.paintOff.PaintOff;

import java.io.*;
import java.util.*;

public class ArenaManager {
    PaintOff instance = PaintOff.getInstance();
    int resistanceTime = 8;
    Location pos1;
    Location pos2;
    Location spawn;

    public void setup() {
        pos1 = null;
        pos2 = null;
        spawn = null;
    }

    public void clear() {
        pos1 = null;
        pos2 = null;
        spawn = null;
    }

    public void setLocation(Player player, Location location, String name, String arenaName) {
        if (name.equals("pos1") || name.equals("pos2") || name.equals("spawn") || name.equals("lobby") || name.equals("create") || name.equals("delete")) {
            if (name.equals("lobby")) {
                setLobby(player);
                return;
            }
            if (name.equals("pos1")) {
                clear();
                pos1 = location;
                instance.messageManager.sendMessage(player, "pos1 set on: " + location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
                return;
            }

            if (pos1 == null) {
                instance.messageManager.sendError(player, "You must set pos1 first!");
                return;
            }

            if ((name.equals("pos2"))) {
                pos2 = location;
                spawn = null;
                instance.messageManager.sendMessage(player, "pos2 set on: " + location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
                return;
            }

            if (pos2 == null) {
                instance.messageManager.sendError(player, "You must set pos2 first!");
                return;
            }

            if ((name.equals("spawn"))) {
                spawn = location;
                instance.messageManager.sendMessage(player, "Spawnpoint set on: " + location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
                return;
            }

            if (spawn == null) {
                instance.messageManager.sendError(player, "You must set spawn first!");
                return;
            }

            if ((name.equals("create") && arenaName != null)) {
                createArena(player,arenaName);
            } else {
                instance.messageManager.sendError(player, "You must give an arena name to create!");
            }
        } else {
            instance.messageManager.sendError(player, "Usage: /arena <pos1|pos2|spawn|lobby|create [name]|delete [name]>");
        }
    }

    public void deleteFunction(Player player, String arenaName) {
        if (arenaName != null) {
            deleteArena(player,arenaName);
        } else {
            instance.messageManager.sendError(player, "Usage: /poarena <delete [name]>");
        }
    }

    private void createArena(Player player,String arenaName) {
        Location playerPos = player.getLocation();

        File arenaFolder = new File("plugins/PaintOff/Arena" + arenaName);
        if (!arenaFolder.exists()) {
            if (!arenaFolder.mkdirs()) {
                instance.messageManager.sendError(player,"Error while creating the Arena-File!");
                instance.messageManager.sendError(null,"Error while creating the Arena-File!");
                return;
            }
        }
        File file = new File(arenaFolder, "arena.dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.format(Locale.US, "%.2f,%.2f,%d,%d,%d", playerPos.getYaw(), playerPos.getPitch(), playerPos.getBlockX(), playerPos.getBlockY(), playerPos.getBlockZ()));
            writer.newLine();
            writer.write(String.format(Locale.US, "%.2f,%.2f,%d,%d,%d", spawn.getYaw(), spawn.getPitch(), spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ()));
            writer.newLine();

            int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
            int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
            int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
            int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
            int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
            int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        Location blockLoc = new Location(playerPos.getWorld(), x, y, z);
                        Block block = blockLoc.getBlock();
                        if (block.getType() != Material.AIR) {
                        BlockData blockData = block.getBlockData();
                        String blockDataString = blockData.getAsString(true);

                        writer.write(String.format("%d,%d,%d,%s", x, y, z, blockDataString));
                        writer.newLine();
                        }
                    }
                }
            }
            instance.messageManager.sendMessage(player, "Arena created: " + arenaName);
            clear();
        } catch (IOException e) {
            clear();
            throw new RuntimeException(e);
        }
    }

    public void editArena(String arenaName, int queue, boolean destroy) {
        File path = new File("plugins/PaintOff/Arena", arenaName);
        if (!path.exists()) {
            instance.messageManager.sendError(null, "Arena-FOLDER not found!");
            return;
        }

        File file = new File(path, "arena.dat");
        if (!file.exists()) {
            instance.messageManager.sendError(null, "Arena-FILE not found!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();

            String[] originalPos = reader.readLine().split(",");
            if (originalPos.length < 5) {
                instance.messageManager.sendError(null, "Invalid position format!");
                return;
            }

            int originalX = Integer.parseInt(originalPos[2]);
            int originalY = Integer.parseInt(originalPos[3]);
            int originalZ = Integer.parseInt(originalPos[4]);

            // Get Po1 world
            World world = Bukkit.getWorld("Po"+queue);
            if (world == null) {
                instance.messageManager.sendError(null, "World Po"+queue+" not found!");
                return;
            }

            List<String> blockLines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                blockLines.add(line);
            }

            blockLines.sort((line1, line2) -> {
                String[] parts1 = line1.split(",", 4);
                String[] parts2 = line2.split(",", 4);
                if (parts1.length < 4 || parts2.length < 4) return 0;

                int y1 = Integer.parseInt(parts1[1]);
                int y2 = Integer.parseInt(parts2[1]);
                return Integer.compare(y2, y1);
            });

            instance.gameManager.assignColors(queue);
            for (String blockLine : blockLines) {
                String[] parts = blockLine.split(",", 4);
                if (parts.length < 4) {
                    instance.messageManager.sendError(null, "Line has less than 4 values: " + blockLine);
                    continue;
                }

                try {
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    int z = Integer.parseInt(parts[2]);
                    String blockDataStr = parts[3];
                    String blockData4Extra = parts[3];

                    // Calculate corrected coordinates
                    int correctedX = x - originalX;
                    int correctedY = y - originalY + instance.basicValues.spawnHight;
                    int correctedZ = z - originalZ;

                    // Get block using Location
                    Location blockLoc = new Location(world, correctedX, correctedY, correctedZ);
                    Block block = blockLoc.getBlock();

                    // Remove minecraft: prefix if present
                    if (blockDataStr.startsWith("minecraft:")) {
                        blockDataStr = blockDataStr.substring(10);
                    }
                    // Remove everything after first '[' if present
                    int bracketIndex = blockDataStr.indexOf('[');
                    if (bracketIndex != -1) {
                        blockDataStr = blockDataStr.substring(0, bracketIndex);
                    }
                    Material material;
                    BlockData blockData = null;

                    try {
                        // Try to create block data directly from the string
                        blockData = Bukkit.createBlockData(blockData4Extra);
                        material = blockData.getMaterial();

                    } catch (IllegalArgumentException e) {
                        // Fallback to basic material parsing if block data parsing fails
                        String materialName = blockDataStr;
                        if (bracketIndex != -1) {
                            materialName = materialName.substring(0, bracketIndex);
                        }

                        material = Material.matchMaterial(materialName.toUpperCase());
                        if (material == null) {
                            try {
                                int materialId = Integer.parseInt(materialName);
                                material = Material.values()[materialId];
                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                                material = Material.STONE;
                            }
                        }
                    }

                    if (destroy) {
                        blockData = null;
                        material = Material.AIR;
                    }
                    if (material != null) {
                        try {
                            if (blockData != null) {
                                if (blockData.getMaterial() == Material.TINTED_GLASS) {
                                    block.setType(Material.valueOf(instance.gameManager.colorA[queue]+"_STAINED_GLASS"), false);
                                } else if (blockData.getMaterial() == Material.GLASS) {
                                    block.setType(Material.valueOf(instance.gameManager.colorB[queue]+"_STAINED_GLASS"), false);
                                } else {
                                    block.setBlockData(blockData, false);
                                }
                            } else {
                                block.setType(material, false);
                            }
                        } catch (Exception e) {
                            instance.messageManager.sendError(null, "Error pasting arena blocks: " + e.getMessage());
                            block.setType(material, false);
                        }
                    }
                } catch (NumberFormatException e) {
                    instance.messageManager.sendError(null, "Invalid coordinates in line: " + line);
                }
            }
        } catch (Exception e) {
            instance.messageManager.sendError(null, "Error pasting arena blocks: " + e.getMessage());
        }
    }

    public void deleteArena(Player player, String arenaName) {
        File arenaFolder = new File("plugins/PaintOff/Arena", arenaName);
        if (arenaFolder.exists()) {
            try {
                deleteDirectory(arenaFolder);
                instance.messageManager.sendMessage(player,"Arena: " + arenaName + " deleted!");
            } catch (IOException e) {
                instance.messageManager.sendError(player,"Error while deleteing Arena: " + arenaName);
                instance.messageManager.sendError(null,"Error while deleteing Arena: " + arenaName + " | " + e.getMessage());
            }
        } else {
            instance.messageManager.sendError(player,"Arena: " + arenaName + " not found!");
        }
    }

    private void deleteDirectory(File directory) throws IOException {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.setWritable(true);
                    deleteDirectory(file);
                }
            }
        }
        if (!directory.delete()) {
            throw new IOException("Could not delete file: " + directory);
        }
    }

    public void choseArena(int queue) {
        instance.queueManager.arena1[queue] = instance.arenaManager.randomArenaName();
        if (instance.arenaManager.countArenas() < 2) {
            instance.messageManager.sendError(null, "Not enough arenas!");
            instance.queueManager.arena2[queue] = instance.queueManager.arena1[queue];
            return;
        }
        instance.queueManager.arena2[queue] = instance.arenaManager.randomArenaName();
        if (instance.queueManager.arena1[queue].equals(instance.queueManager.arena2[queue])) {
            choseArena(queue);
        }
    }

    public String randomArenaName() {
        File path = new File("plugins/PaintOff/Arena");
        if (!path.exists()) {
            if (!path.mkdirs()) {
                instance.messageManager.sendError(null, "Could not create PO-arenas directory!");
                return null;
            }
            instance.messageManager.sendError(null, "No arenas found! Please create an arena first.");
            return null;
        }

        File[] files = path.listFiles();
        if (files == null || files.length == 0) {
            instance.messageManager.sendError(null, "No arenas found in PO-arenas directory!");
            return null;
        }

        List<String> arenas = getStrings(files);
        if (arenas.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return arenas.get(random.nextInt(arenas.size()));
    }

    public int countArenas() {
        return getStrings(Objects.requireNonNull(new File("plugins/PaintOff/Arena").listFiles())).size();
    }

    private List<String> getStrings(File[] arenaFolders) {
        List<String> arenas = new ArrayList<>();
        if (arenaFolders == null) {
            return arenas;
        }

        for (File arenaFolder : arenaFolders) {
            if (arenaFolder != null && arenaFolder.isDirectory()) {
                File file = new File(arenaFolder, "arena.dat");
                if (file.exists()) {
                    arenas.add(arenaFolder.getName());
                }
            }
        }
        return arenas;
    }

    public void portToArena(Player player, String arenaName) {
        int game = instance.gameManager.getGameNumber(player);
        File arenaFile = new File("plugins/PaintOff/Arena", arenaName + "/arena.dat");

        if (!arenaFile.exists()) {
            instance.messageManager.sendError(player,"Arena: " + arenaName + " not found!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arenaFile))) {
            // Read player position (first line)
            String playerPosLine = reader.readLine();
            if (playerPosLine == null) {
                instance.messageManager.sendError(player,"Arena: " + arenaName + " not found! (File is empty)");
                return;
            }

            // Read spawn position (second line)
            String spawnPosLine = reader.readLine();
            if (spawnPosLine == null) {
                instance.messageManager.sendError(player,"Arena: " + arenaName + " is missing spawn position data");
                return;
            }

            String[] playerPos = playerPosLine.split(",");
            String[] spawnPos = spawnPosLine.split(",");

            if (playerPos.length != 5 || spawnPos.length != 5) {
                instance.messageManager.sendError(player,"Arena: " + arenaName + " has invalid data format");
                return;
            }

            // Parse player position with yaw and pitch
            float yaw = Float.parseFloat(playerPos[0]);
            float pitch = Float.parseFloat(playerPos[1]);
            int x1 = Integer.parseInt(playerPos[2]);
            int y1 = Integer.parseInt(playerPos[3]);
            int z1 = Integer.parseInt(playerPos[4]);

            // Parse spawn position with yaw and pitch
            float spawnYaw = Float.parseFloat(spawnPos[0]);
            float spawnPitch = Float.parseFloat(spawnPos[1]);
            int x2 = Integer.parseInt(spawnPos[2]);
            int y2 = Integer.parseInt(spawnPos[3]);
            int z2 = Integer.parseInt(spawnPos[4]);

            // Get the world
            World world = Bukkit.getWorld("Po" + game);
            if (world == null) {
                instance.messageManager.sendError(player, "World not found! (Po" + game + ")");
                return;
            }

            if (instance.gameManager.teamB.get(game).contains(player)) {
                Location spawnA = new Location(world, 0, instance.basicValues.spawnHight, 0,yaw+180,pitch);
                player.teleport(spawnA);
            }
            if (instance.gameManager.teamA.get(game).contains(player)) {
                int deltaX = x1 - x2;
                int deltaY = y1 - y2;
                int deltaZ = z1 - z2;
                Location spawnB = new Location(world, deltaX, deltaY + instance.basicValues.spawnHight, deltaZ,spawnYaw+180,spawnPitch);
                player.teleport(spawnB);
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * resistanceTime, 1));
        } catch (IOException | NumberFormatException e) {
            instance.messageManager.sendError(player,"Error while teleporting to arena");
            instance.queueManager.leaveQueue(player, false);
            instance.messageManager.sendError(null,"Error while porting to arena: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setLobby(Player player) {
        Location playerPos = player.getLocation();

        File path = new File("plugins/PaintOff/Lobby");
        if (!path.exists()) {
            if (!path.mkdirs()) {
                instance.messageManager.sendError(player,"Error while creating the Arena-File!");
                instance.messageManager.sendError(null,"Error while creating the Arena-File!");
                return;
            }
        }
        File file = new File(path, "lobby.dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(playerPos.getBlockX() + "." + playerPos.getBlockY() + "." + playerPos.getBlockZ());
            writer.newLine();
            instance.messageManager.sendMessage(player,"Lobby set on: " + playerPos.getWorld().getName() + ", " + playerPos.getBlockX() + ", " + playerPos.getBlockY() + ", " + playerPos.getBlockZ());
        }
        catch (IOException e) {
            instance.messageManager.sendError(null,"Error while writing the Arena-File!");
            instance.messageManager.sendError(player,"Error while writing the Arena-File!");
        }
    }
    public Location getLobbySpawn() {
        File lobbyFolder = new File("plugins/PaintOff/Lobby");
        if (!lobbyFolder.exists()) {
            lobbyFolder.mkdirs();
        }

        File lobbyFile = new File(lobbyFolder, "lobby.dat");
        if (!lobbyFile.exists()) {
            instance.messageManager.sendError(null,"Lobby-File not found: " + lobbyFile.getAbsolutePath());
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(lobbyFile))) {
            String firstLine = reader.readLine();
            if (firstLine == null) {
                instance.messageManager.sendError(null,"No coordinates in lobby file");
                return null;
            }

            String[] coordinates = firstLine.split("\\."); // Use dot as separator
            if (coordinates.length != 3) {
                instance.messageManager.sendError(null,"Invalid format in lobby file: " + firstLine);
                return null;
            }

            try {
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                double z = Double.parseDouble(coordinates[2]);

                World world = Bukkit.getWorld("world");
                if (world == null) {
                    instance.messageManager.sendError(null,"World not found");
                    return null;
                }

                return new Location(world, x, y, z);
            } catch (NumberFormatException e) {
                instance.messageManager.sendError(null,"Invalid coordinates in lobby file" + e.getMessage());
                return null;
            }
        } catch (IOException e) {
            instance.messageManager.sendError(null,"Error while reading lobby file" + e.getMessage());
            return null;
        }
    }
}
