package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.dependency.VaultDependency;
import me.IcyFlameX.GTACops.main.Main;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PayFine {
    private Main plugin;
    private FetchDetails fetchDetails;

    public PayFine(Main plugin) {
        this.plugin = plugin;
        this.fetchDetails = new FetchDetails(this.plugin);
    }

    public void reduceWantLevel(Player player, boolean flag, int signFine) {
        if (player.hasPermission("GTACops.user.payfine") || player.hasPermission("GTACops.admin")) {
            if (fetchDetails.getWantLvl(player) == 0)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                        plugin.getConfigFileManager().getMsgConfigFile().getString("PayFine_NotWanted")));
            else {
                if (!VaultDependency.getEconomy().hasAccount(player))
                    VaultDependency.getEconomy().createPlayerAccount(player);
                int wantLvL = fetchDetails.getWantLvl(player);
                int fine = flag ? plugin.getConfigFileManager().getConfigFileConfig()
                        .getInt("Money_Deduction.Level" + wantLvL) : signFine;
                if (VaultDependency.getEconomy().getBalance(player) >= fine) {
                    EconomyResponse economyResponse = VaultDependency.getEconomy().withdrawPlayer(player, fine);
                    if (economyResponse.transactionSuccess()) {
                        fetchDetails.setWantLvL(player, fetchDetails.getWantLvl(player) - 1);
                        fetchDetails.setKills(player, plugin.getConfigFileManager().getConfigFileConfig().getInt(
                                "Kills_Per_WantedLevel.Level" + (wantLvL - 1)));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                                plugin.getConfigFileManager().getMsgConfigFile().getString("Current_Wanted_LvL") + fetchDetails.getWantLvlStars(player)));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                                plugin.getConfigFileManager().getMsgConfigFile().getString("PayFine_Success.Level" + wantLvL)));
                    } else
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                                plugin.getConfigFileManager().getMsgConfigFile().getString("TransacFailed")));
                } else
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                            plugin.getConfigFileManager().getMsgConfigFile().getString("PayFine_NoMoney")));
            }
        } else
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                    plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_NoPerm") + "GTACops.user.payfine"));
    }
}
