package me.IcyFlameX.GTACops.mechanics;

import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GUIClass {
    private Main plugin;
    private Inventory inventory;

    public GUIClass(Main plugin) {
        this.plugin = plugin;
    }

    private void createGTACopsPanel() {
        FileConfiguration msgsFile = plugin.getConfigFileManager().getMsgConfigFile();
        inventory = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&',
                msgsFile.getString("GTACops_Gui.MainPanel.Title") + ""));

        //PayFine Button
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "" + msgsFile.getString("GTACops_Gui.MainPanel.Fine.Name")));
        ArrayList<String> payFineList = new ArrayList<String>();
        for (String msg : plugin.getConfigFileManager().getMsgConfigFile().getStringList("GTACops_Gui.MainPanel.Fine.Lore"))
            payFineList.add(ChatColor.translateAlternateColorCodes('&', msg));
        meta.setLore(payFineList);
        item.setItemMeta(meta);
        inventory.setItem(0, item);

        //Tracker Button
        item.setType(Material.COMPASS);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "" + msgsFile.getString("GTACops_Gui.MainPanel.Compass.Name")));
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "" + msgsFile.getString("GTACops_Gui.MainPanel.Compass.Lore1")),
                ChatColor.translateAlternateColorCodes('&', "" + msgsFile.getString("GTACops_Gui.MainPanel.Compass.Lore2"))));
        item.setItemMeta(meta);
        inventory.setItem(4, item);

        //Close Button
        item.setType(Material.BARRIER);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "" + msgsFile.getString("GTACops_Gui.MainPanel.Barrier.Name")));
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "" + msgsFile.getString("GTACops_Gui.MainPanel.Barrier.Lore"))));
        item.setItemMeta(meta);
        inventory.setItem(8, item);
    }

    public void openGUI() {
        createGTACopsPanel();
    }

    public Inventory getInventory() {
        return inventory;
    }
}
