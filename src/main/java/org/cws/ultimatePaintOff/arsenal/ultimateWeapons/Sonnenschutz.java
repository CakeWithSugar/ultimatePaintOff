package org.cws.ultimatePaintOff.arsenal.ultimateWeapons;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Sonnenschutz {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public final String name = "Sonnenschutz";
    private final int reichweite = 10;
    public final double abnahme = 0.15;
    private final double hight = 0.75;

    public void cast(Player player) {
        if (instance.pointsManager.hasEnughUltPoints(player)) {
            launch(player);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 2.0f);
            instance.messageManager.sendUltMessage(player,name);
            instance.pointsManager.ultPoint.put(player, 0);
        }
    }

    public void launch (Player player) {
        Location location = player.getLocation().clone().add(0,2,0);
        Snowball snowballChild = player.getWorld().spawn(location, Snowball.class);
        Vector direction = new Vector(0,hight,0);
        direction.setY(hight);
        direction.setX(0);
        direction.setZ(0);
        snowballChild.setVelocity(direction);
        snowballChild.setShooter(player);
        snowballChild.setGlowing(true);
        player.getWorld().playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 2.0f, 2.0f);
        Location loc = player.getLocation().clone();

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            Location top = snowballChild.getLocation();
            phaseTwo(player, snowballChild, top, loc);
            snowballChild.remove();
        }, 20);
    }

    private void phaseTwo(Player player, Snowball snowball, Location top, Location origin) {
        AtomicReference<Double> distance = new AtomicReference<>((double) reichweite);
        Snowball snowballChild = snowball.getWorld().spawn(snowball.getLocation(), Snowball.class);
        Vector direction = snowball.getVelocity();
        direction.setY(-0.05);
        direction.setX(0);
        direction.setZ(0);
        snowballChild.setVelocity(direction);
        Component customName = Component.text(instance.paintManager.getColorCode(instance.paintManager.getColorByPlayer(player)) + name);
        snowballChild.customName(customName);
        snowballChild.setCustomNameVisible(true);
        snowballChild.setShooter(snowball.getShooter());
        snowballChild.setGlowing(true);
        snowballChild.setGravity(false);
        String color = instance.paintManager.getColorByPlayer(player);
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 0, true, true));
            Location shieldCenter = snowballChild.getLocation();
            double currentRadius = distance.updateAndGet(v -> (v - abnahme));
            List<Player> playersToAffect = instance.gameManager.teamOfPlayer(player);

            for (int i = 0; i < 20; i++) {
                double angle = 2 * Math.PI * i / 20;
                double x = Math.cos(angle) * currentRadius;
                double z = Math.sin(angle) * currentRadius;
                double y = 0;
                Location particleLoc = shieldCenter.clone().add(x, y, z);
                particleLoc.setY(origin.getY());

                // Spawn the particle effect
                Objects.requireNonNull(shieldCenter.getWorld()).spawnParticle(Particle.FLAME,
                        snowballChild.getLocation(), 1, 0, 0, 0, 0.05);
                Objects.requireNonNull(shieldCenter.getWorld()).spawnParticle(Particle.END_ROD,
                        particleLoc, 1, 0, 0, 0, 0.01);
                instance.paintManager.playColorParticle(color, particleLoc, 0.2, 3, 0.2, 1.5f);
                instance.paintManager.playColorParticle(color, snowballChild.getLocation(), 0.1, 1, 0.1, 3.0f);
            }
            for (Player target : playersToAffect) {
                if (target.getWorld().equals(shieldCenter.getWorld())) {
                    Location targetLoc = target.getLocation();
                    Location shieldLoc = shieldCenter.clone();

                    double dx = targetLoc.getX() - shieldLoc.getX();
                    double dz = targetLoc.getZ() - shieldLoc.getZ();
                    double distanceSquared = dx * dx + dz * dz;

                    if (distanceSquared <= currentRadius * currentRadius) {
                        if (Math.abs(targetLoc.getY() - origin.getY()) <= 2.0) {
                            target.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20, 0, true, false));
                        }
                    }
                }
            }
            if (currentRadius <= 1) {
                Objects.requireNonNull(shieldCenter.getWorld()).spawnParticle(
                        Particle.FIREWORK, snowballChild.getLocation(), 40, 0, 0, 0, 0.1);
                Objects.requireNonNull(shieldCenter.getWorld()).spawnParticle(
                        Particle.END_ROD, snowballChild.getLocation(), 120, 0, 0, 0, 0.5);
                top.getWorld().playSound(top, Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 2.0f, 0.5f);
                top.getWorld().playSound(top, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, 4.0f, 1.0f);
                snowballChild.remove();
            }
        }, 0, 10);

        Objects.requireNonNull(snowballChild.getWorld()).spawnParticle(
                Particle.END_ROD, snowballChild.getLocation(), 60, 0, 0, 0, 0.5);
        top.getWorld().playSound(top, Sound.ENTITY_BLAZE_BURN, 2.0f, 0.5f);
        top.getWorld().playSound(top, Sound.ENTITY_BLAZE_SHOOT, 1.0f, 2.0f);
    }
}
