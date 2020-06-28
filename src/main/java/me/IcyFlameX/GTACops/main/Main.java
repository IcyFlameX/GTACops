package me.IcyFlameX.GTACops.main;

import me.IcyFlameX.GTACops.dataManager.ConfigFileManager;
import me.IcyFlameX.GTACops.dependency.VaultDependency;
import me.IcyFlameX.GTACops.listenerPackage.ListenerClass;
import me.IcyFlameX.GTACops.mechanics.CopsFeature;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Main extends JavaPlugin implements Listener {

    //To maintain only a single copy of the ConfigFileManager object
    // So as to reflect changes in every class
    private static ConfigFileManager configFileManager;
    private CopsFeature copsFeature;

    @Override
    public void onEnable() {
        if (!new VaultDependency(Main.this).setupEconomy()) {
            getLogger().info("-----------------[GTACops]-----------------");
            getLogger().log(Level.SEVERE, "Vault Not Found, Disabling plugin. Install Vault!");
            getLogger().info("-----------------[GTACops]-----------------");
            getPluginLoader().disablePlugin(this);
        } else {
            setConfigFileManager(new ConfigFileManager(this));
            this.getCommand("gcops").setExecutor(new CommandManager(this));
            this.getServer().getPluginManager().registerEvents(new ListenerClass(this), this);
            super.onEnable();
        }
    }

    @Override
    public void onDisable() {
        copsFeature = new CopsFeature(this);
        super.onDisable();
        for (Player player : ListenerClass.playerCopsMap.keySet()) {
            copsFeature.killCops(player, ListenerClass.playerCopsMap);
        }
    }

    public ConfigFileManager getConfigFileManager() {
        return configFileManager;
    }

    public static ConfigFileManager getApiCongManager() {
        return configFileManager;
    }

    public void setConfigFileManager(ConfigFileManager configFileManager) {
        Main.configFileManager = configFileManager;
    }


}
