package me.IcyFlameX.GTACops.mechanics;

import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import me.IcyFlameX.GTACops.utilities.PayFine;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class CustomSign {

    private Main plugin;
    private PayFine payFine;
    public static final String SIGNPAYFINE = "&f&l[&d&lPayFine&f&l]";

    public CustomSign(Main plugin) {
        this.plugin = plugin;
        this.payFine = new PayFine(this.plugin);
    }


    public void changeSign(SignChangeEvent sign, Player player) {
        if ("[GTACops]".equals(sign.getLine(0)) && "[PayFine]".equals(sign.getLine(2)) && sign.getLine(1).startsWith("$")
                && sign.getLine(1).substring(1).matches("[0-9]+")) {
            if (player.hasPermission("GTACops.admin")) {
                sign.setLine(0, ChatColor.translateAlternateColorCodes('&', CommandManager.TITLE));
                sign.setLine(2, ChatColor.translateAlternateColorCodes('&', SIGNPAYFINE));
            } else
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                        plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_NoPerm") +
                        "GTACops.admin"));
        }
    }


    public void deductFromPlayer(Sign sign, Player player) {
        int fine = Integer.parseInt(sign.getLine(1).substring(1));
        payFine.reduceWantLevel(player, false, fine);
    }
}
