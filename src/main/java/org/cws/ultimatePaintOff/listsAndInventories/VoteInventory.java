package org.cws.ultimatePaintOff.listsAndInventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class VoteInventory {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    private final String VOTE_TITLE = "§6§l- Votebox -";
    public final Inventory VOTE = Bukkit.createInventory(null, 9, VOTE_TITLE);

    public void setupVoteInventory(int queue) {
        VOTE.setItem(3, instance.itemManager.createVote(instance.queueManager.arena1[queue],instance.queueManager.votes1[queue]));
        VOTE.setItem(5, instance.itemManager.createVote(instance.queueManager.arena2[queue],instance.queueManager.votes2[queue]));
    }
}
