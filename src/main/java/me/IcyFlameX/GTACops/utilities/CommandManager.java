package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.mechanics.CheatCard;
import me.IcyFlameX.GTACops.mechanics.GUIClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public final class CommandManager implements CommandExecutor {

    private Main plugin;
    private FetchDetails fetchDetails;
    private CheatCard cheatCard;
    public static final String PREFIX = "&f&l[&d&lGTACops&f&l]&6 &f&l>> ";
    public static final String TITLE = "&f&l[&d&lGTACops&f&l]";
    public static final String HEADER = "&f<&6----------&f&l[&d&lGTACops&f&l]&6----------&f>";
    public static final String FOOTER = "&f<&6--------------&f&l()&6--------------&f>";

    public CommandManager(Main plugin) {
        this.plugin = plugin;
        fetchDetails = new FetchDetails(this.plugin);
        cheatCard = new CheatCard(this.plugin);
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
                        if ("help".equalsIgnoreCase(args[0])) {
                            FileConfiguration msg = plugin.getConfigFileManager().getMsgConfigFile();
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.HEADER));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops kills : " + msg.getString("gcops_kills")));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops wantlvl : " + msg.getString("gcops_wantlvl")));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops topkills : " + msg.getString("gcops_topkills")));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops topwant : " + msg.getString("gcops_topwant")));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops panel : " + msg.getString("gcops_panel")));
                            if (player.hasPermission("GTACops.admin")) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops check <PlayerName> : " + msg.getString("gcops_check")));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops set <PlayerName> <Number> : " + msg.getString("gcops_set")));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops reset <PlayerName> : " + msg.getString("gcops_reset")));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops givecheat <PlayerName> <Number> : " + msg.getString("gcops_cheat")));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops checkupdate : " + msg.getString("gcops_update")));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d/gcops reload : " + msg.getString("gcops_reload")));
                            }
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.FOOTER));
                        } else if ("kills".equalsIgnoreCase(args[0]))
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
                        } else if ("checkupdate".equalsIgnoreCase(args[0])) {
                            if (player.hasPermission("GTACops.admin"))
                                new UpdateChecker(plugin, 39090, false, player);
                            else
                                return noPermAdmin(player);
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
                        } else if ("check".equalsIgnoreCase(args[0])) {
                            if (player.hasPermission("GTACops.admin")) {
                                if (Bukkit.getPlayer(args[1]) != null) {
                                    Player search = Bukkit.getPlayer(args[1]);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.HEADER));
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', search.getDisplayName()));
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigFileManager().getMsgConfigFile()
                                            .getString("Current_Kills") + fetchDetails.getKills(search)));
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigFileManager().getMsgConfigFile()
                                            .getString("Current_Wanted_LvL") + fetchDetails.getWantLvlStars(search)));
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.FOOTER));
                                } else
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigFileManager()
                                            .getMsgConfigFile().getString("Not_Online") + ""));
                            } else
                                noPermAdmin(player);
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + this.plugin.
                                    getConfigFileManager().getMsgConfigFile().getString("GTACops_Help_comm")));
                            return true;
                        }
                    } else if (args.length == 3) {
                        if ("set".equalsIgnoreCase(args[0])) {
                            if (player.hasPermission("GTACops.admin"))
                                changeStats(player, new String[]{args[1], args[2]});
                            else
                                noPermAdmin(player);
                        } else if ("givecheat".equalsIgnoreCase(args[0])) {
                            if (player.hasPermission("GTACops.admin")) {
                                cheatCard.giveCheatCard(args[1], Integer.parseInt(args[2]));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                                        plugin.getConfigFileManager().getMsgConfigFile().getString("CheatCard.Admin")));
                            } else
                                noPermAdmin(player);
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + this.plugin.
                                    getConfigFileManager().getMsgConfigFile().getString("GTACops_Help_comm")));
                            return true;
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + this.plugin.
                                getConfigFileManager().getMsgConfigFile().getString("GTACops_Help_comm")));
                        return true;
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
