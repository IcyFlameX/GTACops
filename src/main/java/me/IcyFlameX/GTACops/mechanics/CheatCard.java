package me.IcyFlameX.GTACops.mechanics;

<<<<<<< HEAD
import me.IcyFlameX.GTACops.api.FetchDetails;
=======
>>>>>>> e4e1f09b48efeebed0531a9b67c3f0e727b091dd
import me.IcyFlameX.GTACops.listenerPackage.ListenerClass;
import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheatCard {

    private Main plugin;
    private CopsFeature copsFeature;
<<<<<<< HEAD
    private FetchDetails fetchDetails;
=======
>>>>>>> e4e1f09b48efeebed0531a9b67c3f0e727b091dd

    public CheatCard(Main plugin) {
        this.plugin = plugin;
        copsFeature = new CopsFeature(this.plugin);
<<<<<<< HEAD
        this.fetchDetails = new FetchDetails(this.plugin);
=======
>>>>>>> e4e1f09b48efeebed0531a9b67c3f0e727b091dd
    }

    public void giveCheatCard(String playerName, int no) {
        if (Bukkit.getServer().getPlayer(playerName) != null) {
            Player player = Bukkit.getPlayer(playerName);
            ItemStack itemStack = getCardProperties();
            itemStack.setAmount(no);
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
<<<<<<< HEAD
                fetchDetails.setWantLvL(player,0);
=======
>>>>>>> e4e1f09b48efeebed0531a9b67c3f0e727b091dd
            } else
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX + plugin
                        .getConfigFileManager().getMsgConfigFile().getString("CheatCard.No_Follow")));
        } else
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX + plugin.getConfigFileManager().getMsgConfigFile()
                    .getString("GTACops_NoPerm") + "GTACops.user.cheat"));
    }

    public ItemStack getCardProperties() {
        ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(plugin.getConfigFileManager().
                getConfigFileConfig().getString("CheatCard.Type")))));
        ItemMeta itemMeta = itemStack.getItemMeta();
<<<<<<< HEAD
        assert itemMeta != null;
=======
>>>>>>> e4e1f09b48efeebed0531a9b67c3f0e727b091dd
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfigFileManager()
                .getConfigFileConfig().getString("CheatCard.Name"))));
        List<String> list = new ArrayList<String>();
        for (String msg : plugin.getConfigFileManager().getConfigFileConfig().getStringList("CheatCard.Lore"))
            list.add(ChatColor.translateAlternateColorCodes('&', msg));
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
