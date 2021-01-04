package me.IcyFlameX.GTACops.dependency;

import me.IcyFlameX.GTACops.main.Main;

public class PAPIDependency {
    private Main plugin;

    public PAPIDependency(Main plugin) {
        this.plugin = plugin;
    }

    public boolean isPAPIinstalled() {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return false;
        }
        return true;
    }
}
