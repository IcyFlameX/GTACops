package me.IcyFlameX.GTACops.listenerPackage;

import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.mechanics.CopsFeature;
import me.IcyFlameX.GTACops.mechanics.GUIClass;
import me.IcyFlameX.GTACops.mechanics.Tracker;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import me.IcyFlameX.GTACops.utilities.UpdateKillWant;
import org.bukkit.ChatColor;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class KillListener implements Listener {

    private Main plugin;
    private UpdateKillWant updateKillWant;
    private CopsFeature copsFeature;
    private Tracker tracker;
    private HashMap<Player, ArrayList<PigZombie>> playerCopsMap;

    public KillListener(Main plugin) {
        this.plugin = plugin;
        updateKillWant = new UpdateKillWant(this.plugin);
        copsFeature = new CopsFeature(this.plugin);
        tracker = new Tracker(this.plugin);
        playerCopsMap = new HashMap<Player, ArrayList<PigZombie>>();
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player dead = (Player) event.getEntity();
            if (dead.getHealth() <= event.getDamage()) {
                if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                    Player killer = (Player) event.getDamager();
                    updateKillWant.updateKill(killer, dead);
                    if (!(killer.hasPermission("GTACops.user.bypass"))) {
                        copsFeature.spawnCops(killer, playerCopsMap);
                        copsFeature.killCopsAfterTime(killer, playerCopsMap);
                    }
                    copsFeature.killCops(dead, playerCopsMap);
                }
                if (event.getDamager() instanceof Player && event.getEntity() instanceof PigZombie) {
                    Player killer = (Player) event.getDamager();
                    copsFeature.removeCopsUponDeath(killer, playerCopsMap, (PigZombie) event.getEntity());
                }
                if (event.getDamager() instanceof PigZombie) {
                    updateKillWant.resetKillWant(dead);
                    copsFeature.killCops(dead, playerCopsMap);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_Gui.MainPanel.Title") + ""))) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                Player player = (Player) event.getWhoClicked();
                if (event.getSlot() == 0)
                    player.closeInventory();
                else if (event.getSlot() == 4) {
                    tracker.trackPlayerDown(player);
                    player.closeInventory();
                } else if (event.getSlot() == 8)
                    player.closeInventory();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMouseClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_Gui.MainPanel.Compass.Name") + ""))) {
            if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX
                        + plugin.getConfigFileManager().
                        getMsgConfigFile().getString("Track_Player_Dist") + tracker.getDistOfNearestWanted(player)));
        }
    }
}