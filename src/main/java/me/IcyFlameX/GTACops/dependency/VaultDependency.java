package me.IcyFlameX.GTACops.dependency;

import me.IcyFlameX.GTACops.main.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;


public class VaultDependency {

    private Main plugin;
    private static Economy econ = null;

    public VaultDependency(Main plugin) {
        this.plugin = plugin;
    }

    public boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
