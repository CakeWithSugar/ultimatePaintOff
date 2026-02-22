package org.cws.paintOff.managers;

import org.bukkit.entity.Player;
import org.cws.paintOff.PaintOff;

import java.io.*;
import java.util.Locale;

public class ArchiveManager {
    PaintOff instance = PaintOff.getInstance();

    public void createEmptyFile(Player player) {
        File parent = new File("plugins/PaintOff/Archive");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                instance.getLogger().severe("File does not exist: plugins/PaintOff/Archive");
                return;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.format(Locale.US, "%d", 0)); //Weapon Number
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", 0)); // Coins
            writer.newLine();
            writer.write(String.format(Locale.US, "%s,%d", "None",-1)); // Shoes Name + Level
            writer.newLine();
            writer.write(String.format(Locale.US, "%s,%d", "None",-1)); // Pants Name + Level
            writer.newLine();
            writer.write(String.format(Locale.US, "%s,%d", "None",-1)); // Jacket Name + Level
        } catch (IOException e) {
            instance.getLogger().severe("Error while creating the Player-File!");
        }
    }

    public void editFile(Player player) {
        File parent = new File("plugins/PaintOff/Archive");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                instance.getLogger().severe("File does not exist: plugins/PaintOff/Archive");
                return;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            if (instance.selectionManager.weapon.get(player) == null) {
                writer.write(String.format(Locale.US, "%s", instance.snap.name)); //Weapon Name
                writer.newLine();
            } else {
                writer.write(String.format(Locale.US, "%s", instance.selectionManager.weapon.get(player))); //Weapon Name
                writer.newLine();
            }
            if (instance.pointsManager.coins.get(player) == null) {
                writer.write(String.format(Locale.US, "%d", 0)); // Coins
                writer.newLine();
            } else {
                writer.write(String.format(Locale.US, "%d", instance.pointsManager.coins.get(player))); // Coins
                writer.newLine();
            }
            writer.write(String.format(Locale.US, "%s,%d", "None",-1)); // Shoes Name + Level
            writer.newLine();
            writer.write(String.format(Locale.US, "%s,%d", "None",-1)); // Pants Name + Level
            writer.newLine();
            writer.write(String.format(Locale.US, "%s,%d", "None",-1)); // Jacket Name + Level
        } catch (IOException e) {
            instance.getLogger().severe("Error while creating the Player-File!");
        }
    }

    public String readWeapon(Player player) {
        File parent = new File("plugins/PaintOff/Archive");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                instance.getLogger().severe("File does not exist: plugins/PaintOff/Archive");
                return null;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            instance.getLogger().severe("Error while reading the Player-File!");
        }
        return null;
    }

    public int readCoins(Player player) {
        File parent = new File("plugins/PaintOff/Archive");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                instance.getLogger().severe("File does not exist: plugins/PaintOff/Archive");
                return -1;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            instance.getLogger().severe("Error while reading the Player-File!");
        }
        return -1;
    }

    public String readCloath(Player player,String object) {
        File parent = new File("plugins/PaintOff/Archive");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                instance.getLogger().severe("File does not exist: plugins/PaintOff/Archive");
                return null;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            reader.readLine();
            if (object.equals("Shoes")) {
                String line = reader.readLine();
                String[] parts = line.split(",", 2);
                return parts[0];
            } else {
                reader.readLine();
            }
            if (object.equals("Pants")) {
                String line = reader.readLine();
                String[] parts = line.split(",", 2);
                return parts[0];
            } else {
                reader.readLine();
            }
            if (object.equals("Jacket")) {
                String line = reader.readLine();
                String[] parts = line.split(",", 2);
                return parts[0];
            }
        } catch (IOException e) {
            instance.getLogger().severe("Error while reading the Player-File!");
        }
        return null;
    }

    public int readCloathLevel(Player player,String object) {
        File parent = new File("plugins/PaintOff/Archive");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                instance.getLogger().severe("File does not exist: plugins/PaintOff/Archive");
                return -1;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            reader.readLine();
            if (object.equals("Shoes")) {
                String line = reader.readLine();
                String[] parts = line.split(",", 2);
                return Integer.parseInt(parts[1]);
            } else {
                reader.readLine();
            }
            if (object.equals("Pants")) {
                String line = reader.readLine();
                String[] parts = line.split(",", 2);
                return Integer.parseInt(parts[1]);
            } else {
                reader.readLine();
            }
            if (object.equals("Jacket")) {
                String line = reader.readLine();
                String[] parts = line.split(",", 2);
                return Integer.parseInt(parts[1]);
            }
        } catch (IOException e) {
            instance.getLogger().severe("Error while reading the Player-File!");
        }
        return -1;
    }

    public boolean hasFile(Player player) {
        File file = new File("plugins/PaintOff/Archive", player.getName()+".dat");
        return file.exists();
    }

    public void setup(Player player, boolean leaving) {
        if (!hasFile(player)) {
            createEmptyFile(player);
        }
        if (!leaving) {
            String weapon = readWeapon(player);
            int coins = readCoins(player);
            String shoes = readCloath(player, "Shoes");
            String pants = readCloath(player, "Pants");

            String jacket = readCloath(player, "Jacket");
            int shoesLevel = readCloathLevel(player, "Shoes");
            int pantsLevel = readCloathLevel(player, "Pants");
            int jacketLevel = readCloathLevel(player, "Jacket");

            instance.selectionManager.weapon.put(player, weapon);
            instance.pointsManager.coins.put(player, coins);
        } else {
            editFile(player);
            instance.selectionManager.weapon.remove(player);
            instance.pointsManager.coins.remove(player);
        }
    }
}
