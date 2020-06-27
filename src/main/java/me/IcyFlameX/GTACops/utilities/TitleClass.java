package me.IcyFlameX.GTACops.utilities;


import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleClass {
    private Main plugin;
    private FetchDetails fetchDetails;

    public TitleClass(Main plugin) {
        this.plugin = plugin;
        fetchDetails = new FetchDetails(this.plugin);
    }

    public void sendTitle(Player sender) {
        if (plugin.getConfigFileManager().getConfigFileConfig().getBoolean("Enable_Title")) {
            int wantLvL = fetchDetails.getWantLvl(sender);
            String title = ChatColor.translateAlternateColorCodes('&', "" +
                    plugin.getConfigFileManager().getMsgConfigFile().getString("Title.Level" + wantLvL));
            String subtitle = ChatColor.translateAlternateColorCodes('&', "" +
                    plugin.getConfigFileManager().getMsgConfigFile().getString("SubTitle.Level" + wantLvL));
            int duration = plugin.getConfigFileManager().getConfigFileConfig().getInt("TitleStayTime") * 20;
            int fadeIn = plugin.getConfigFileManager().getConfigFileConfig().getInt("TitleFadeInTime") * 20;
            int fadeOut = plugin.getConfigFileManager().getConfigFileConfig().getInt("TitleFadeOutTime") * 20;
            sender.sendTitle(title, subtitle, fadeIn, duration, fadeOut);
        }
    }
}
