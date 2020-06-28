package me.IcyFlameX.GTACops.mechanics;

import me.IcyFlameX.GTACops.listenerPackage.ListenerClass;
import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CheatCard {

    private Main plugin;
    private CopsFeature copsFeature;

    public CheatCard(Main plugin) {
        this.plugin = plugin;
        copsFeature = new CopsFeature(this.plugin);
    }

    public void giveCheatCard(String playerName, int no) {
        if (Bukkit.getServer().getPlayer(playerName) != null) {
            Player player = Bukkit.getPlayer(playerName);
            ItemStack itemStack = getCardProperties();
            player.getInventory().addItem(itemStack);
        }
    }

    public void useCheatCard(Player player) {
        Inventory inventory = player.getInventory();
        if (player.hasPermission("GTACops.user.cheat") || player.hasPermission("GTACops.admin")) {
            if (ListenerClass.playerCopsMap.containsKey(player)) {
                inventory.removeItem(getCardProperties());
                copsFeature.killCops(player, ListenerClass.playerCopsMap);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                        plugin.getConfigFileManager().getMsgConfigFile().getString("CheatCard.Usage") + ""));
            } else
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX + plugin
                        .getConfigFileManager().getMsgConfigFile().getString("CheatCard.No_Follow")));
        }
    }

    private ItemStack getCardProperties() {
        ItemStack itemStack = new ItemStack(Material.getMaterial(plugin.getConfigFileManager().
                getConfigFileConfig().getString("CheatCard.Type")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfigFileManager()
                .getConfigFileConfig().getString("CheatCard.Name")));
        List<String> list = new ArrayList<String>();
        for (String msg : plugin.getConfigFileManager().getConfigFileConfig().getStringList("CheatCard.Lore"))
            list.add(ChatColor.translateAlternateColorCodes('&', msg));
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
