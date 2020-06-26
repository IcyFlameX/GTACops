package me.IcyFlameX.GTACops.listenerPackage;

import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.mechanics.CopsFeature;
import me.IcyFlameX.GTACops.mechanics.CustomSign;
import me.IcyFlameX.GTACops.mechanics.Tracker;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import me.IcyFlameX.GTACops.utilities.PayFine;
import me.IcyFlameX.GTACops.utilities.UpdateKillWant;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
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
    private PayFine payFine;
    private CustomSign customSign;
    private HashMap<Player, ArrayList<PigZombie>> playerCopsMap;

    public KillListener(Main plugin) {
        this.plugin = plugin;
        updateKillWant = new UpdateKillWant(this.plugin);
        copsFeature = new CopsFeature(this.plugin);
        tracker = new Tracker(this.plugin);
        payFine = new PayFine(this.plugin);
        customSign = new CustomSign(this.plugin);
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
                if (event.getSlot() == 0) {
                    payFine.reduceWantLevel(player, true, 0);
                    player.closeInventory();
                } else if (event.getSlot() == 4) {
                    tracker.trackPlayerDown(player);
                    player.closeInventory();
                } else if (event.getSlot() == 8)
                    player.closeInventory();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        customSign.changeSign(event, event.getPlayer());
    }


    @EventHandler
    public void onMouseClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        if (item != null && item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigFileManager().getMsgConfigFile().getString("GTACops_Gui.MainPanel.Compass.Name") + ""))) {
            if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX
                        + plugin.getConfigFileManager().
                        getMsgConfigFile().getString("Track_Player_Dist") + tracker.getDistOfNearestWanted(player)));
            else if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX
                        + plugin.getConfigFileManager().
                        getMsgConfigFile().getString("Track_NoLonger")));
                tracker.stopTracking(player);
            }
        }
        if (action.equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            if (ChatColor.translateAlternateColorCodes('&', CommandManager.TITLE).equals(sign.getLine(0)) &&
                    ChatColor.translateAlternateColorCodes('&', CustomSign.SIGNPAYFINE).equals(sign.getLine(2))
                    && sign.getLine(1).startsWith("$") && sign.getLine(1).substring(1).matches("[0-9]+"))
                customSign.deductFromPlayer(sign, player);
        }
    }
}