package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

    private Main plugin;
    private int project;
    private URL url;
    private String version;
    private boolean setup = true;

    private boolean update;
    private String newVersion;

    public UpdateChecker(Main plugin, int project, boolean flag, Player player) {
        this.plugin = plugin;
        this.project = project;
        version = plugin.getDescription().getVersion();
        try {
            url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + project);
            check(flag, player);
        } catch (MalformedURLException exception) {
            setup = false;
            exception.printStackTrace();
        }
    }

    public String getResourceURL() {
        return "https://www.spigotmc.org/resources/gtacops." + project;
    }

    public void check(boolean flag, Player player) {
        if (!setup) return;
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                URLConnection connection = url.openConnection();
                newVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                update = !version.equals(newVersion);
                if (flag)
                    getUpdateMessage();
                else
                    sendPlayerUpdateMessage(player);
            } catch (Exception exception) {
                exception.printStackTrace();
                setup = false;
            }
        });
    }

    private void getUpdateMessage() {
        plugin.getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(GTACops)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        if (update) {
            plugin.getLogger().info("New version found! You are currently running version " + version + ", latest version is " + newVersion);
            plugin.getLogger().info("Download From : " + getResourceURL());
        } else plugin.getLogger().info("You are using latest version of the plugin!");
        plugin.getLogger().info("Plugin Enabled Successfully");
        plugin.getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(GTACops)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    private void sendPlayerUpdateMessage(Player player) {
        if (update) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                    plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_NewVersionFound") + version));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d" + getResourceURL()));
        } else
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                    plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_NoNewVersion") + version));

    }
}