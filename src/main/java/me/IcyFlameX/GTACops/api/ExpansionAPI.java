package me.IcyFlameX.GTACops.api;

import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ExpansionAPI {
    public static String fetchAPIkills(Player player) {
        String killPath = player.getUniqueId().toString() + "." + player.getName() + ".Kills";
        if (Main.getApiCongManager().getStatsFileConfig().contains(killPath))
            return String.valueOf(Main.getApiCongManager().getStatsFileConfig().getInt(killPath));
        else {
            Main.getApiCongManager().getStatsFileConfig().set(killPath, 0);
            Main.getApiCongManager().saveStatsFile();
            return "0";
        }
    }

    public static String fetchAPIStars(Player player) {
        String wantpath = player.getUniqueId().toString() + "." + player.getName() + ".WantLevel";
        int want = 0;
        if (Main.getApiCongManager().getStatsFileConfig().contains(wantpath))
            want = Main.getApiCongManager().getStatsFileConfig().getInt(wantpath);
        else {
            Main.getApiCongManager().getStatsFileConfig().set(wantpath, 0);
            Main.getApiCongManager().saveStatsFile();
            want = 0;
        }
        int rem = 5 - want;
        StringBuilder sb = new StringBuilder(ChatColor.translateAlternateColorCodes('&',
                Main.getApiCongManager().getConfigFileConfig().getString("Stars_FillColor") + ""));
        while (want-- > 0)
            sb.append(Main.getApiCongManager().getConfigFileConfig().getString("Stars_Logo"));
        sb.append(ChatColor.translateAlternateColorCodes('&',
                Main.getApiCongManager().getConfigFileConfig().getString("Stars_EmptyColor") + ""));
        while (rem-- > 0)
            sb.append(Main.getApiCongManager().getConfigFileConfig().getString("Stars_Logo"));
        return sb.toString();
    }

}
