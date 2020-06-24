package me.IcyFlameX.GTACops.mechanics;

import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CopsFeature {

    private Main plugin;
    private FetchDetails fetchDetails;

    public CopsFeature(Main plugin) {
        this.plugin = plugin;
        fetchDetails = new FetchDetails(this.plugin);
    }

    //Responsible For Spawning Cops for the killer
    public void spawnCops(Player killer, HashMap<Player, ArrayList<PigZombie>> playerCopsMap) {
        int level = fetchDetails.getWantLvl(killer);
        CustomCops cops = new CustomCops(this.plugin);
        if (!playerCopsMap.containsKey(killer))
            playerCopsMap.put(killer, new ArrayList<PigZombie>());
        for (int i = 0; i < plugin.getConfigFileManager().getConfigFileConfig().getInt("Total_Cops.Level" + level); i++) {
            PigZombie pigZombie = ((PigZombie) killer.getWorld().spawnEntity(killer.getLocation(), EntityType.PIG_ZOMBIE));
            cops.setBasicProperties(pigZombie, fetchDetails.getWantLvl(killer));
            playerCopsMap.get(killer).add(pigZombie);
        }
    }

    //Responsible for killing the Cops associated with the player wh dies
    public void killCops(Player dead, HashMap<Player, ArrayList<PigZombie>> playerCopsMap) {
        if (playerCopsMap.containsKey(dead)) {
            for (PigZombie pigZombie : playerCopsMap.get(dead))
                pigZombie.setHealth(0);
            playerCopsMap.get(dead).clear();
            playerCopsMap.remove(dead);
        }
    }

    public void killCopsAfterTime(final Player killer, final HashMap<Player, ArrayList<PigZombie>> playerCopsMap) {
        if (playerCopsMap.containsKey(killer))
            Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                public void run() {
                    killCops(killer, playerCopsMap);
                }
            }, this.plugin.getConfigFileManager().getConfigFileConfig().getInt(
                    ("Cops_Time.Level" + fetchDetails.getWantLvl(killer))) * 20);

    }

    public void removeCopsUponDeath(Player killer, HashMap<Player, ArrayList<PigZombie>> playerCopsMap, PigZombie pigZombie) {
        if (playerCopsMap.containsKey(killer)) {
            if (!playerCopsMap.get(killer).isEmpty()) {
                if (playerCopsMap.get(killer).contains(pigZombie))
                    playerCopsMap.get(killer).remove(pigZombie);
            } else
                playerCopsMap.remove(killer);
        }
    }
}

