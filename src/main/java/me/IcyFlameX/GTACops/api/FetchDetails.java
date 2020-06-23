package me.IcyFlameX.GTACops.api;

import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.entity.Player;

public class FetchDetails {

    private Main plugin;

    public FetchDetails(Main plugin) {
        this.plugin = plugin;
    }

    public int getKillsOfPlayer(Player player) {
        String killPath = player.getUniqueId().toString() + "." + player.getName() + ".Kills";
        if (plugin.getConfigFileManager().getStatsFileConfig().contains(killPath))
            return plugin.getConfigFileManager().getStatsFileConfig().getInt(killPath);
        else {
            this.plugin.getConfigFileManager().getStatsFileConfig().set(killPath, 0);
            return 0;
        }
    }

    public int getWantLvlOfPlayer(Player player) {
        String wantpath = player.getUniqueId().toString() + "." + player.getName() + ".WantLevel";
        if (plugin.getConfigFileManager().getStatsFileConfig().contains(wantpath))
            return plugin.getConfigFileManager().getStatsFileConfig().getInt(wantpath);
        else {
            this.plugin.getConfigFileManager().getStatsFileConfig().set(wantpath, 0);
            return 0;
        }
    }
}
