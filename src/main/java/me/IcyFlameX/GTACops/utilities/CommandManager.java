package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.mechanics.GUIClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandManager implements CommandExecutor {

    private Main plugin;
    private FetchDetails fetchDetails;
    public static final String PREFIX = "&f&l[&d&lGTACops&f&l]&6 &f&l>> ";
    public static final String TITLE = "&f&l[&d&lGTACops&f&l]";
    public static final String HEADER = "&f<&6----------&f&l[&d&lGTACops&f&l]&6----------&f>";
    public static final String FOOTER = "&f<&6--------------&f&l()&6--------------&f>";

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
                            plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_NoPerm") +
                            "GTACops.user"));
                    return true;
                } else {
                    if (args.length == 1) {
                        if ("kills".equalsIgnoreCase(args[0]))
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX +
                                    "&6" + plugin.getConfigFileManager().getMsgConfigFile().getString("Current_Kills") + "&c " +
                                    fetchDetails.getKills(player)));
                        else if ("wantlvl".equalsIgnoreCase(args[0]))
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX +
                                    "&6" + plugin.getConfigFileManager().getMsgConfigFile().getString("Current_Wanted_LvL") + "&c " +
                                    fetchDetails.getWantLvlStars(player)));
                        else if ("topkills".equalsIgnoreCase(args[0]))
                            new TopStats(plugin).topKillsBoard(player);
                        else if ("topwant".equalsIgnoreCase(args[0]))
                            new TopStats(plugin).topWantBoard(player);
                        else if ("panel".equalsIgnoreCase(args[0])) {
                            GUIClass guiClass = new GUIClass(plugin);
                            guiClass.openGUI();
                            player.openInventory(guiClass.getInventory());
                        } else if ("reload".equalsIgnoreCase(args[0])) {
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
                    } else if (args.length == 2) {
                        if ("reset".equalsIgnoreCase(args[0])) {
                            if (player.hasPermission("GTACops.admin")) {
                                changeStats(player, new String[]{args[1], "0"});
                            } else
                                noPermAdmin(player);
                        }
                    } else if (args.length == 3) {
                        if ("set".equalsIgnoreCase(args[0])) {
                            if (player.hasPermission("GTACops.admin")) {
                                changeStats(player, new String[]{args[1], args[2]});
                            } else
                                noPermAdmin(player);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void changeStats(Player player, String[] args) {
        boolean flag = false;
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getName().equals(args[0]) || online.getDisplayName().equals(args[0])) {
                if (Integer.parseInt(args[1]) == 0) {
                    fetchDetails.setKills(online, 0);
                    fetchDetails.setWantLvL(online, 0);
                } else {
                    fetchDetails.setKills(online, plugin.getConfigFileManager().getConfigFileConfig().getInt("Kills_Per_WantedLevel.Level" +
                            Integer.parseInt(args[1])));
                    fetchDetails.setWantLvL(online, Integer.parseInt(args[1]));
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                        plugin.getConfigFileManager().getMsgConfigFile().getString("Stats_Change")));
                online.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                        plugin.getConfigFileManager().getMsgConfigFile().getString("Stats_Change_Admin")));
                flag = true;
            }
        }
        if (!flag)
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                    plugin.getConfigFileManager().getMsgConfigFile().getString("Not_Online")));
    }

    public boolean noPermAdmin(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX +
                plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_NoPerm") +
                "GTACops.admin"));
        return true;
    }
}
