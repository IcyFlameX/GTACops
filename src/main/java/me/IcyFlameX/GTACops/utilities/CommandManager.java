package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandManager implements CommandExecutor {

    private Main plugin;
    private FetchDetails fetchDetails;
    private final String PREFIX = "&D&L[GTACops] &f&l>> ";

    public CommandManager(Main plugin) {
        this.plugin = plugin;
        fetchDetails = new FetchDetails(this.plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("gcops")) {
                if (!player.hasPermission("GTACops.user")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX +
                            plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_NoPerm_User") +
                            "GTACops.user"));
                    return true;
                } else {
                    if (args.length == 1) {
                        if ("kills".equalsIgnoreCase(args[0]))
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX +
                                    "&6" + plugin.getConfigFileManager().getMsgConfigFile().getString("Current_Kills") + "&c " +
                                    fetchDetails.getKillsOfPlayer(player)));
                        else if ("wantlvl".equalsIgnoreCase(args[0]))
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX +
                                    "&6" + plugin.getConfigFileManager().getMsgConfigFile().getString("Current_Wanted_LvL") + "&c " +
                                    fetchDetails.getWantLvlOfPlayer(player)));
                        else if ("reload".equalsIgnoreCase(args[0])) {
                            if (player.hasPermission("GTACops.admin")) {
                                plugin.getConfigFileManager().reloadConfig();
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + plugin.getConfigFileManager()
                                        .getMsgConfigFile().getString("GTACops_Reload_comm")));
                            } else
                                return noPermAdmin(player);
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + this.plugin.
                                    getConfigFileManager().getMsgConfigFile().getString("GTACops_Help_comm")));
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean noPermAdmin(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX +
                plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_NoPerm_Admin") +
                "GTACops.admin"));
        return true;
    }
}
