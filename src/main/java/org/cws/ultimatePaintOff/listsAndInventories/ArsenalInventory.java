package org.cws.ultimatePaintOff.listsAndInventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cws.ultimatePaintOff.UltimatePaintOff;

import java.util.ArrayList;
import java.util.List;

public class ArsenalInventory {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();
    private final String ARSENAL_TITLE = "§6§l- Del'Finn Arsenal -";
    public final Inventory ARSENAL = Bukkit.createInventory(null, 54, ARSENAL_TITLE);

    private final String ULT_INFO = "§6§l- La-Ser Ults -";
    public final Inventory INFO = Bukkit.createInventory(null, 9, ULT_INFO);

    //setup in ArsenalCoordinatingManager

    public ItemStack info() {
        return instance.itemManager.createItem(Material.BOOK, "Ultimate Info");
    }

    public void setupInfoInventory() {
        INFO.setItem(1, heliTornedo());
        INFO.setItem(2, platzRegen());
        INFO.setItem(3, sonnenschutz());
        INFO.setItem(4, jettBlaster());
        INFO.setItem(5, fokusBooster());
        INFO.setItem(6, krawumKreisel());
        INFO.setItem(7, non());
    }

    public ItemStack heliTornedo() {
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6"+ instance.heliTornedo.name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Feuert einen §3"+ instance.heliTornedo.helixRadius +"§7 breiten Strahl aus Farbe in die §eBlickrichtung§7.");
            lore.add("§7Der Strahl läd sich §3" + instance.heliTornedo.prepTime + "§7 Sekunden auf,");
            lore.add("§7bevor er §3" + instance.heliTornedo.time + "§7 Sekunden gegner in ihm §eerledigt");
            lore.add("§7und Blöcke §eeinfärbt§7.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack platzRegen() {
        ItemStack item = new ItemStack(Material.WATER_BUCKET);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6"+ instance.platzRegen.name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Feuert eine Wolke aus Farbe in die §eBlickrichtung§7.");
            lore.add("§7Die Wolke §efliegt§7 in die Luft und schwebt für §3" + instance.platzRegen.duration + "§7 Sekunden");
            lore.add("§7in die §egleiche Richtung§7.");
            lore.add("§7Dabei tropfen Farbkugeln runter, welche Blöcke §eeinfärben§7");
            lore.add("§7und gegnern §3" + instance.platzRegen.damage + "§7 Schaden zufügen.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack sonnenschutz() {
        ItemStack item = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6"+ instance.sonnenschutz.name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Läst eine Sonne §eüber dem Spieler§7 erscheinen,");
            lore.add("§7Welche sich langsam §edem boden nähert§7.");
            lore.add("§7Auf dem Boden entsteht eine §eSchutzzohne§7,");
            lore.add("§7welche sich um §3" + instance.sonnenschutz.abnahme + "§7 alle §e0.5§7 Sekunden verkleinert");
            lore.add("§7und anderen Teammitgliedern in der Zone §eResistenz§7 gibt.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack jettBlaster() {
        ItemStack item = new ItemStack(Material.ELYTRA);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6"+ instance.jettBlaster.name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Läst den Spieler für §3" + instance.jettBlaster.duration + "§7 Sekunden §3" + instance.jettBlaster.hight + "§7 Blöcke hoch fliegen.");
            lore.add("§7Währenddessen §egleitet§7 der Spieler in die §eBlickrichtung§7.");
            lore.add("§7Wird während des §egleitens§7 geschossen werden §eBlasterkugeln§7 geschossen.");
            lore.add("§7Bei aufprall §eexplodieren§7 sie, §efärben§7 ein und verursachen §3" + instance.jettBlaster.damage + "§7 Explosionsschaden.");
            lore.add("§7Nach Ablauf der Zeit wird der Spieler zurück zu seiner Ausgangsposition §eteleportiert§7.");
            lore.add("");
            lore.add("§7Durch §e[SHIFT]§7 kann der flug unterbrochen werden.");
            lore.add("§7Fähigkeit geht bei §eeleminierung§7 verloren.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack fokusBooster() {
        ItemStack item = new ItemStack(Material.TARGET);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6"+ instance.fokusBooster.name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Jeder Spieler im §eTeam§7 erhält für §3"+ instance.fokusBooster.duration +" §7Sekunden §3+"+ instance.fokusBooster.regenBoost +" §eFarbregeneration§7 und");
            lore.add("§7jeder §everursachte Schaden§7 wird um §3+"+ instance.fokusBooster.damageBoost +" §7gesteigert.");
            lore.add("§7Kugeln §emakieren§7 für §3"+ instance.fokusBooster.markSeconds +"§7 Sekunden den Gegner, wenn sie treffen.");
            lore.add("§7Während der dauer können keine §eUltpunkte§7 erhalten werden.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack krawumKreisel() {
        ItemStack item = new ItemStack(Material.SUNFLOWER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6"+ instance.krawumKreisel.name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Feuert einen Kreisel aus Farbe in die §eBlickrichtung§7.");
            lore.add("§7Der Kreisel §efliegt§7 in die Luft und schwebt für §3" + instance.krawumKreisel.duration + "§7 Sekunden");
            lore.add("§7nach §eoben§7.");
            lore.add("§7Dabei schießt er §eFarbkugeln §7in §ezufällige Richtungen§7, welche");
            lore.add("§7bei aufprall §eexplodieren§7, Blöcke §eeinfärben §7und gegnern");
            lore.add("§3" + instance.krawumKreisel.damage + "§7 Explosionsschaden zufügen.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack non() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§c???");
            List<String> lore = new ArrayList<>();
            lore.add("§dAuf dem Weg!");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
