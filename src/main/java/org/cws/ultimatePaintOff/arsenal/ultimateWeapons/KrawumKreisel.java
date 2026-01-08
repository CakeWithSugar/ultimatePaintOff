package org.cws.ultimatePaintOff.arsenal.ultimateWeapons;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import javax.annotation.Nullable;

public class KrawumKreisel {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public double upwardMotion = 0.2;
    public String name = "Krawumkreisel";
    public String nameChild = "Krawumsel";
    public int duration = 120;
    public int damage = 4;

    public void cast(Player player) {
        if (instance.pointsManager.hasEnughUltPoints(player)) {
            launch(player);
            instance.messageManager.sendUltMessage(player,name);
            instance.pointsManager.ultPoint.put(player, 0);
        }
    }

    public void launch(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.1f, 2.0f);
        instance.snowballManager.createSnowball(player,null, 1.5, 0.1, name, 0, false, 4, Particle.REDSTONE,false,0,0,0,0,false);
    }

    public void phaseOne(Snowball snowball, Block hitBlock,Player player) {
        Location loc = snowball.getLocation().add(0, 1, 0);
        Snowball snowballChild = snowball.getWorld().spawn(snowball.getLocation(), Snowball.class);
        Vector direction = snowball.getLocation().getDirection();
        direction.setY(upwardMotion);
        direction.setX(0);
        direction.setZ(0);
        snowballChild.setVelocity(direction);
        Component customName = Component.text(instance.paintManager.getColorCode(instance.paintManager.getColorByPlayer(player)) + name);
        snowballChild.customName(customName);
        snowballChild.setCustomNameVisible(true);
        snowballChild.setGravity(false);
        snowballChild.setGlowing(true);
        snowballChild.setShooter(snowball.getShooter());
        snowball.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1, 0, 0, 0, 0.005);
        hitBlock.getWorld().playSound(hitBlock.getLocation(), Sound.BLOCK_SLIME_BLOCK_HIT, 1.0f, 2.0f);

        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            instance.paintManager.playColorParticle(instance.paintManager.getColorByPlayer(player),snowballChild.getLocation(),1,1,1,2f);
            snowballChild.getWorld().spawnParticle(Particle.CRIMSON_SPORE, snowballChild.getLocation(), 10, 0, 0, 0, 0.1);
            instance.snowballManager.createSnowball(player,snowballChild.getLocation(), 0.5,0.1,nameChild,40,false,5,Particle.ELECTRIC_SPARK,false,0,1,6,360,true);
        }, 0, 2);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (snowballChild.isDead() || !snowballChild.isValid()) {
                return;
            }
            snowballChild.getWorld().spawnParticle(Particle.END_ROD, snowballChild.getLocation(), 20, 0, 0, 0, 0.1);
            snowballChild.remove();
        }, duration);
    }
}
