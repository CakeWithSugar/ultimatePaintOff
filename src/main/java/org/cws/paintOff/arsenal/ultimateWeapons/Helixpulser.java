package org.cws.paintOff.arsenal.ultimateWeapons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.entity.Snowball;
import net.kyori.adventure.text.Component;
import org.cws.paintOff.PaintOff;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Helixpulser {
    PaintOff instance = PaintOff.getInstance();
    public final String name = "Helixpulser";
    private final int beamLength = 200;
    public final double helixRadius = 3.0;
    private final double helixFrequency = 0.5;
    public final int prepTime = 2;
    public final int time = 6;
    private final double prepParticleWidth = 0.75;
    private final double timeParticleWidth = 0.5;
    private final int damage = 5;

    public void cast(Player player) {
        if (instance.pointsManager.hasEnughUltPoints(player)) {
            launch(player);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 2.0f);
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 0.75f, 0.75f);
            instance.messageManager.sendUltMessage(player,name);
            instance.pointsManager.ultPoint.put(player, 0);
        }
    }

    public void launch(Player player) {
        Location startLoc = player.getLocation().clone();
        int game = instance.gameManager.getGameNumber(player);
        startLoc.add(0,1,0);

        Snowball snowballChild = startLoc.getWorld().spawn(startLoc, Snowball.class);
        Vector drc = new Vector(0,0,0);
        snowballChild.setVelocity(drc);
        Component customName = Component.text(instance.paintManager.getColorCode(instance.paintManager.getColorByPlayer(player,false)) + name);
        snowballChild.customName(customName);
        snowballChild.setVisibleByDefault(false);
        snowballChild.setGravity(false);
        snowballChild.setCustomNameVisible(true);
        Bukkit.getScheduler().runTaskLater(instance, snowballChild::remove, (time) * 20);

        Vector direction = startLoc.getDirection().normalize();

        AtomicBoolean stop = new AtomicBoolean(false);
        AtomicBoolean prep = new AtomicBoolean(false);
        String colorName = instance.paintManager.getColorByPlayer(player,false);

        final int[] rotationCounter = {0};

        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (prep.get()) {
                return;
            }
            rotationCounter[0]++;
            for (double d = 0; d <= beamLength; d += prepParticleWidth) {
                double angle = d * helixFrequency + (rotationCounter[0] * prepParticleWidth);
                Location beamLoc = startLoc.clone().add(direction.clone().multiply(d));

                Vector axis1 = direction.clone().crossProduct(new Vector(1, 0, 0));
                if (axis1.lengthSquared() < 0.01) {
                    axis1 = direction.clone().crossProduct(new Vector(0, 1, 0));
                }
                if (axis1.lengthSquared() < 0.01) {
                    axis1 = direction.clone().crossProduct(new Vector(0, 0, 1));
                }
                axis1.normalize();

                Vector axis2 = direction.clone().crossProduct(axis1).normalize();

                double offsetX = helixRadius * (Math.cos(angle) * axis1.getX() + Math.sin(angle) * axis2.getX());
                double offsetY = helixRadius * (Math.cos(angle) * axis1.getY() + Math.sin(angle) * axis2.getY());
                double offsetZ = helixRadius * (Math.cos(angle) * axis1.getZ() + Math.sin(angle) * axis2.getZ());

                beamLoc.add(offsetX, offsetY, offsetZ);
                instance.paintManager.playColorParticle(colorName, beamLoc, 0.05, 1, 0.05, 1.5f);
            }
        }, 0, 6);
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (stop.get()) {
                return;
            }
            if (prep.get()) {
                rotationCounter[0]++;
                for (double d = 0; d <= beamLength; d += timeParticleWidth) {
                    double angle = d * helixFrequency + (rotationCounter[0] * timeParticleWidth);
                    Location beamLoc = startLoc.clone().add(direction.clone().multiply(d));

                    for (Player p : instance.gameManager.game.get(game)) {
                        if (p.getLocation().distance(beamLoc) <= helixRadius) {
                            instance.damageManager.damagePlayer(p, player, damage);
                        }
                    }

                    Vector axis1 = direction.clone().crossProduct(new Vector(1, 0, 0));
                    if (axis1.lengthSquared() < 0.01) {
                        axis1 = direction.clone().crossProduct(new Vector(0, 1, 0));
                    }
                    if (axis1.lengthSquared() < 0.01) {
                        axis1 = direction.clone().crossProduct(new Vector(0, 0, 1));
                    }
                    axis1.normalize();

                    Vector axis2 = direction.clone().crossProduct(axis1).normalize();

                    // First helix
                    double offsetX1 = helixRadius * (Math.cos(angle) * axis1.getX() + Math.sin(angle) * axis2.getX());
                    double offsetY1 = helixRadius * (Math.cos(angle) * axis1.getY() + Math.sin(angle) * axis2.getY());
                    double offsetZ1 = helixRadius * (Math.cos(angle) * axis1.getZ() + Math.sin(angle) * axis2.getZ());

                    // Second helix (180° rotated)
                    double offsetX2 = helixRadius * (Math.cos(angle + Math.PI) * axis1.getX() + Math.sin(angle + Math.PI) * axis2.getX());
                    double offsetY2 = helixRadius * (Math.cos(angle + Math.PI) * axis1.getY() + Math.sin(angle + Math.PI) * axis2.getY());
                    double offsetZ2 = helixRadius * (Math.cos(angle + Math.PI) * axis1.getZ() + Math.sin(angle + Math.PI) * axis2.getZ());

                    // First helix particle
                    Location beamLoc1 = beamLoc.clone().add(offsetX1, offsetY1, offsetZ1);
                    instance.paintManager.playColorParticle(colorName, beamLoc1, 0.05, 1, 0.05, 1.25f);
                    beamLoc1.getWorld().playSound(beamLoc1, Sound.BLOCK_BEEHIVE_WORK, 0.25f, 1.0f);
                    AtomicReference<Block> block1 = new AtomicReference<>(beamLoc1.getBlock());
                    if (!block1.get().getType().isAir()) {
                        instance.paintManager.setBlock(block1.get().getLocation(), colorName, player, false);
                    }

                    // Second helix particle
                    Location beamLoc2 = beamLoc.clone().add(offsetX2, offsetY2, offsetZ2);
                    instance.paintManager.playColorParticle(colorName, beamLoc2, 0.05, 1, 0.05, 1.25f);
                    beamLoc2.getWorld().playSound(beamLoc2, Sound.BLOCK_BEEHIVE_WORK, 0.25f, 1.0f);
                    AtomicReference<Block> block2 = new AtomicReference<>(beamLoc2.getBlock());
                    if (!block2.get().getType().isAir()) {
                        instance.paintManager.setBlock(block2.get().getLocation(), colorName, player, false);
                    }
                }
            }
        }, 0, 2);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            stop.set(true);
        }, time * 20);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            prep.set(true);
        }, prepTime * 20);
    }
}
