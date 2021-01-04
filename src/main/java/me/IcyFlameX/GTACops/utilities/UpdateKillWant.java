package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UpdateKillWant {

    private Main plugin;
    private FetchDetails fetchDetails;

    public UpdateKillWant(Main plugin) {
        this.plugin = plugin;
        fetchDetails = new FetchDetails(this.plugin);
    }

    //This function updates the kill Value of a player after he has died.
    public void updateKill(Player killer, Player dead) {
        //For Killer
        String killPath = killer.getUniqueId().toString() + "." + killer.getName() + ".Kills";
        String wantpath = killer.getUniqueId().toString() + "." + killer.getName() + ".WantLevel";
        if (this.plugin.getConfigFileManager().getStatsFileConfig().contains(killPath)) {
            this.plugin.getConfigFileManager().getStatsFileConfig().set(killPath, this.plugin.getConfigFileManager().getStatsFileConfig().getInt(killPath) + 1);
        } else {
            this.plugin.getConfigFileManager().getStatsFileConfig().set(killPath, 1);
            this.plugin.getConfigFileManager().getStatsFileConfig().set(wantpath, 0);
        }
        //Implicitly call the updateWant function
        if (!(fetchDetails.getKills(killer) >
                plugin.getConfigFileManager().getConfigFileConfig().getInt("Kills_Per_WantedLevel.Level5")))
            updateWantLevel(killer);
        //For Dead Player
        resetKillWant(dead);
        plugin.getConfigFileManager().saveStatsFile();

    }

    //This function updates the wanted level of a player after he has killed another player.
    public void updateWantLevel(Player killer) {
        if (this.plugin.getConfigFileManager().getStatsFileConfig().contains(getWantPath(killer))) {
            int currentKill = this.plugin.getConfigFileManager().getStatsFileConfig().getInt(getKillPath(killer));
            for (int i = 1; i <= 5; i++)
                if (currentKill == plugin.getConfigFileManager().getConfigFileConfig().getInt("Kills_Per_WantedLevel.Level" + i)) {
                    this.plugin.getConfigFileManager().getStatsFileConfig().set(getWantPath(killer), i);
                    killer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            CommandManager.PREFIX + this.plugin.getConfigFileManager().getMsgConfigFile().
                                    getString("Current_Wanted_LvL")) + fetchDetails.getWantLvlStars(killer));
                    break;
                }
        } else
            this.plugin.getConfigFileManager().getStatsFileConfig().set(getWantPath(killer), 0);
        this.plugin.getConfigFileManager().saveStatsFile();
    }

    //Reset Player Stats after the player has died
    public void resetKillWant(Player player) {
        plugin.getConfigFileManager().getStatsFileConfig().set(getKillPath(player), 0);
        plugin.getConfigFileManager().getStatsFileConfig().set(getWantPath(player), 0);
        plugin.getConfigFileManager().saveStatsFile();
    }

    //Returns the path in Stats.yml file for player total kills
    public String getKillPath(Player player) {
        return player.getUniqueId().toString() + "." + player.getName() + ".Kills";
    }

    //Returns the path in Stats.yml file for player total wanted kills
    public String getWantPath(Player player) {
        return player.getUniqueId().toString() + "." + player.getName() + ".WantLevel";
    }
}
