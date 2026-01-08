package org.cws.ultimatePaintOff.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SnowballManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final Map<Snowball,String> snowballName = new HashMap<>();

    public void createSnowball(Player player, Location spawnLocation, double speedMultiplier, double gravityLevel, String name, int destructionTime, boolean glowing, int paintLength, Particle trailParticle, boolean ultPoint, int coolDown, int explosionRadius, int explosionDamage, float yawOffset, boolean randomizeYaw) {
        String color = instance.paintManager.getColorByPlayer(player);
        Snowball snowball;

        if (randomizeYaw && yawOffset != 0) {
            Random random = new Random();
            yawOffset = random.nextFloat(2 * yawOffset + 1) - yawOffset;
        }
        float yaw = player.getLocation().getYaw() + yawOffset;
        float pitch = player.getLocation().getPitch();

        double radianYaw = Math.toRadians(yaw);
        double radianPitch = Math.toRadians(pitch);

        double x = -Math.sin(radianYaw) * Math.cos(radianPitch);
        double y = -Math.sin(radianPitch);
        double z = Math.cos(radianYaw) * Math.cos(radianPitch);

        Vector direction = new Vector(x, y, z).normalize().multiply(speedMultiplier);
        if (spawnLocation != null) {
            snowball = spawnLocation.getWorld().spawn(spawnLocation, Snowball.class);
            snowball.setVelocity(direction);
        } else {
            snowball = player.launchProjectile(Snowball.class);
            snowball.setVelocity(direction);
        }

        if (gravityLevel == 0) {
            snowball.setGravity(false);
        } else {
            applyGravity(snowball,gravityLevel);
        }

        if (trailParticle != null) {
            applyTrail(snowball, trailParticle, color);
        }
        if (coolDown > 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, coolDown, 1, true, false), true);
        }
        snowball.setGlowing(glowing);
        snowball.setShooter(player);
        snowballName.put(snowball,name);
        if (paintLength > 0) {
            instance.paintManager.startPaintSequence(snowball, paintLength,color,ultPoint);
        }
        setTimer(snowball, destructionTime,explosionRadius,explosionDamage,ultPoint);
    }

    public void applyGravity(Snowball snowball,double gravityLevel) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            if (!snowball.isValid() || snowball.isDead()) {
                removeSnowball(snowball);
                return;
            }
            snowball.setVelocity(snowball.getVelocity().add(new Vector(0, -gravityLevel, 0)));
        },0, 1);
    }

    public void explode(Block hit,int radius, Player player, boolean ultPoint, int damage) {
        String color = instance.paintManager.getColorByPlayer(player);
        int game = instance.gameManager.getGameNumber(player);
        hit.getWorld().playSound(hit.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.1f, 3.0f);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block targetBlock = hit.getRelative(x, y, z);
                    Location targetLocation = targetBlock.getLocation();
                    instance.paintManager.setBlock(targetLocation,color,player,ultPoint);

                    for (Player p : instance.gameManager.game.get(game)) {
                        if (p.getWorld().equals(hit.getWorld()) &&
                                p.getLocation().distanceSquared(targetLocation) <= 1) {
                            instance.damageManager.damagePlayer(p, player, damage);
                        }
                    }
                }
            }
        }
    }

    public void applyTrail(Snowball snowball, Particle particle, String color) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            if (!snowball.isValid() || snowball.isDead()) {
                removeSnowball(snowball);
                return;
            }
            if (particle == Particle.REDSTONE) {
                instance.paintManager.playColorParticle(color, snowball.getLocation(), 0.1, 1, 0.1, 1.0f);
            } else {
                snowball.getWorld().spawnParticle(particle, snowball.getLocation(), 1, 0, 0, 0, 0.01);
            }
        },0, 1);
    }

    public void setTimer(Snowball snowball,int time, int radius, int damage, boolean ultPoint) {
        if (time != 0) {
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                removeSnowball(snowball);
            }, time);
        }
        if (radius > 0) {
            int[] task = new int[1];
            task[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
                if (!snowball.isValid() || snowball.isDead()) {
                    Player player = (Player) snowball.getShooter();
                    explode(snowball.getLocation().getBlock(),radius,player,ultPoint,damage);
                    Bukkit.getScheduler().cancelTask(task[0]);
                }
            },0, 1);
        }
    }

    public void removeSnowball(Snowball snowball) {
        snowballName.remove(snowball);
        snowball.remove();
    }
}
