package org.cws.ultimatePaintOff.listsAndInventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class InGameMenu {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    private final String MENU_TITLE = "§6§l- La menu -";
    public final Inventory menu = Bukkit.createInventory(null, 27, MENU_TITLE);

    public void setupInfoInventory() {
        menu.setItem(8,instance.itemManager.createItem(Material.CHEST,"Arsenal"));
    }
}
