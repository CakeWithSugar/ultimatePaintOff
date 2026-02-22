package org.cws.paintOff.managers;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;
import org.cws.paintOff.PaintOff;

public class ArsenalCoordinatingManager {
    PaintOff instance = PaintOff.getInstance();

    public void setupQueueObjects(Player player) {
        player.getInventory().setItem(1,instance.itemManager.createItem(Material.CHEST,"Arsenal"));
        if (instance.arenaManager.countArenas() >= 2) {
            player.getInventory().setItem(2, instance.itemManager.createItem(Material.PAPER, "Arena Vote"));
        }
    }

    public void dealDamage(Player player, Player attacker, String objectName) {
        if (objectName.equals(instance.snap.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.snap.damage);
        }
        if (objectName.equals(instance.snapLight.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.snapLight.damage);
        }
        if (objectName.equals(instance.platzRegen.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.platzRegen.damage);
        }
        if (objectName.equals(instance.nova.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.nova.damage);
        }
        if (objectName.equals(instance.novaPuls.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.novaPuls.damage);
        }
        if (objectName.equals(instance.novaExtend.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.novaExtend.damage);
        }
        if (objectName.equals(instance.triAtler.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.triAtler.damage);
        }
        if (objectName.equals(instance.triAtlerExtend.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.triAtlerExtend.damage);
        }
        if (objectName.equals(instance.triAtlerPegasus.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.triAtlerPegasus.damage);
        }
        if (objectName.equals(instance.snapComplex.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.snapComplex.damage);
        }
        if (objectName.equals(instance.akonda.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.akonda.damage);
        }
        if (objectName.equals(instance.akondaExtend.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.akondaExtend.damage);
        }
    }

    public ItemStack getPrimaryItemByWeaponName(String weapon) {
        ItemStack item = null;
        if (weapon.equals(instance.snap.name)) {
            item = instance.snap.gameItem();
        }
        if (weapon.equals(instance.snapLight.name)) {
            item = instance.snapLight.gameItem();
        }
        if (weapon.equals(instance.nova.name)) {
            item = instance.nova.gameItem();
        }
        if (weapon.equals(instance.novaPuls.name)) {
            item = instance.novaPuls.gameItem();
        }
        if (weapon.equals(instance.novaExtend.name)) {
            item = instance.novaExtend.gameItem();
        }
        if (weapon.equals(instance.triAtler.name)) {
            item = instance.triAtler.gameItem();
        }
        if (weapon.equals(instance.triAtlerExtend.name)) {
            item = instance.triAtlerExtend.gameItem();
        }
        if (weapon.equals(instance.triAtlerPegasus.name)) {
            item = instance.triAtlerPegasus.gameItem();
        }
        if (weapon.equals(instance.snapComplex.name)) {
            item = instance.snapComplex.gameItem();
        }
        if (weapon.equals(instance.akonda.name)) {
            item = instance.akonda.gameItem();
        }
        if (weapon.equals(instance.akondaExtend.name)) {
            item = instance.akondaExtend.gameItem();
        }
        return item;
    }

    public ItemStack getUltInfoItemByWeaponNumber(String weapon) {
        ItemStack item = null;
        if (weapon.equals(instance.snap.name)) {
            item = instance.arsenalInventory.helixpulser();
        }
        if (weapon.equals(instance.snapLight.name)) {
            item = instance.arsenalInventory.platzRegen();
        }
        if (weapon.equals(instance.nova.name)) {
            item = instance.arsenalInventory.sonnenschutz();
        }
        if (weapon.equals(instance.novaPuls.name)) {
            item = instance.arsenalInventory.fokusBooster();
        }
        if (weapon.equals(instance.novaExtend.name)) {
            item = instance.arsenalInventory.helixpulser();
        }
        if (weapon.equals(instance.triAtler.name)) {
            item = instance.arsenalInventory.jettBlaster();
        }
        if (weapon.equals(instance.triAtlerExtend.name)) {
            item = instance.arsenalInventory.platzRegen();
        }
        if (weapon.equals(instance.triAtlerPegasus.name)) {
            item = instance.arsenalInventory.sonnenschutz();
        }
        if (weapon.equals(instance.snapComplex.name)) {
            item = instance.arsenalInventory.krawumKreisel();
        }
        if (weapon.equals(instance.akonda.name)) {
            item = instance.arsenalInventory.fokusBooster();
        }
        if (weapon.equals(instance.akondaExtend.name)) {
            item = instance.arsenalInventory.jettBlaster();
        }
        return item;
    }

    public void shootPrimary(Player player, ItemStack item) {
        if (instance.itemManager.isItem(item,Material.CHEST,"Arsenal")) {
            player.openInventory(instance.arsenalInventory.ARSENAL);
            instance.arsenalCoordination.setupArsenalInventory();
            return;
        }
        if (instance.itemManager.isItem(item,Material.PAPER,"Arena Vote") && instance.queueManager.arena2[instance.queueManager.getQueueNumber(player)] != null) {
            player.openInventory(instance.voteInventory.VOTE);
            instance.voteInventory.setupVoteInventory(instance.queueManager.getQueueNumber(player));
            return;
        }
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY) || player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
            return;
        }
        // Specials
        if (instance.jettBlaster.inPhase.containsKey(player)) {
            instance.jettBlaster.shoot(player);
            return;
        }
        //Primary Weapons
        if (instance.snap.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.snap.cost)) {
            instance.snap.shoot(player);
            return;
        }
        if (instance.snapLight.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.snapLight.cost)) {
            instance.snapLight.shoot(player);
            return;
        }
        if (instance.nova.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.nova.cost)) {
            instance.nova.shoot(player);
            return;
        }
        if (instance.novaPuls.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.novaPuls.cost)) {
            instance.novaPuls.shoot(player);
            return;
        }
        if (instance.novaExtend.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.novaExtend.cost)) {
            instance.novaExtend.shoot(player);
            return;
        }
        if (instance.triAtler.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtler.cost)) {
            instance.triAtler.shoot(player);
            return;
        }
        if (instance.triAtlerExtend.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtlerExtend.cost)) {
            instance.triAtlerExtend.shoot(player);
            return;
        }
        if (instance.triAtlerPegasus.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtlerPegasus.cost)) {
            instance.triAtlerPegasus.shoot(player);
            return;
        }
        if (instance.snapComplex.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.snapComplex.cost)) {
            instance.snapComplex.shoot(player);
            return;
        }
        if (instance.akonda.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.akonda.cost)) {
            instance.akonda.shoot(player);
            return;
        }
        if (instance.akondaExtend.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.akondaExtend.cost)) {
            instance.akondaExtend.shoot(player);
        }
    }

    public void shootSecondary(Player player, ItemStack item) {
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY) || player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
            return;
        }
        if (instance.jettBlaster.inPhase.containsKey(player)) {
            instance.jettBlaster.shoot(player);
            return;
        }
        if (instance.triAtler.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtler.costSecondary)) {
            instance.triAtler.shootSecondary(player);
        }
        if (instance.triAtlerExtend.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtlerExtend.costSecondary)) {
            instance.triAtlerExtend.shootSecondary(player);
        }
        if (instance.triAtlerPegasus.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtlerPegasus.costSecondary)) {
            instance.triAtlerPegasus.shootSecondary(player);
        }
    }

    public void arsenalSelection(Player player, ItemStack item) {
        if (instance.itemManager.isItem(item, Material.BOOK, "Ultimate Info")) {
            player.openInventory(instance.arsenalInventory.INFO);
            instance.arsenalInventory.setupInfoInventory();
            return;
        }
        if (instance.itemManager.isItem(item, instance.snap.material, instance.snap.name)) {
            instance.selectionManager.put(instance.snap.name,player);
        }
        if (instance.itemManager.isItem(item, instance.snapLight.material, instance.snapLight.name)) {
            instance.selectionManager.put(instance.snapLight.name,player);
        }
        if (instance.itemManager.isItem(item, instance.nova.material, instance.nova.name)) {
            instance.selectionManager.put(instance.nova.name,player);
        }
        if (instance.itemManager.isItem(item, instance.novaPuls.material, instance.novaPuls.name)) {
            instance.selectionManager.put(instance.novaPuls.name,player);
        }
        if (instance.itemManager.isItem(item, instance.novaExtend.material, instance.novaExtend.name)) {
            instance.selectionManager.put(instance.novaExtend.name,player);
        }
        if (instance.itemManager.isItem(item, instance.triAtler.material, instance.triAtler.name)) {
            instance.selectionManager.put(instance.triAtler.name,player);
        }
        if (instance.itemManager.isItem(item, instance.triAtlerExtend.material, instance.triAtlerExtend.name)) {
            instance.selectionManager.put(instance.triAtlerExtend.name,player);
        }
        if (instance.itemManager.isItem(item, instance.triAtlerPegasus.material, instance.triAtlerPegasus.name)) {
            instance.selectionManager.put(instance.triAtlerPegasus.name,player);
        }
        if (instance.itemManager.isItem(item, instance.snapComplex.material, instance.snapComplex.name)) {
            instance.selectionManager.put(instance.snapComplex.name,player);
        }
        if (instance.itemManager.isItem(item, instance.akonda.material, instance.akonda.name)) {
            instance.selectionManager.put(instance.akonda.name,player);
        }
        if (instance.itemManager.isItem(item, instance.akondaExtend.material, instance.akondaExtend.name)) {
            instance.selectionManager.put(instance.akondaExtend.name,player);
        }
    }

    public int getUltPointsByWeaponName(String weaponName) {
        if (weaponName.equals(instance.snap.name)) {
            return instance.snap.ultPoints;
        }
        if (weaponName.equals(instance.snapLight.name)) {
            return instance.snapLight.ultPoints;
        }
        if (weaponName.equals(instance.nova.name)) {
            return instance.nova.ultPoints;
        }
        if (weaponName.equals(instance.novaPuls.name)) {
            return instance.novaPuls.ultPoints;
        }
        if (weaponName.equals(instance.novaExtend.name)) {
            return instance.novaExtend.ultPoints;
        }
        if (weaponName.equals(instance.triAtler.name)) {
            return instance.triAtler.ultPoints;
        }
        if (weaponName.equals(instance.triAtlerExtend.name)) {
            return instance.triAtlerExtend.ultPoints;
        }
        if (weaponName.equals(instance.triAtlerPegasus.name)) {
            return instance.triAtlerPegasus.ultPoints;
        }
        if (weaponName.equals(instance.snapComplex.name)) {
            return instance.snapComplex.ultPoints;
        }
        if (weaponName.equals(instance.akonda.name)) {
            return instance.akonda.ultPoints;
        }
        if (weaponName.equals(instance.akondaExtend.name)) {
            return instance.akondaExtend.ultPoints;
        }
        return 10;
    }

    public void setupArsenalInventory() {
        instance.arsenalInventory.ARSENAL.setItem(10, instance.snap.item());
        instance.arsenalInventory.ARSENAL.setItem(19, instance.snapLight.item());
        instance.arsenalInventory.ARSENAL.setItem(28, instance.snapComplex.item());

        instance.arsenalInventory.ARSENAL.setItem(11, instance.nova.item());
        instance.arsenalInventory.ARSENAL.setItem(20, instance.novaPuls.item());
        instance.arsenalInventory.ARSENAL.setItem(29, instance.novaExtend.item());

        instance.arsenalInventory.ARSENAL.setItem(12, instance.triAtler.item());
        instance.arsenalInventory.ARSENAL.setItem(21, instance.triAtlerExtend.item());
        instance.arsenalInventory.ARSENAL.setItem(30, instance.triAtlerPegasus.item());

        instance.arsenalInventory.ARSENAL.setItem(13, instance.akonda.item());
        instance.arsenalInventory.ARSENAL.setItem(22, instance.akondaExtend.item());

        instance.arsenalInventory.ARSENAL.setItem(45, instance.arsenalInventory.info());
    }

    public void castUltimate(Player player) {
        if (instance.jettBlaster.inPhase.containsKey(player)) {
            instance.jettBlaster.end(player,true);
        }

        if (instance.selectionManager.weapon.get(player).equals(instance.snap.name)) {
            instance.helixpulser.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.snapLight.name)) {
            instance.platzRegen.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.nova.name)) {
            instance.sonnenschutz.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.novaPuls.name)) {
            instance.fokusBooster.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.novaExtend.name)) {
            instance.helixpulser.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.triAtler.name)) {
            instance.jettBlaster.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.triAtlerExtend.name)) {
            instance.platzRegen.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.triAtlerPegasus.name)) {
            instance.sonnenschutz.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.snapComplex.name)) {
            instance.krawumKreisel.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.akonda.name)) {
            instance.fokusBooster.cast(player);
        }
        if (instance.selectionManager.weapon.get(player).equals(instance.akondaExtend.name)) {
            instance.jettBlaster.cast(player);
        }
    }

    public void dressUp(Player player, boolean clear) {
        if (!clear && player.getInventory().getChestplate().isEmpty()) {
            String color = instance.paintManager.getColorByPlayer(player,false);
            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();

            // Set color based on team
            switch (color) {
                case "RED" -> meta.setColor(Color.RED);
                case "BLUE" -> meta.setColor(Color.BLUE);
                case "GREEN" -> meta.setColor(Color.GREEN);
                case "YELLOW" -> meta.setColor(Color.YELLOW);
                case "PURPLE" -> meta.setColor(Color.PURPLE);
                case "MAGENTA" -> meta.setColor(Color.FUCHSIA);
                case "ORANGE" -> meta.setColor(Color.ORANGE);
                case "GRAY" -> meta.setColor(Color.GRAY);
                case "BLACK" -> meta.setColor(Color.BLACK);
                case "WHITE" -> meta.setColor(Color.WHITE);
                case "LIME" -> meta.setColor(Color.LIME);
                case "CYAN" -> meta.setColor(Color.TEAL);
                case "LIGHT_BLUE" -> meta.setColor(Color.AQUA);
            }
            chestplate.setItemMeta(meta);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setHelmet(ItemStack.of(Material.valueOf(color + "_STAINED_GLASS")));
        } else if (clear){
            player.getInventory().setChestplate(null);
            player.getInventory().setHelmet(null);
        }
    }
}
