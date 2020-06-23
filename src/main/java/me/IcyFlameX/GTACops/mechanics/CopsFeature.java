package me.IcyFlameX.GTACops.mechanics;

import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CopsFeature {

    private Main plugin;

    public CopsFeature(Main plugin) {
        this.plugin = plugin;
    }

    //Responsible For Spawning Cops for the killer
    public void spawnCops(Player killer, HashMap<Player, ArrayList<PigZombie>> playerCopsMap) {
        CustomCops cops = new CustomCops(this.plugin);
        PigZombie pigZombie = ((PigZombie) killer.getWorld().spawnEntity(killer.getLocation(), EntityType.PIG_ZOMBIE));
        cops.setProperties(pigZombie);
        if (playerCopsMap.containsKey(killer)) {
            playerCopsMap.get(killer).add(pigZombie);
        } else
            playerCopsMap.put(killer, new ArrayList<PigZombie>(Arrays.asList(pigZombie)));
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
}

