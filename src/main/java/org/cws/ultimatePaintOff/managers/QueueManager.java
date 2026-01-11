package org.cws.ultimatePaintOff.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    public Map<Integer, List<Player>> queued = new HashMap<>();
    public Map<Player, Integer> voted = new HashMap<>();
    int[] task;
    public boolean[] active;

    public String[] arena1;
    public String[] arena2;
    public int[] votes1;
    public int[] votes2;

    public void setup() {
        task = new int[instance.basicValues.maxQueues + 1];
        active = new boolean[instance.basicValues.maxQueues + 1];
        votes1 = new int[instance.basicValues.maxQueues + 1];
        votes2 = new int[instance.basicValues.maxQueues + 1];
        arena1 = new String[instance.basicValues.maxQueues + 1];
        arena2 = new String[instance.basicValues.maxQueues + 1];
        for (int i = 1; i <= instance.basicValues.maxQueues; i++) {
            queued.put(i,new ArrayList<>());
        }
    }

    public void joinQueue(Player player) {
        // Control
        if (checkIfInQueue(player) || instance.gameManager.checkIfInGame(player)) {
            instance.messageManager.sendError(player,"You are already in a queue or in a game!");
            return;
        }
        int queue = searchForQueue(player);
        if (queue <= 0) {
            instance.messageManager.sendError(player,"All queues are full or in a game. Try again later!");
            return;
        }
        // Message
        if (queued.get(queue).isEmpty()) {
            instance.arenaManager.choseArena(queue);
        }
        queued.get(queue).add(player);
        voted.put(player,0);
        player.setAllowFlight(false);
        Location lobby = instance.arenaManager.getLobbySpawn();
        instance.arsenalCoordination.setupQueueObjects(player);
        player.teleport(lobby);
        instance.messageManager.sendMessage(player,"Joined queue: " + queue + " | §e(" + queued.get(queue).size() + "/" + instance.basicValues.maxQueueSize + ")");
        for (Player p : queued.get(queue)) {
            instance.messageManager.sendHotbarQueueMessage(p);
        }

        // Start
        if (queued.get(queue).size() >= instance.basicValues.minQueueSize) {
            startSequence(queue);
        }
    }

    public void startSequence(int queue) {
        AtomicInteger i = new AtomicInteger(instance.basicValues.countDown);
        active[queue] = true;
        String arenaName;
        if (votes1[queue] > votes2[queue]) {
            arenaName = arena1[queue];
        } else if (votes2[queue] > votes1[queue]) {
            arenaName = arena2[queue];
        } else {
            arenaName = instance.arenaManager.randomArenaName();
        }
        instance.arenaManager.editArena(arenaName,queue,false);
        task[queue] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            if (i.get() != 0) {
                for (Player p : instance.queueManager.queued.get(queue)) {
                    p.sendTitle("§a§l" + i, "", 10, 40, 40);
                    checkEmpty(queue);
                }
                i.addAndGet(-1);
            } else {
                instance.gameManager.startGame(queue,arenaName);
                cancelTask(queue);
            }
        },0, 20);
    }

    public void leaveQueue(Player player, boolean throughCommand) {
        if (!checkIfInQueue(player)) {
            instance.messageManager.sendError(player,"You are not in a queue!");
            return;
        }
        int queue = getQueueNumber(player);
        queued.get(queue).remove(player);
        instance.inventoryManager.clearInventory(player);
        checkEmpty(queue);
        cancelTask(queue);
        if (voted.containsKey(player)) {
            int vote = voted.get(player);
            if (vote == 1) {
                votes1[queue]--;
            } else if (vote == 2) {
                votes2[queue]--;
            }
            voted.remove(player);
        }
        if (throughCommand) {
            instance.messageManager.sendMessage(player,"Left queue: " + queue);
        }
    }

    public int searchForQueue(Player player) {
        for (int i = 1; i <= instance.basicValues.maxQueues; i++) {
            if ((queued.get(i).size() < instance.basicValues.maxQueueSize) && !active[i] && !instance.gameManager.inGame[i]
            && !queued.get(i).contains(player) && !queued.get(i).isEmpty()) {
                return i;
            }
        }
        for (int i = 1; i <= instance.basicValues.maxQueues; i++) {
            if ((queued.get(i).size() < instance.basicValues.maxQueueSize) && !active[i] && !instance.gameManager.inGame[i]
            && !queued.get(i).contains(player)) {
                return i;
            }
        }
        return -1;
    }
    public int getQueueNumber(Player player) {
        for (int i = 1; i <= instance.basicValues.maxQueues; i++) {
            if (queued.get(i).contains(player)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkIfInQueue(Player player) {
        return getQueueNumber(player) != -1;
    }

    public void checkEmpty(int queue) {
        if (queued.get(queue).isEmpty()) {
            cancelTask(queue);
            queued.put(queue,new ArrayList<>());
        }
    }

    public void cancelTask(int queue) {
        Bukkit.getScheduler().cancelTask(task[queue]);
        active[queue] = false;
    }
}
