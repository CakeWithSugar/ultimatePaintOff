package org.cws.ultimatePaintOff.managers;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.cws.ultimatePaintOff.UltimatePaintOff;

public class ArsenalCoordinatingManager {
    UltimatePaintOff instance = UltimatePaintOff.getInstance();

    public void setupQueueObjects(Player player) {
        player.getInventory().setItem(1,instance.itemManager.createItem(Material.CHEST,"Arsenal"));
        if (instance.arenaManager.countArenas() >= 2) {
            player.getInventory().setItem(2, instance.itemManager.createItem(Material.PAPER, "Arena Vote"));
        }
    }

    public void dealDamage(Player player, Player attacker, String objectName) {
        if (objectName.equals(instance.pistol.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.pistol.damage);
        }
        if (objectName.equals(instance.pistolLight.name)) {
            instance.damageManager.damagePlayer(player, attacker, instance.pistolLight.damage);
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
    }

    public ItemStack getPrimaryItemByWeaponNumber(int weapon) {
        ItemStack item = null;
        if (weapon == instance.pistol.weaponNumber) {
            item = instance.pistol.gameItem();
        }
        if (weapon == instance.pistolLight.weaponNumber) {
            item = instance.pistolLight.gameItem();
        }
        if (weapon == instance.nova.weaponNumber) {
            item = instance.nova.gameItem();
        }
        if (weapon == instance.novaPuls.weaponNumber) {
            item = instance.novaPuls.gameItem();
        }
        if (weapon == instance.novaExtend.weaponNumber) {
            item = instance.novaExtend.gameItem();
        }
        if (weapon == instance.triAtler.weaponNumber) {
            item = instance.triAtler.gameItem();
        }
        if (weapon == instance.triAtlerExtend.weaponNumber) {
            item = instance.triAtlerExtend.gameItem();
        }
        if (weapon == instance.triAtlerPegasus.weaponNumber) {
            item = instance.triAtlerPegasus.gameItem();
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
        if (instance.pistol.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.pistol.cost)) {
            instance.pistol.shoot(player);
        }
        if (instance.pistolLight.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.pistolLight.cost)) {
            instance.pistolLight.shoot(player);
        }
        if (instance.nova.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.nova.cost)) {
            instance.nova.shoot(player);
        }
        if (instance.novaPuls.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.novaPuls.cost)) {
            instance.novaPuls.shoot(player);
        }
        if (instance.novaExtend.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.novaExtend.cost)) {
            instance.novaExtend.shoot(player);
        }
        if (instance.triAtler.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtler.cost)) {
            instance.triAtler.shoot(player);
        }
        if (instance.triAtlerExtend.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtlerExtend.cost)) {
            instance.triAtlerExtend.shoot(player);
        }
        if (instance.triAtlerPegasus.isItem(item) && instance.pointsManager.hasEnughFuel(player, instance.triAtlerPegasus.cost)) {
            instance.triAtlerPegasus.shoot(player);
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
        if (instance.itemManager.isItem(item, instance.pistol.material, instance.pistol.name)) {
            instance.selectionManager.put(instance.pistol.weaponNumber,instance.pistol.name,player);
        }
        if (instance.itemManager.isItem(item, instance.pistolLight.material, instance.pistolLight.name)) {
            instance.selectionManager.put(instance.pistolLight.weaponNumber,instance.pistolLight.name,player);
        }
        if (instance.itemManager.isItem(item, instance.nova.material, instance.nova.name)) {
            instance.selectionManager.put(instance.nova.weaponNumber,instance.nova.name,player);
        }
        if (instance.itemManager.isItem(item, instance.novaPuls.material, instance.novaPuls.name)) {
            instance.selectionManager.put(instance.novaPuls.weaponNumber,instance.novaPuls.name,player);
        }
        if (instance.itemManager.isItem(item, instance.novaExtend.material, instance.novaExtend.name)) {
            instance.selectionManager.put(instance.novaExtend.weaponNumber,instance.novaExtend.name,player);
        }
        if (instance.itemManager.isItem(item, instance.triAtler.material, instance.triAtler.name)) {
            instance.selectionManager.put(instance.triAtler.weaponNumber,instance.triAtler.name,player);
        }
        if (instance.itemManager.isItem(item, instance.triAtlerExtend.material, instance.triAtlerExtend.name)) {
            instance.selectionManager.put(instance.triAtlerExtend.weaponNumber,instance.triAtlerExtend.name,player);
        }
        if (instance.itemManager.isItem(item, instance.triAtlerPegasus.material, instance.triAtlerPegasus.name)) {
            instance.selectionManager.put(instance.triAtlerPegasus.weaponNumber,instance.triAtlerPegasus.name,player);
        }
    }

    public int getUltPointsByWeaponNumber(int weaponNumber) {
        if (weaponNumber == instance.pistol.weaponNumber) {
            return instance.pistol.ultPoints;
        }
        if (weaponNumber == instance.pistolLight.weaponNumber) {
            return instance.pistolLight.ultPoints;
        }
        if (weaponNumber == instance.nova.weaponNumber) {
            return instance.nova.ultPoints;
        }
        if (weaponNumber == instance.novaPuls.weaponNumber) {
            return instance.novaPuls.ultPoints;
        }
        if (weaponNumber == instance.novaExtend.weaponNumber) {
            return instance.novaExtend.ultPoints;
        }
        if (weaponNumber == instance.triAtler.weaponNumber) {
            return instance.triAtler.ultPoints;
        }
        if (weaponNumber == instance.triAtlerExtend.weaponNumber) {
            return instance.triAtlerExtend.ultPoints;
        }
        if (weaponNumber == instance.triAtlerPegasus.weaponNumber) {
            return instance.triAtlerPegasus.ultPoints;
        }
        return 10;
    }

    public void setupArsenalInventory() {
        instance.arsenalInventory.ARSENAL.setItem(10, instance.pistol.item());
        instance.arsenalInventory.ARSENAL.setItem(19, instance.pistolLight.item());

        instance.arsenalInventory.ARSENAL.setItem(11, instance.nova.item());
        instance.arsenalInventory.ARSENAL.setItem(20, instance.novaPuls.item());
        instance.arsenalInventory.ARSENAL.setItem(29, instance.novaExtend.item());

        instance.arsenalInventory.ARSENAL.setItem(12, instance.triAtler.item());
        instance.arsenalInventory.ARSENAL.setItem(21, instance.triAtlerExtend.item());
        instance.arsenalInventory.ARSENAL.setItem(30, instance.triAtlerPegasus.item());

        instance.arsenalInventory.ARSENAL.setItem(45, instance.arsenalInventory.info());
    }

    public void castUltimate(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 2.0f);
        if (instance.selectionManager.weapon.get(player) == instance.pistol.weaponNumber) {
            instance.heliTornedo.cast(player);
        }
        if (instance.selectionManager.weapon.get(player) == instance.pistolLight.weaponNumber) {
            instance.platzRegen.cast(player);
        }
        if (instance.selectionManager.weapon.get(player) == instance.nova.weaponNumber) {
            instance.sonnenschutz.cast(player);
        }
        if (instance.selectionManager.weapon.get(player) == instance.novaPuls.weaponNumber) {
            instance.fokusBooster.cast(player);
        }
        if (instance.selectionManager.weapon.get(player) == instance.novaExtend.weaponNumber) {
            instance.heliTornedo.cast(player);
        }
        if (instance.selectionManager.weapon.get(player) == instance.triAtler.weaponNumber) {
            instance.jettBlaster.cast(player);
        }
        if (instance.selectionManager.weapon.get(player) == instance.triAtlerExtend.weaponNumber) {
            instance.platzRegen.cast(player);
        }
        if (instance.selectionManager.weapon.get(player) == instance.triAtlerPegasus.weaponNumber) {
            instance.sonnenschutz.cast(player);
        }
    }
}
