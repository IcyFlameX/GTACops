package me.IcyFlameX.GTACops.utilities;

import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class TopStats {

    private Main plugin;
    private FetchDetails fetchDetails;

    public TopStats(Main plugin) {
        this.plugin = plugin;
        fetchDetails = new FetchDetails(this.plugin);
    }

    public void topKillsBoard(Player sender) {
        HashMap<Player, Integer> hashMap = new HashMap<Player, Integer>();
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            hashMap.put(player, fetchDetails.getKills(player));
        displayLeaderBoard(sender, sortByValue(hashMap), false);

    }

    public void topWantBoard(Player sender) {
        HashMap<Player, Integer> hashMap = new HashMap<Player, Integer>();
        for (Player player : Bukkit.getOnlinePlayers())
            hashMap.put(player, fetchDetails.getWantLvl(player));
        displayLeaderBoard(sender, sortByValue(hashMap), true);
    }

    private HashMap<Player, Integer> sortByValue(HashMap<Player, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Player, Integer>> list =
                new LinkedList<Map.Entry<Player, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Player, Integer>>() {
            public int compare(Map.Entry<Player, Integer> o1,
                               Map.Entry<Player, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Player, Integer> temp = new LinkedHashMap<Player, Integer>();
        for (Map.Entry<Player, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private void displayLeaderBoard(Player sender, HashMap<Player, Integer> hashMap, boolean flag) {
        int counter = 10;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f<&6----------&f&l[&d&lGTACops&f&l]&6----------&f>"));
        for (Player player : hashMap.keySet()) {
            if ((counter--) != 0) {
                if (flag)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d" + player.getDisplayName() +
                            " &f: &c" + fetchDetails.getWantLvlStars(player)));
                else
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d" + player.getDisplayName() +
                            " &f: &c" + fetchDetails.getKills(player)));
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f<&6----------&f&l[&d&lGTACops&f&l]&6----------&f>"));
    }
}
