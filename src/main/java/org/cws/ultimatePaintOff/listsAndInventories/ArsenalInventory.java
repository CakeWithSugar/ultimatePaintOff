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
        INFO.setItem(1, helixpulser());
        INFO.setItem(2, platzRegen());
        INFO.setItem(3, sonnenschutz());
        INFO.setItem(4, jettBlaster());
        INFO.setItem(5, fokusBooster());
        INFO.setItem(6, krawumKreisel());
        INFO.setItem(7, non());
    }

    public ItemStack helixpulser() {
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6"+ instance.helixpulser.name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Feuert einen §3" + instance.helixpulser.helixRadius + " §7Blöcke breiten Farbstrahl in §edie Blickrichtung§7.");
            lore.add("§7Der Strahl lädt sich §3" + instance.helixpulser.prepTime + " §7Sekunden lang auf,");
            lore.add("§7bevor er §3" + instance.helixpulser.time + " §7Sekunden lang Gegner in ihm §eerledigt");
            lore.add("§7und Blöcke §7§eeinfärbt§7.");
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
            lore.add("§7Dabei tropfen Farbkugeln herab, die Blöcke §eeinfärben");
            lore.add("§7und Gegnern §3" + instance.platzRegen.damage + "§7 Schaden zufügen.");
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
            lore.add("§7Lässt eine Sonne §eüber dem Spieler§7 erscheinen,");
            lore.add("§7welche sich langsam §edem Boden nähert§7.");
            lore.add("§7Auf dem Boden entsteht eine §eSchutzzone§7,");
            lore.add("§7welche sich alle §e0.5§7 Sekunden um §3" + instance.sonnenschutz.abnahme + "§7 verkleinert");
            lore.add("§7und anderen Teammitgliedern in der Zone §eResistenz§7 verleiht.");
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
            lore.add("§7Lässt den Spieler für §3" + instance.jettBlaster.duration + "§7 Sekunden §3" + instance.jettBlaster.hightToReach + "§7 Blöcke hoch fliegen.");
            lore.add("§7Währenddessen §egleitet§7 der Spieler in die §eBlickrichtung§7.");
            lore.add("§7Während des §eGleitens§7 werden §eBlasterkugeln§7 abgefeuert,");
            lore.add("§7die bei Aufprall §eexplodieren§7, Blöcke §eeinfärben");
            lore.add("§7und §3" + instance.jettBlaster.damage + "§7 Explosionsschaden verursachen.");
            lore.add("§7Nach Ablauf der Zeit wird der Spieler zurück zu seiner Ausgangsposition §eteleportiert§7.");
            lore.add("");
            lore.add("§7Durch §e[SHIFT]§7 kann der Flug §eunterbrochen§7 werden.");
            lore.add("§7Durch §e[Q]§7 kann der Flug §esofort beendet§7 werden.");
            lore.add("§7Die Fähigkeit geht bei §eEliminierung§7 verloren.");
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
            lore.add("§7Jeder Spieler im §eTeam§7 erhält für §3" + instance.fokusBooster.duration + "§7 Sekunden §3+" + instance.fokusBooster.regenBoost + " §eFarbregeneration§7,");
            lore.add("§7und jeder §everursachte Schaden§7 wird um §3+" + instance.fokusBooster.damageBoost + " §7erhöht.");
            lore.add("§7Getroffene Gegner werden für §3" + instance.fokusBooster.markSeconds + "§7 Sekunden §emarkiert§7.");
            lore.add("§7Während der Dauer können keine §eUltimativ-Punkte§7 erhalten werden.");
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
            lore.add("§7Dabei schießt er §eFarbkugeln§7 in §ezufällige Richtungen§7, die");
            lore.add("§7beim Aufprall §eexplodieren§7, Blöcke §eeinfärben");
            lore.add("§7und Gegnern §3" + instance.krawumKreisel.damage + "§7 Explosionsschaden zufügen.");
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
