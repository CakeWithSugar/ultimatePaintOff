package org.cws.ultimatePaintOff.managers;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public Map<Integer, List<Player>> game = new HashMap<>();
    public Map<Integer, List<Player>> teamA = new HashMap<>();
    public Map<Integer, List<Player>> teamB = new HashMap<>();
    public Map<Player, Integer> sneakTask = new HashMap<>();

    int[] minuets;
    int[] time;
    public String[] colorA;
    public String[] colorB;
    public String[] arenaName;
    boolean[] inGame;

    public void setup() {
        colorA = new String[instance.basicValues.maxQueues +1];
        colorB = new String[instance.basicValues.maxQueues +1];
        time = new int[instance.basicValues.maxQueues +1];
        arenaName = new String[instance.basicValues.maxQueues+1];
        minuets = new int[instance.basicValues.maxQueues+1];
        inGame = new boolean[instance.basicValues.maxQueues+1];
        for (int i = 1; i <= instance.basicValues.maxQueues+1; i++) {
            game.put(i,new ArrayList<>());
            teamA.put(i,new ArrayList<>());
            teamB.put(i,new ArrayList<>());
        }
    }

    public void startGame(int queue, String arenaName) {
        inGame[queue] = true;
        instance.queueManager.checkEmpty(queue);
        if (instance.queueManager.queued.get(queue).isEmpty()) {inGame[queue] = false; return;}

        joinGameAndLeaveQueue(queue);
        if (arenaName == null) {
            instance.stopManager.endSequence(queue, arenaName);
            return;
        }
        this.arenaName[queue] = arenaName;
        setGameRules("Po"+queue);
        assignTeams(queue);
        minuetTimer(queue);
        instance.messageManager.sendStartMessage(queue);
        instance.stopManager.startEndingTimer(queue, arenaName);
        instance.paintManager.setTeamPainted(queue);

        for (Player player : game.get(queue)) {
            instance.inventoryManager.clearInventory(player);
            instance.inventoryManager.getPrimaryItem(player);
            instance.pointsManager.fuel.put(player, 150);
            instance.pointsManager.ultPoint.put(player, 0);

            instance.messageManager.sendInfo(player, "Arena: §a" + arenaName);
            instance.arsenalCoordination.dressUp(player,false);
            instance.arenaManager.portToArena(player, instance.gameManager.arenaName[queue]);
            instance.scoreManager.updateScore(player);
        }
        instance.pointsManager.fuelTimer(queue);
        setColoredName(queue);
    }

    public void assignColors(int queue) {
        colorA[queue] = instance.paintManager.choseRandomColorName();
        colorB[queue] = instance.paintManager.choseRandomColorName();
        while (colorA[queue].equals(colorB[queue])) {
            assignColors(queue);
        }
    }

    private void assignTeams(int queue) {
        List<Player> players = new ArrayList<>(game.get(queue));
        for (int i = 0; i < players.size(); i++) {
            if (i % 2 == 0) {
                teamA.get(queue).add(players.get(i));
            } else {
                teamB.get(queue).add(players.get(i));
            }
        }
    }

    public void joinGameAndLeaveQueue(int queue) {
        List<Player> playersToProcess = new ArrayList<>(instance.queueManager.queued.get(queue));
        for (Player player : playersToProcess) {
            instance.queueManager.leaveQueue(player, false);
            game.get(queue).add(player);
        }
    }

    public void leaveGame(Player player, boolean throughCommand) {
        if (!checkIfInGame(player)) {
            instance.messageManager.sendError(player,"You are not in a game!");
            return;
        }
        int game = getGameNumber(player);
        this.game.get(game).remove(player);
        instance.inventoryManager.clearInventory(player);
        instance.pointsManager.resetPoints(player);
        if (checkEmpty(game)) {
            instance.stopManager.endSequence(game, arenaName[game]);
        }
        for (PotionEffect type : player.getActivePotionEffects()) {
            player.removePotionEffect(type.getType());
        }
        resetName(player);
        player.setAllowFlight(false);
        Location lobby = instance.arenaManager.getLobbySpawn();
        player.teleport(lobby);
        instance.scoreManager.removeScoreboard(player);
        if (throughCommand) {
            instance.messageManager.sendMessage(player, "Left game: " + game);
        } else {
            instance.queueManager.joinQueue(player);
        }
    }

    public int getGameNumber(Player player) {
        for (int i = 1; i <= instance.basicValues.maxQueues; i++) {
            if (game.get(i).contains(player)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkIfInGame(Player player) {
        return getGameNumber(player) != -1;
    }

    private boolean checkEmpty(int queue) {
        return game.get(queue).isEmpty();
    }

    private void minuetTimer(int queue) {
        AtomicInteger min = new AtomicInteger(instance.basicValues.maxGameTime);
        minuets[queue] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            for (Player player : instance.gameManager.game.get(queue)) {
                if (min.get() > 0) {
                    instance.messageManager.sendInfo(player, "§a" + min + "§7 Minuets left");
                    instance.scoreManager.updateScore(player);
                }
            }
            min.getAndDecrement();
        },0, 20*60);
    }


    public void sneakEffects(Player player) {
        int game = instance.gameManager.getGameNumber(player);
        player.playSound(player.getLocation(), Sound.ENTITY_AXOLOTL_SPLASH, 0.5f, 1.0f);

        sneakTask.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            if (!player.isSneaking()) {
                removeSneakEffects(player);
                Bukkit.getScheduler().cancelTask(sneakTask.get(player));
                sneakTask.remove(player);
                return;
            }

            Material type = player.getLocation().add(0,-1,0).getBlock().getType();
            if ((type == Material.valueOf(colorA[game] + "_WOOL") && teamA.get(game).contains(player)) ||
                    (type == Material.valueOf(colorB[game] + "_WOOL") && teamB.get(game).contains(player))) {
                addSneakEffects(player);
            } else {
                removeSneakEffects(player);
            }
        }, 0, 1));
    }

    public void removeSneakEffects(Player player) {
        player.getInventory().setHeldItemSlot(1);
        instance.arsenalCoordination.dressUp(player,false);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.removePotionEffect(PotionEffectType.SPEED);
    }

    public void addSneakEffects(Player player) {
        player.getInventory().setHeldItemSlot(3);
        instance.arsenalCoordination.dressUp(player,true);
        player.setSaturation(20);
        player.setFoodLevel(20);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 19, true, false), true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20, 1, true, false), true);
    }

    public void setGameRules(String string) {
        World world = instance.getServer().getWorld(string);
        world.setGameRule(GameRules.NATURAL_HEALTH_REGENERATION, false);
        world.setGameRule(GameRules.SHOW_ADVANCEMENT_MESSAGES, false);
        world.setGameRule(GameRules.ADVANCE_TIME, false);
        world.setGameRule(GameRules.ADVANCE_WEATHER, false);
        world.setGameRule(GameRules.SPAWN_MOBS, false);
    }

    public void setColoredName(int queue) {
        String colorCodeA = instance.paintManager.getColorCode(instance.gameManager.colorA[queue]);
        String colorCodeB = instance.paintManager.getColorCode(instance.gameManager.colorB[queue]);

        for (Player player : instance.gameManager.teamA.get(queue)) {
            Component name = Component.text(colorCodeA + player.getName());
            player.displayName(name);
            player.playerListName(name);
            player.customName(name);
            player.setCustomNameVisible(true);
        }
        for (Player player : instance.gameManager.teamB.get(queue)) {
            Component name = Component.text(colorCodeB + player.getName());
            player.displayName(name);
            player.playerListName(name);
            player.customName(name);
            player.setCustomNameVisible(true);
        }
    }

    public void resetName(Player player) {
        Component defaultName = Component.text(player.getName());
        player.displayName(defaultName);
        player.playerListName(defaultName);
        player.customName(null);
        player.setCustomNameVisible(false);
    }

    public List<Player> teamOfPlayer(Player player,boolean enemy) {
        if (teamA.get(getGameNumber(player)).contains(player) && !enemy) {
            return teamA.get(getGameNumber(player));
        } else {
            return teamB.get(getGameNumber(player));
        }
    }
}
