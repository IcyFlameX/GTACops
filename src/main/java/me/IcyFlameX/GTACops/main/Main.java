package me.IcyFlameX.GTACops.main;

import me.IcyFlameX.GTACops.api.MetricsLite;
import me.IcyFlameX.GTACops.dataManager.ConfigFileManager;
import me.IcyFlameX.GTACops.dependency.PAPIDependency;
import me.IcyFlameX.GTACops.dependency.VaultDependency;
<<<<<<< HEAD
import me.IcyFlameX.GTACops.listenerPackage.CrackShotListener;
=======
>>>>>>> e4e1f09b48efeebed0531a9b67c3f0e727b091dd
import me.IcyFlameX.GTACops.listenerPackage.ListenerClass;
import me.IcyFlameX.GTACops.mechanics.CopsFeature;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import me.IcyFlameX.GTACops.utilities.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

<<<<<<< HEAD
import java.util.Objects;
=======
>>>>>>> e4e1f09b48efeebed0531a9b67c3f0e727b091dd
import java.util.logging.Level;

public final class Main extends JavaPlugin implements Listener {

    //To maintain only a single copy of the ConfigFileManager object
    // So as to reflect changes in every class
    private static ConfigFileManager configFileManager;

    @Override
    public void onEnable() {
        metricsChecker();
        if (checkDependency()) {
            checkUpdate();
            setConfigFileManager(new ConfigFileManager(this));
<<<<<<< HEAD
            Objects.requireNonNull(this.getCommand("gcops")).setExecutor(new CommandManager(this));
            this.getServer().getPluginManager().registerEvents(new ListenerClass(this), this);
            this.getServer().getPluginManager().registerEvents(new CrackShotListener(this),this);
=======
            this.getCommand("gcops").setExecutor(new CommandManager(this));
            this.getServer().getPluginManager().registerEvents(new ListenerClass(this), this);
>>>>>>> e4e1f09b48efeebed0531a9b67c3f0e727b091dd
            super.onEnable();
        }

    }

    @Override
    public void onDisable() {
        CopsFeature copsFeature = new CopsFeature(this);
        super.onDisable();
        if (ListenerClass.playerCopsMap != null)
            for (Player player : ListenerClass.playerCopsMap.keySet()) {
                copsFeature.killCops(player, ListenerClass.playerCopsMap);
            }
    }

    private void metricsChecker() {
        int pluginID = 2017;
        new MetricsLite(this, pluginID);
    }

    private boolean checkDependency() {
        if (!new VaultDependency(Main.this).setupEconomy()) {
            getLogger().info("-----------------[GTACops]-----------------");
            getLogger().log(Level.SEVERE, "Vault Not Found or Essentials Missing, Disabling plugin. Install Vault/Essentials!");
            getLogger().info("-----------------[GTACops]-----------------");
            getPluginLoader().disablePlugin(this);
            return false;
        } else if (!new PAPIDependency(Main.this).isPAPIinstalled()) {
            getLogger().info("-----------------[GTACops]-----------------");
            getLogger().log(Level.WARNING, "PlaceHolderAPI Not Found, PlaceHolders Won't Work. Install PlaceHolderAPI!");
            getLogger().info("-----------------[GTACops]-----------------");
        }
        return true;
    }

    private void checkUpdate() {
        new UpdateChecker(this, 39090, true, null);
    }

    public ConfigFileManager getConfigFileManager() {
        return configFileManager;
    }

    public void setConfigFileManager(ConfigFileManager configFileManager) {
        Main.configFileManager = configFileManager;
    }

    //For PAPI only
    public static ConfigFileManager getApiCongManager() {
        return configFileManager;
    }

}
