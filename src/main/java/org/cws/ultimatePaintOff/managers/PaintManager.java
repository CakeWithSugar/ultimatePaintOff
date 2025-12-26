package org.cws.ultimatePaintOff.managers;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.*;

public class PaintManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    private final Set<Material> exeptions = new HashSet<>();

    public void startPaintSequence(Snowball snowball, int lengh,String color,boolean ultPoint) {
        final int[] paintTaskId = {-1};
        Player player = (Player) snowball.getShooter();
        paintTaskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            Location loc = snowball.getLocation();
            for (int i = 0; i <= lengh; i++) {
                setBlock(loc.clone().add(0, -i, 0),color,player,ultPoint);
            }

            if (!snowball.isValid()) {
                Bukkit.getScheduler().cancelTask(paintTaskId[0]);
                paintTaskId[0] = -1;
            }
        }, 0L, 1L);
    }

    public void setBlock(Location location, String colorName,Player player, boolean ultPoint) {
        Block block = location.getWorld().getBlockAt(location);
        Block blockAbove = block.getRelative(BlockFace.UP);
        if (paintable(block) && !block.getType().equals((Material.valueOf(colorName + "_WOOL")))) {
            block.setType(Material.valueOf(colorName + "_WOOL"));
            if (ultPoint && blockAbove.getType().equals(Material.AIR)) {
                instance.pointsManager.grantUltPoint(player);
                location.getWorld().playSound(location,Sound.BLOCK_HONEY_BLOCK_SLIDE,0.25f,1.5f);
            }
            playColorParticle(colorName, location, 0.5, 2, 0.5, 2f);
        }
    }

    public String getColorByPlayer(Player player) {
        int queue = instance.gameManager.getGameNumber(player);
        if (instance.gameManager.teamA.get(queue).contains(player)) {
            return instance.gameManager.colorA[queue];
        } else if (instance.gameManager.teamB.get(queue).contains(player)) {
            return instance.gameManager.colorB[queue];
        }
        return "WHITE";
    }

    public String choseRandomColorName() {
        String[] colors;
        if (instance.basicValues.monochrome) {
            colors = instance.basicValues.colorsMonochrome;
        } else {
            colors = instance.basicValues.colors;
        }
        return colors[new Random().nextInt(colors.length)];
    }

    public String getColorCode(String colorName) {
        switch (colorName) {
            case ("WHITE") -> {
                return "§f";
            }
            case ("ORANGE") -> {
                return "§6";
            }
            case ("MAGENTA") -> {
                return "§d";
            }
            case ("LIGHT_BLUE") -> {
                return "§b";
            }
            case ("YELLOW") -> {
                return "§e";
            }
            case ("LIME") -> {
                return "§a";
            }
            case ("GRAY") -> {
                return "§8";
            }
            case ("LIGHT_GRAY") -> {
                return "§7";
            }
            case ("CYAN") -> {
                return "§3";
            }
            case ("PURPLE") -> {
                return "§5";
            }
            case ("BLUE") -> {
                return "§1";
            }
            case ("GREEN") -> {
                return "§2";
            }
            case ("RED") -> {
                return "§4";
            }
            case ("BLACK") -> {
                return "§0";
            }
        }
        return "§f";
    }

    public void setup() {
        exeptions.add(Material.ANDESITE);
        exeptions.add(Material.POLISHED_ANDESITE);
        exeptions.add(Material.DIORITE);
        exeptions.add(Material.POLISHED_DIORITE);
        exeptions.add(Material.GRANITE);
        exeptions.add(Material.POLISHED_GRANITE);
        exeptions.add(Material.DEEPSLATE);
        exeptions.add(Material.COBBLED_DEEPSLATE);
        exeptions.add(Material.CHISELED_DEEPSLATE);
        exeptions.add(Material.POLISHED_DEEPSLATE);
        exeptions.add(Material.TUFF);
        exeptions.add(Material.CHISELED_TUFF);
        exeptions.add(Material.POLISHED_TUFF);
        exeptions.add(Material.PACKED_MUD);
        exeptions.add(Material.SAND);
        exeptions.add(Material.RED_SAND);
        exeptions.add(Material.PRISMARINE);
        exeptions.add(Material.DARK_PRISMARINE);
        exeptions.add(Material.NETHERRACK);
        exeptions.add(Material.PURPUR_PILLAR);
        exeptions.add(Material.QUARTZ_PILLAR);
        exeptions.add(Material.PODZOL);
        exeptions.add(Material.MYCELIUM);
        exeptions.add(Material.GRAVEL);
        exeptions.add(Material.CLAY);
        exeptions.add(Material.CALCITE);
        exeptions.add(Material.OBSIDIAN);
        exeptions.add(Material.SOUL_SOIL);
        exeptions.add(Material.ANCIENT_DEBRIS);
        exeptions.add(Material.MELON);
        exeptions.add(Material.BRICK);
        exeptions.add(Material.NETHER_BRICK);
        exeptions.add(Material.RESIN_BRICK);
        exeptions.add(Material.SCULK);
        exeptions.add(Material.BEDROCK);
        exeptions.add(Material.BARREL);
        exeptions.add(Material.SMOOTH_QUARTZ);
    }

    public boolean paintable(Block block) {
        if (block.getType().name().endsWith("_BLOCK")) {
            return true;
        } else if (block.getType().name().endsWith("_WOOD")) {
            return true;
        } else if (block.getType().name().endsWith("_DIRT")) {
            return true;
        } else if (block.getType().name().endsWith("_PLANKS")) {
            return true;
        } else if (block.getType().name().endsWith("STONE")) {
            return true;
        } else if (block.getType().name().endsWith("_LOG")) {
            return true;
        } else if (block.getType().name().endsWith("TERRACOTTA")) {
            return true;
        } else if (block.getType().name().endsWith("_CONCRETE")) {
            return true;
        } else if (block.getType().name().endsWith("_CONCRETE_POWDER")) {
            return true;
        } else if (block.getType().name().endsWith("BOX")) {
            return true;
        } else if (block.getType().name().endsWith("ICE") || block.getType().name().endsWith("PUMPKIN") || block.getType().name().startsWith("SUSPICIOUS")) {
            return true;
        } else if (block.getType().name().endsWith("_BRICKS")) {
            return true;
        } else if (block.getType().name().endsWith("_STEM")) {
            return true;
        } else if (block.getType().name().endsWith("_HYPHAE")) {
            return true;
        } else if (block.getType().name().endsWith("_TILES")) {
            return true;
        } else if (block.getType().name().endsWith("BASALT") || block.getType().name().endsWith("OBSIDIAN") || block.getType().name().endsWith("NYLIUM")) {
            return true;
        } else if (block.getType().name().endsWith("_COPPER")) {
            return true;
        } else if (block.getType().name().endsWith("_ORE")) {
            return true;
        } else if (block.getType().name().endsWith("_WOOL")) {
            return true;
        } else if (exeptions.contains(block.getType())) {
            return true;
        }
        return false;
    }

    public void playColorParticle(String color, Location loc, double width, int count, double hight,float size) {
        Color particleColor = switch (color) {
            case "RED" -> Color.RED;
            case "ORANGE" -> Color.ORANGE;
            case "YELLOW" -> Color.YELLOW;
            case "LIME" -> Color.LIME;
            case "GREEN" -> Color.GREEN;
            case "LIGHT_BLUE" -> Color.AQUA;
            case "BLUE" -> Color.BLUE;
            case "PURPLE" -> Color.PURPLE;
            case "CYAN" -> Color.TEAL;
            case "BLACK" -> Color.BLACK;
            case "GRAY" -> Color.GRAY;
            case "MAGENTA" -> Color.FUCHSIA;
            case "WHITE" -> Color.WHITE;
            default -> Color.SILVER;
        };

        Particle.DustOptions dustOptions = new Particle.DustOptions(particleColor, size);

        for (int i = 0; i < count; i++) {
            Objects.requireNonNull(loc.getWorld()).spawnParticle(
                    Particle.DUST,
                    loc.getX() + (Math.random() * width * 2 - width),
                    loc.getY() + (Math.random() * hight * 2 - hight),
                    loc.getZ() + (Math.random() * width * 2 - width),
                    1, 0, 0, 0, 0, dustOptions
            );
        }
    }

    public boolean shouldExcludeAnyLocation(Location loc, Location refLoc, List<int[]> excludeCoords) {
        for (int[] coords : excludeCoords) {
            if (shouldExcludeLocation(loc, refLoc, coords[0], coords[1])) { // x und z
                return true;
            }
        }
        return false;
    }

    private boolean shouldExcludeLocation(Location loc, Location refLoc, int x, int z) {
        return loc.getBlockX() == refLoc.getBlockX() + x &&
                loc.getBlockZ() == refLoc.getBlockZ() + z;
    }
}
