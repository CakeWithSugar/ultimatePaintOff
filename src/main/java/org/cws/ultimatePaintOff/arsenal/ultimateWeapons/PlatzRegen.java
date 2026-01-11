package org.cws.ultimatePaintOff.arsenal.ultimateWeapons;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.*;

public class PlatzRegen {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public double upwardMotion = 0.8;
    public String name = "Platzregen";
    public String nameChild = "Regen";
    public int drops = 6;
    public int duration = 16;
    public double speedNegator = 3;
    public int damage = 4;

    public void cast(Player player) {
        if (instance.pointsManager.hasEnughUltPoints(player)) {
            launch(player);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 2.0f);
            instance.messageManager.sendUltMessage(player,name);
            instance.pointsManager.ultPoint.put(player, 0);
        }
    }

    public void launch(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.1f, 2.0f);
        instance.snowballManager.createSnowball(player,null, 1.5, 0.1, name, 0, false, 4, Particle.DUST,false,0,0,0,0,false);
    }

    public void phaseOne(Snowball snowball, Block hitBlock, Player player) {
        float yaw = player.getLocation().getYaw();
        Location loc = snowball.getLocation().add(0, 1, 0);
        Snowball snowballChild = snowball.getWorld().spawn(snowball.getLocation(), Snowball.class);
        Vector direction = snowball.getLocation().getDirection();
        direction.setY(upwardMotion);
        direction.setX(0);
        direction.setZ(0);
        snowballChild.setVelocity(direction);
        Component customName = Component.text(instance.paintManager.getColorCode(instance.paintManager.getColorByPlayer(player,false)) + name);
        snowballChild.customName(customName);
        snowballChild.setCustomNameVisible(true);
        snowballChild.setShooter(snowball.getShooter());
        snowball.getWorld().spawnParticle(Particle.EXPLOSION, loc, 1, 0, 0, 0, 0.005);
        hitBlock.getWorld().playSound(hitBlock.getLocation(), Sound.BLOCK_SLIME_BLOCK_HIT, 1.0f, 2.0f);

        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            Location loc2 = snowballChild.getLocation();
            snowballChild.getWorld().spawnParticle(Particle.WARPED_SPORE, loc2, 10, 0, 0, 0, 0.1);
            snowballChild.getWorld().spawnParticle(Particle.CLOUD, loc2, 3, 0, 0, 0, 0.05);
        }, 0, 1);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            phaseTwo(player, snowballChild.getLocation(), yaw);
            snowballChild.remove();
        }, 20);
    }

    public void phaseTwo(Player player, Location hitLocation, float yaw) {
        Snowball snowballChild = hitLocation.getWorld().spawn(hitLocation, Snowball.class);
        double speed = 0.3;
        double rad = Math.toRadians(yaw);
        Vector direction = new Vector(
                -Math.sin(rad) * (speed / speedNegator),  // X-Komponente mit speedNegator
                0,                                        // Y-Komponente
                Math.cos(rad) * (speed / speedNegator)    // Z-Komponente mit speedNegator
        );

        snowballChild.setVelocity(direction);
        snowballChild.setGravity(false);
        snowballChild.setVisibleByDefault(false);
        snowballChild.setShooter(player);

        hitLocation.getWorld().spawnParticle(Particle.EXPLOSION, hitLocation, 1, 0, 0, 0, 0.005);
        hitLocation.getWorld().playSound(hitLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 5.0f, 2.0f);
        hitLocation.getWorld().playSound(hitLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 2.75f, 1.0f);
        hitLocation.getWorld().playSound(hitLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.5f, 0.5f);

        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            Vector currentDirection = snowballChild.getVelocity();
            if (currentDirection.lengthSquared() > 0) {
                currentDirection.normalize().multiply(speed / speedNegator);
                snowballChild.setVelocity(currentDirection);
            }
        }, 0, 1);

        String colorPara = instance.paintManager.getColorByPlayer(player,false);
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            Location loc2 = snowballChild.getLocation();
            snowballChild.getWorld().spawnParticle(Particle.DRIPPING_WATER, loc2, 5, 3, 0, 3, 0.5);
            snowballChild.getWorld().spawnParticle(Particle.SMOKE, loc2, 75, 3, 0.5, 3, 0.05);
            snowballChild.getWorld().spawnParticle(Particle.CLOUD, loc2, 75, 3, 0.5, 3, 0.05);
            snowballChild.getWorld().spawnParticle(Particle.FIREWORK, loc2, 50, 3, 0.5, 3, 0.05);
            snowballChild.getWorld().spawnParticle(Particle.SNOWFLAKE, loc2, 25, 3, 0.5, 3, 0.05);
            snowballChild.getWorld().spawnParticle(Particle.COMPOSTER, loc2, 1, 3, 0.5, 3, 0.5);
            instance.paintManager.playColorParticle(colorPara,snowballChild.getLocation(),5,40,1.5, 3f);

            List<Location> possibleLocations = new ArrayList<>();
            int radius = 6;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Location loc = loc2.clone().add(x, y, z);
                        if (loc.getBlock().getType() == Material.AIR) {
                            possibleLocations.add(loc);
                        }
                    }
                }
            }

            Collections.shuffle(possibleLocations);
            int numLocations = Math.min(drops, possibleLocations.size());
            List<int[]> excludeCoords = Arrays.asList(
                    new int[]{6, 6},
                    new int[]{5, 5},
                    new int[]{6, 5},
                    new int[]{5, 6},
                    new int[]{3, 6},
                    new int[]{4, 6},
                    new int[]{6, 3},
                    new int[]{6, 4},

                    new int[]{-6, 6},
                    new int[]{-5, 5},
                    new int[]{-6, 5},
                    new int[]{-5, 6},
                    new int[]{-3, 6},
                    new int[]{-4, 6},
                    new int[]{-6, 3},
                    new int[]{-6, 4},

                    new int[]{-6, -6},
                    new int[]{-5, -5},
                    new int[]{-6, -5},
                    new int[]{-5, -6},
                    new int[]{-3, -6},
                    new int[]{-4, -6},
                    new int[]{-6, -3},
                    new int[]{-6, -4},

                    new int[]{6, -6},
                    new int[]{5, -5},
                    new int[]{6, -5},
                    new int[]{5, -6},
                    new int[]{3, -6},
                    new int[]{4, -6},
                    new int[]{6, -3},
                    new int[]{6, -4}
            );

            for (int i = 0; i < numLocations; i++) {
                Location randomLoc = possibleLocations.get(i);
                if (instance.paintManager.shouldExcludeAnyLocation(randomLoc, loc2, excludeCoords)) {
                    continue;
                }
                instance.snowballManager.createSnowball(player,randomLoc,0,0.1,nameChild,0,false,1,null,false,0,0,0,0,false);
            }
        }, 0, 2);


        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            Location loc2 = snowballChild.getLocation();
            snowballChild.getWorld().playSound(loc2, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 2.0f);

        }, 0, 20L *(duration/4));
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            snowballChild.remove();
        }, 20L *duration);
    }
}
