package org.cws.paintOff.arsenal.ultimateWeapons;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cws.paintOff.PaintOff;

public class FokusBooster {
    PaintOff instance = PaintOff.getInstance();
    public final String name = "Fokusbooster";
    public final int duration = 15;
    public final int regenBoost = 4;
    public final int damageBoost = 2;
    public final int markTime = 20;
    public final double markSeconds = markTime/20;

    public void cast(Player player) {
        if (instance.pointsManager.hasEnughUltPoints(player)) {
            launch(player);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 2.0f);
            instance.messageManager.sendUltMessage(player,name);
            instance.pointsManager.ultPoint.put(player, 0);
        }
    }

    public void launch (Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * duration, 0));
        for (Player p : instance.gameManager.teamOfPlayer(player,false)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * duration, 0));
        }
    }
}
