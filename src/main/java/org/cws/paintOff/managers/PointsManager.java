package org.cws.paintOff.managers;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.cws.paintOff.PaintOff;

import java.util.HashMap;
import java.util.Map;

public class PointsManager {
    PaintOff instance = PaintOff.getInstance();
    public Map<Player, Integer> fuel = new HashMap<>();
    public Map<Player, Integer> ultPoint = new HashMap<>();
    public Map<Player, Integer> coins = new HashMap<>();
    int[] fuelTimer;

    public void setup() {
        fuelTimer = new int[instance.basicValues.maxQueues+1];
    }

    public void fuelTimer(int queue) {
        fuelTimer[queue] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            for (Player player : instance.gameManager.game.get(queue)) {
                fuelAmount(player, 2);
                instance.scoreManager.updateScore(player);
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    fuelAmount(player, 20);
                }
                if (player.hasPotionEffect(PotionEffectType.STRENGTH)) {
                    fuelAmount(player, instance.fokusBooster.regenBoost);
                }
                if (player.hasPotionEffect(PotionEffectType.RESISTANCE)) {
                    player.getLocation().getWorld().spawnParticle(Particle.END_ROD, player.getLocation().add(0, 0, 0), 5, 0.25, 0.25, 0.25, 0);
                }
                if (instance.gameManager.teamA.get(queue).contains(player)) {
                    instance.messageManager.sendHotbarGameMessage(player, instance.paintManager.getColorCode(instance.gameManager.colorA[queue]), fuel.get(player), ultPoint.get(player));
                } else {
                    instance.messageManager.sendHotbarGameMessage(player, instance.paintManager.getColorCode(instance.gameManager.colorB[queue]), fuel.get(player), ultPoint.get(player));
                }
            }
        }, 0, instance.basicValues.refuelTicks);
    }

    public void fuelAmount(Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            if (fuel.get(player) < instance.basicValues.maxFuel) {
                fuel.put(player, fuel.get(player) + 1);
            }
        }

    }

    public boolean hasEnughFuel(Player player, int requiredFuel) {
        return fuel.get(player) >= requiredFuel;
    }

    public void grantUltPoint(Player player) {
        if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
            return;
        }
        int neededUltPoints = instance.arsenalCoordination.getUltPointsByWeaponName(instance.selectionManager.weapon.get(player));
        if (ultPoint.get(player) < neededUltPoints) {
            ultPoint.put(player, ultPoint.get(player) + 1);
            if (ultPoint.get(player) == neededUltPoints) {
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 2f);
            }
        }
    }

    public boolean hasEnughUltPoints(Player player) {
        int neededUltPoints = instance.arsenalCoordination.getUltPointsByWeaponName(instance.selectionManager.weapon.get(player));
        return ultPoint.get(player) == neededUltPoints;
    }

    public void resetPoints(Player player) {
        ultPoint.remove(player);
        fuel.remove(player);
    }

    // Coins

    public void addCoins(Player player,int amount){
        if (!coins.containsKey(player)) {
            coins.put(player, amount);
        } else {
            coins.put(player,(coins.get(player)+amount));
        }
    }

    public void removeCoins(Player player,int amount){
        if ((coins.get(player)-amount) <= coins.get(player) && !((coins.get(player)-amount) < 0)) {
            coins.put(player,(coins.get(player)-amount));
        } else {
            instance.messageManager.sendError(player,"Not enugh coins!");
        }
    }
}
