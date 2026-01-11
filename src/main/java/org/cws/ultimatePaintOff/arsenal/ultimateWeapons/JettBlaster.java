package org.cws.ultimatePaintOff.arsenal.ultimateWeapons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.HashMap;
import java.util.Map;

public class JettBlaster {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final String name = "Jettblaster";
    public Map<Player, Integer> inPhase = new HashMap<>();
    public Map<Player, Location> spawnLocation = new HashMap<>();

    public final double hight = 5;
    public final int duration = 15;
    private final double moveDivider = 5;

    public final int damage = 3;
    public final int cooldown = 15;

    public void cast(Player player) {
        if (instance.pointsManager.hasEnughUltPoints(player)) {
            spawnLocation.put(player,player.getLocation());
            launch(player);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 2.0f);
            instance.messageManager.sendUltMessage(player,name);
            instance.pointsManager.ultPoint.put(player, 0);
        }
    }

    public void launch(Player player) {
        player.setAllowFlight(true);
        int[] task = new int[1];
        String color = instance.paintManager.getColorByPlayer(player,false);

        task[0] = Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (!player.isOnline() || !instance.gameManager.game.get(instance.gameManager.getGameNumber(player)).contains(player)) {
                end(player, true);
                return;
            }
            Location playerLoc = player.getLocation();
            Location groundCheck = playerLoc.clone();
            Vector direction = player.getLocation().getDirection().normalize();

            groundCheck.setY(0);
            double groundY = groundCheck.getWorld().getHighestBlockYAt(groundCheck);
            double distanceToGround = playerLoc.getY() - groundY;

            if ((distanceToGround < hight) && !player.isSneaking()) {
                double boost = 1.0 - (distanceToGround / hight);
                player.setVelocity(new Vector(direction.getX()/moveDivider, 0.1 + (boost * 0.4), direction.getZ()/moveDivider));
            }
            Location particleLoc = player.getLocation().clone().subtract(0, 0.5, 0);
            player.getWorld().spawnParticle(Particle.END_ROD, particleLoc, 1, 0.25, 0.1, 0.25, 0.01);
            instance.paintManager.playColorParticle(color, playerLoc, 0.5,1,1, 4f);
            instance.paintManager.playColorParticle(color, spawnLocation.get(player), 0.5,1,2, 3f);
        }, 0, 1).getTaskId();
        inPhase.put(player, task[0]);
        end(player, false);
    }

    public void end(Player player, boolean directly) {
        if (!directly && instance.gameManager.game.get(instance.gameManager.getGameNumber(player)).contains(player)) {
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                if (inPhase.containsKey(player)) {
                    Bukkit.getScheduler().cancelTask(inPhase.get(player));
                    player.teleport(spawnLocation.get(player));
                    player.setAllowFlight(false);
                    inPhase.remove(player);
                    spawnLocation.remove(player);
                }
            }, 20 * duration).getTaskId();
        } else {
            Bukkit.getScheduler().cancelTask(inPhase.get(player));
            player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation(), 10, 0, 0.5, 0, 0.1);
            player.teleport(spawnLocation.get(player));
            player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20, 0, true, true));
            player.setAllowFlight(false);
            inPhase.remove(player);
            spawnLocation.remove(player);
        }
    }

    public void shoot(Player player) {
        final int radius = 1;
        final double speed = 1.5;
        final int destructionTime = 40;
        final boolean glowing = false;
        final int paintLength = 15;
        final Particle trailParticle = Particle.END_ROD;
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.1f, 3.0f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.1f, 3.0f);
        instance.snowballManager.createSnowball(player,null,speed,0,name,destructionTime,glowing,paintLength,trailParticle,false,cooldown,radius,damage,0,false);
    }
}
