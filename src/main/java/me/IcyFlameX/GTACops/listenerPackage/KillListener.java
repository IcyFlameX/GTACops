package me.IcyFlameX.GTACops.listenerPackage;

import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.mechanics.CopsFeature;
import me.IcyFlameX.GTACops.utilities.UpdateKillWant;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class KillListener implements Listener {

    private Main plugin;
    private UpdateKillWant updateKillWant;
    private CopsFeature copsFeature;
    private HashMap<Player, ArrayList<PigZombie>> playerCopsMap;

    public KillListener(Main plugin) {
        this.plugin = plugin;
        updateKillWant = new UpdateKillWant(this.plugin);
        copsFeature = new CopsFeature(this.plugin);
        playerCopsMap = new HashMap<Player, ArrayList<PigZombie>>();
    }

    @EventHandler
    public void onPlayerDeathbyCops(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player dead = (Player) event.getEntity();
            if (dead.getHealth() <= event.getDamage()) {
                if (event.getDamager() instanceof Player) {
                    Player killer = (Player) event.getDamager();
                    updateKillWant.updateKill(killer, dead);
                    copsFeature.spawnCops(killer, playerCopsMap);
                }
                if (event.getDamager() instanceof PigZombie) {
                    updateKillWant.resetKillWant(dead);
                    copsFeature.killCops(dead, playerCopsMap);
                }
            }
        }
    }
}


