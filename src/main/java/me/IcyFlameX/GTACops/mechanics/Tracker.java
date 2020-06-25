package me.IcyFlameX.GTACops.mechanics;

import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Tracker {
    private Main plugin;
    private Tracker.MinPlayer minPlayer;

    public Tracker(Main plugin) {
        this.plugin = plugin;
    }

    protected class MinPlayer {
        private int dist = 0;
        private Player player = null;

        protected MinPlayer(int dist, Player player) {
            this.dist = dist;
            this.player = player;
        }

        protected int getDist() {
            return dist;
        }

        protected Player getPlayer() {
            return player;
        }
    }

    private MinPlayer findNearestWanted(Player sender) {
        int min = Integer.MAX_VALUE;
        Player find = null;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (sender.getWorld() == player.getWorld())
                if (sender.getLocation().distance(player.getLocation()) <= min) {
                    if (!player.equals(sender)) {
                        min = (int) sender.getLocation().distance(player.getLocation());
                        find = player;
                    }
                }
        }
        return new MinPlayer(min, find);
    }

    public void trackPlayerDown(Player sender) {
        minPlayer = new Tracker(plugin).findNearestWanted(sender);
        if (minPlayer.getPlayer() == null)
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.TITLE +
                    plugin.getConfigFileManager().getMsgConfigFile().getString("Not_Online") + ""));
        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.HEADER));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigFileManager().
                    getMsgConfigFile().getString("Track_Player_Name") + minPlayer.getPlayer().getDisplayName()));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigFileManager().
                    getMsgConfigFile().getString("Track_Player_Dist") + minPlayer.getDist()));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.FOOTER));
            addCompassToPlayer(sender, minPlayer.getPlayer());
            sender.setCompassTarget(minPlayer.getPlayer().getLocation());
        }
    }

    private void addCompassToPlayer(Player sender, Player toFind) {
        Inventory inventory = sender.getInventory();
        int i = 0;
        int pos = 0;
        int counter = 0;
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null && counter == 0) {
                pos = i;
                counter = 1;
            } else if (itemStack != null && (ChatColor.translateAlternateColorCodes('&', "&4&lTracker")).equals(itemStack.getItemMeta().getDisplayName())) {
                inventory.setItem(i, getCompass(toFind));
                counter = 2;
                break;
            }
            i++;
        }
        if (counter == 1)
            inventory.setItem(pos, getCompass(toFind));
    }

    private ItemStack getCompass(Player player) {
        ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.
                getConfigFileManager().getMsgConfigFile().getString("GTACops_Gui.MainPanel.Compass.Name") + ""));
        itemMeta.setLore(Arrays.asList(player.getDisplayName()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    public int getDistOfNearestWanted(Player player) {
        return (int) player.getLocation().distance(minPlayer.getPlayer().getLocation());
    }
}
