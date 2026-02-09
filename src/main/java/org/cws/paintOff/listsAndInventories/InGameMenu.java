package org.cws.paintOff.listsAndInventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.cws.paintOff.PaintOff;

public class InGameMenu {
    PaintOff instance = PaintOff.getInstance();
    private final String MENU_TITLE = "§6§l- La menu -";
    public final Inventory menu = Bukkit.createInventory(null, 27, MENU_TITLE);

    public void setupInfoInventory() {
        menu.setItem(8,instance.itemManager.createItem(Material.CHEST,"Arsenal"));
    }
}
