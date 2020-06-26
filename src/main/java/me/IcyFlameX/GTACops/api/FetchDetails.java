package me.IcyFlameX.GTACops.api;

import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FetchDetails {

    private Main plugin;

    public FetchDetails(Main plugin) {
        this.plugin = plugin;
    }

    public int getKills(Player player) {
        String killPath = player.getUniqueId().toString() + "." + player.getName() + ".Kills";
        if (plugin.getConfigFileManager().getStatsFileConfig().contains(killPath))
            return plugin.getConfigFileManager().getStatsFileConfig().getInt(killPath);
        else {
            plugin.getConfigFileManager().getStatsFileConfig().set(killPath, 0);
            plugin.getConfigFileManager().saveStatsFile();
            return 0;
        }
    }

    public int getWantLvl(Player player) {
        String wantpath = player.getUniqueId().toString() + "." + player.getName() + ".WantLevel";
        if (plugin.getConfigFileManager().getStatsFileConfig().contains(wantpath))
            return plugin.getConfigFileManager().getStatsFileConfig().getInt(wantpath);
        else {
            plugin.getConfigFileManager().getStatsFileConfig().set(wantpath, 0);
            plugin.getConfigFileManager().saveStatsFile();
            return 0;
        }
    }

    public String getWantLvlStars(Player player) {
        int want = getWantLvl(player);
        int rem = 5 - want;
        StringBuilder sb = new StringBuilder(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigFileManager().getConfigFileConfig().getString("Stars_FillColor") + ""));
        while (want-- > 0)
            sb.append(plugin.getConfigFileManager().getConfigFileConfig().getString("Stars_Logo"));
        sb.append(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigFileManager().getConfigFileConfig().getString("Stars_EmptyColor") + ""));
        while (rem-- > 0)
            sb.append(plugin.getConfigFileManager().getConfigFileConfig().getString("Stars_Logo"));
        return sb.toString();

    }

    public void setWantLvL(Player player, int wantlvl) {
        wantlvl = wantlvl > 5 ? 5 : Math.max(0, wantlvl);
        String wantpath = player.getUniqueId().toString() + "." + player.getName() + ".WantLevel";
        plugin.getConfigFileManager().getStatsFileConfig().set(wantpath, wantlvl);
        plugin.getConfigFileManager().saveStatsFile();
    }

    public void setKills(Player player, int kills) {
        String killPath = player.getUniqueId().toString() + "." + player.getName() + ".Kills";
        plugin.getConfigFileManager().getStatsFileConfig().set(killPath, kills);
        plugin.getConfigFileManager().saveStatsFile();
    }
}
