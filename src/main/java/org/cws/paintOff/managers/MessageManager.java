package org.cws.paintOff.managers;

import net.kyori.adventure.text.Component;

import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cws.paintOff.PaintOff;

import java.util.HashMap;
import java.util.Map;

public class MessageManager {
    PaintOff instance = PaintOff.getInstance();
    private final Map<Player, Integer> colorOffsets = new HashMap<>();

    public void sendError(Player player, String message) {
        if (player == null) {
            instance.getLogger().severe("PaintOff | " + message);
            return;
        }
        player.sendMessage("§cPaintOff §f| §7" + message);
    }

    public void sendMessage(Player player, String message) {
        if (player == null) {
            instance.getLogger().info("PaintOff | " + message);
            return;
        }
        player.sendMessage("§aPaintOff §f| §7" + message);
    }

    public void sendInfo(Player player, String message) {
        player.sendMessage("§ePaintOff §f| §7" + message);
    }

    public void sendStartMessage(int game) {
        for (Player player : instance.gameManager.game.get(game)) {
            Component main = Component.text(getColoredColor(player, false));
            Component title = Component.text("§7vs " + getColoredColor(player, true));
            Title message = Title.title(main, title);
            player.showTitle(message);
        }
    }

    public void sendUltMessage(Player player,String ultName) {
        for (Player p : instance.gameManager.game.get(instance.gameManager.getGameNumber(player))) {
            p.sendMessage(" §7> " + instance.paintManager.getColorCode(instance.paintManager.getColorByPlayer(player,false)) + ultName);
        }
    }

    public String getColoredColor(Player player, boolean enemy) {
        int queue = instance.gameManager.getGameNumber(player);
        if (instance.gameManager.teamA.get(queue).contains(player) && !enemy) {
            return (instance.paintManager.getColorCode(instance.gameManager.colorA[queue]) + instance.gameManager.colorA[queue]);
        } else if (instance.gameManager.teamB.get(queue).contains(player) && !enemy) {
            return (instance.paintManager.getColorCode(instance.gameManager.colorB[queue]) + instance.gameManager.colorB[queue]);
        }
        if (instance.gameManager.teamA.get(queue).contains(player) && enemy) {
            return (instance.paintManager.getColorCode(instance.gameManager.colorB[queue]) + instance.gameManager.colorB[queue]);
        } else if (instance.gameManager.teamB.get(queue).contains(player) && enemy) {
            return (instance.paintManager.getColorCode(instance.gameManager.colorA[queue]) + instance.gameManager.colorA[queue]);
        }
        return "";
    }

    public void sendHotbarQueueMessage(Player player) {
        int queueNumber = instance.queueManager.getQueueNumber(player);
        player.sendActionBar(Component.text("§7In queue: §e( " + instance.queueManager.queued.get(queueNumber).size() + " / " + instance.basicValues.maxQueueSize + " )"));
    }

    public void sendHotbarGameMessage(Player player, String color,int fuel, int ultpoints) {
        if (instance.pointsManager.hasEnughUltPoints(player)) {
            int colorOffset = colorOffsets.getOrDefault(player, 0);
            colorOffsets.put(player, (colorOffset + 1) % 7); // 7 ist die Anzahl der Regenbogenfarben

            String[] rainbowColors = {"§c", "§6", "§e", "§a", "§b", "§9", "§d"};

            StringBuilder rainbowText = new StringBuilder();
            String ultpointsLabel = "READY (Q)";

            for (int i = 0; i < ultpointsLabel.length(); i++) {
                String rainbowColor = rainbowColors[(i + colorOffset) % rainbowColors.length];
                rainbowText.append(rainbowColor).append(ultpointsLabel.charAt(i));
            }

            String finalText = "§7Fuel: " + color + fuel + " §7| §l"+ rainbowText;

            player.sendActionBar(Component.text(finalText));
            return;
        }
        if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
        player.sendActionBar(Component.text("§7Fuel: " + color + fuel +" §7| §cUltpoints: " + color + ultpoints + " §7/ " + color + instance.arsenalCoordination.getUltPointsByWeaponName(instance.selectionManager.weapon.get(player))));
        } else {
            player.sendActionBar(Component.text("§7Fuel: " + color + fuel +" §7| Ultpoints: " + color + ultpoints + " §7/ " + color + instance.arsenalCoordination.getUltPointsByWeaponName(instance.selectionManager.weapon.get(player))));
        }
    }

    public void sendDeathMessage(Player player, Player attacker, int game) {
        String color;
        String enemyColor;
        if (instance.gameManager.teamA.get(game).contains(player)) {
            color = instance.paintManager.getColorCode(instance.gameManager.colorA[game]);
            enemyColor = instance.paintManager.getColorCode(instance.gameManager.colorB[game]);
        } else {
            color = instance.paintManager.getColorCode(instance.gameManager.colorB[game]);
            enemyColor = instance.paintManager.getColorCode(instance.gameManager.colorA[game]);
        }
        if (attacker != null) {
            instance.pointsManager.addCoins(attacker,10);
            for (Player p : instance.gameManager.game.get(game)) {
                p.sendMessage(enemyColor + attacker.getName() + " §7--> x " + color + "§m" + player.getName());
            }
        } else {
            for (Player p : instance.gameManager.game.get(game)) {
                p.sendMessage(" §7--> x " + color + "§m" + player.getName());
            }
        }
    }

    public void finishSequence(int game, String arena) {
        int countA = instance.stopManager.checkPaintedBlocks(game, arena, true);
        int countB = instance.stopManager.checkPaintedBlocks(game, arena, false);
        String colA = instance.paintManager.getColorCode(instance.gameManager.colorA[game]);
        String colB = instance.paintManager.getColorCode(instance.gameManager.colorB[game]);
        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
            for (Player player : instance.gameManager.game.get(game)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 140, 1));
                if (player != null && player.isOnline()) {
                    player.sendTitle("", colA + "§l> §7> > < < " + colB + "§l<", 0, 20, 20);
                    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.5f, 1.0f);
                }
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                for (Player player : instance.gameManager.game.get(game)) {
                    if (player != null && player.isOnline()) {
                        player.sendTitle("", colA + "§l> >§7 > < " + colB + "§l< <", 0, 20, 20);
                        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.5f, 1.5f);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    for (Player player : instance.gameManager.game.get(game)) {
                        if (player != null && player.isOnline()) {
                            player.sendTitle("", colA + "§l> > > " + colB + "§l< < <", 0, 20, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.5f, 2f);
                        }
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                        if (countA > countB) {
                            for (Player player : instance.gameManager.teamA.get(game)) {
                                player.sendTitle("§7-= " + colA + "§lVictory! §7=-", "", 10, 40, 40);
                                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITH_ITEM, 1.0f, 0.5f);
                                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITH_ITEM, 1.0f, 1.0f);
                                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1.0f, 2f);
                                instance.pointsManager.addCoins(player,50);
                            }
                            for (Player player : instance.gameManager.teamB.get(game)) {
                                player.sendTitle("§7-= " + colB + "§lLost! §7=-", "", 10, 40, 40);
                                player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.1f, 2f);
                                player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_AMBIENT, 0.5f, 0.5f);
                                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 0.5f, 0.5f);
                                instance.pointsManager.addCoins(player,10);
                            }
                        } else if (countB > countA) {
                            for (Player player : instance.gameManager.teamB.get(game)) {
                                player.sendTitle("§7-= " + colB + "§lVictory!" + " §7=-", "", 10, 40, 40);
                                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITH_ITEM, 1.0f, 0.5f);
                                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITH_ITEM, 1.0f, 1.0f);
                                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1.0f, 2f);
                                instance.pointsManager.addCoins(player,50);
                            }
                            for (Player player : instance.gameManager.teamA.get(game)) {
                                player.sendTitle("§7-= " + colA + "§lLost! §7=-", "", 10, 40, 40);
                                player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.1f, 2f);
                                player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_AMBIENT, 0.5f, 0.5f);
                                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 0.5f, 0.5f);
                                instance.pointsManager.addCoins(player,10);
                            }
                        } else {
                            for (Player player : instance.gameManager.game.get(game)) {
                                player.sendTitle("§8-= §7§lDraw! §8=-", "", 10, 40, 40);
                            }
                        }
                        for (Player player : instance.gameManager.game.get(game)) {
                            instance.messageManager.sendInfo(player,colA + "§l" + countA + " §7| " + colB + "§l" + countB);
                        }
                        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> instance.stopManager.endSequence(game, arena),(80));
                    },(15));
                },(15));
            },(15));
        },(15));
    }
}
