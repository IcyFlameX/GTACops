package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RewardsPunish {

    private Main plugin;
    private FetchDetails fetchDetails;

    public RewardsPunish(Main plugin) {
        this.plugin = plugin;
        fetchDetails = new FetchDetails(this.plugin);
    }

    public void giveReward(Player toBeRewarded, Player dead, boolean type) {
        String category = "Enable_Rewards_Player";
        String from = "Rewards_KillPlayer";
        if (!type) {
            category = "Enable_Rewards_Cops";
            from = "Rewards_KillCops";
            dead = toBeRewarded;
        }
        if (plugin.getConfigFileManager().getConfigFileConfig().getBoolean(category)) {
            if (fetchDetails.getWantLvl(dead) > 0) {
                for (String commands : plugin.getConfigFileManager().getConfigFileConfig().getStringList(from + ".Level"
                        + fetchDetails.getWantLvl(dead)))
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", toBeRewarded.getName()));
                for (String msg : plugin.getConfigFileManager().getMsgConfigFile().getStringList(from + ".Level"
                        + fetchDetails.getWantLvl(dead)))
                    toBeRewarded.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX + msg));
            }
        }
    }

    public void givePunish(Player dead, boolean type) {
        String category = "Enable_Punish_Player";
        String from = "Punish_KillByPlayer";
        if (!type) {
            category = "Enable_Punish_Cops";
            from = "Punish_KillByCops";
        }
        if (plugin.getConfigFileManager().getConfigFileConfig().getBoolean(category)) {
            if (fetchDetails.getWantLvl(dead) > 0) {
                for (String commands : plugin.getConfigFileManager().getConfigFileConfig().getStringList(from + ".Level"
                        + fetchDetails.getWantLvl(dead)))
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", dead.getName()));
                for (String msg : plugin.getConfigFileManager().getMsgConfigFile().getStringList(from + ".Level"
                        + fetchDetails.getWantLvl(dead)))
                    dead.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX + msg));
            }
        }
    }
}
