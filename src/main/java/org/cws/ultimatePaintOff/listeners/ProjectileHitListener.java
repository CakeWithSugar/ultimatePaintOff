package org.cws.ultimatePaintOff.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class ProjectileHitListener implements Listener {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        Player target = (Player) event.getHitEntity();
        Player player = (Player) projectile.getShooter();
        Block block = event.getHitBlock();
        if (player == null) {return;}

        if (projectile instanceof Snowball snowball) {
            if (instance.snowballManager.snowballName.containsKey(snowball)) {
                String name = instance.snowballManager.snowballName.get(snowball);
                if (target != null) {
                    instance.arsenalCoordination.dealDamage(target, player, name);
                    if (name.equals(instance.platzRegen.name)) {
                        instance.platzRegen.phaseOne(snowball, target.getLocation().add(0, 2, 0).getBlock(), (Player) snowball.getShooter());
                    }
                }
                if (block != null) {
                    if (name.equals(instance.platzRegen.name)) {
                        instance.platzRegen.phaseOne(snowball, block, (Player) snowball.getShooter());
                    }
                }
            }
        }
    }
}
