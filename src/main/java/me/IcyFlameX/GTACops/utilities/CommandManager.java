package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.dataManager.ConfigFileManager;
import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandManager implements CommandExecutor {

    private Main plugin;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("gcops") && args.length == 1 && "kills".equalsIgnoreCase(args[0]))
                if (player.hasPermission("GTACops.user.stats")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&D&L[GTACops] &6&L>> " +
                            "&FKills : 0"));
                    return true;
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have " +
                            "GTACops.user.stats permission"));
                    return false;
                }
            if (label.equalsIgnoreCase("gcops") && args.length == 1 && "reload".equalsIgnoreCase(args[0])) {
                Bukkit.getConsoleSender().sendMessage("Reloaded GTACops!");
                this.plugin.setConfigFileManager(new ConfigFileManager(plugin));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&D&L[GTACops] &6&L>> " +
                        "&FGTACops reloaded!"));
                return true;
            }
        }

        return false;
    }
}
