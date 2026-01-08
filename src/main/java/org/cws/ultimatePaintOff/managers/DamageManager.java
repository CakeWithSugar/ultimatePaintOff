package org.cws.ultimatePaintOff.managers;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class DamageManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    public void damagePlayer(Player player, Player attacker, int damage) {
        int game = instance.gameManager.getGameNumber(player);
        if ((instance.gameManager.teamA.get(game).contains(player) && instance.gameManager.teamA.get(game).contains(attacker))
                || (instance.gameManager.teamB.get(game).contains(player) && instance.gameManager.teamB.get(game).contains(attacker))
                || player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            return;
        }

        if (attacker != null) {
            if (attacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                damage += instance.fokusBooster.damageBoost;
            }
        }
        if (player.getHealth() - damage <= 0) {
            death(player, attacker);
        } else {
            player.setHealth(player.getHealth() - damage);
            if (attacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * instance.fokusBooster.markTime, 0));
            }
        }
    }

    public void death(Player player, Player attacker) {
        int game = instance.gameManager.getGameNumber(player);
        if (instance.jettBlaster.inPhase.containsKey(player)) {
            instance.jettBlaster.end(player, player.getLocation(), true);
        }
        for (PotionEffect type : player.getActivePotionEffects()) {
            player.removePotionEffect(type.getType());
        }
        instance.arenaManager.portToArena(player, instance.gameManager.arenaName[game]);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        instance.messageManager.sendDeathMessage(player, attacker, game);
    }
}
