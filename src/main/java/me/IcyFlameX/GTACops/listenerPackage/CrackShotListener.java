package me.IcyFlameX.GTACops.listenerPackage;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import me.IcyFlameX.GTACops.api.FetchDetails;
import me.IcyFlameX.GTACops.main.Main;
import me.IcyFlameX.GTACops.mechanics.CopsFeature;
import me.IcyFlameX.GTACops.utilities.CommandManager;
import me.IcyFlameX.GTACops.utilities.RewardsPunish;
import me.IcyFlameX.GTACops.utilities.TitleClass;
import me.IcyFlameX.GTACops.utilities.UpdateKillWant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CrackShotListener  implements Listener {

    private Main plugin;
    private FetchDetails fetchDetails;
    private UpdateKillWant updateKillWant;
    private RewardsPunish rewardsPunish;
    private TitleClass titleClass;
    private CopsFeature copsFeature;
    public CrackShotListener(Main plugin){
        this.plugin = plugin;
        fetchDetails = new FetchDetails(this.plugin);
        updateKillWant = new UpdateKillWant(this.plugin);
        rewardsPunish = new RewardsPunish(this.plugin);
        titleClass = new TitleClass(this.plugin);
        copsFeature = new CopsFeature(this.plugin);
    }

    @EventHandler
    public void WeaponDamage(WeaponDamageEntityEvent event){
        Entity deadEntity= event.getVictim();
        if (deadEntity instanceof Player) {
            Player deadPlayer = (Player) deadEntity;
            Player killer = event.getPlayer();
            if (event.getDamage()>= deadPlayer.getHealth()) {
                rewardsPunish.giveReward(killer,deadPlayer,true);
                rewardsPunish.givePunish(deadPlayer, true);
                titleClass.sendTitle(deadPlayer);
                updateKillWant.updateKill(killer,deadPlayer);
                if (plugin.getConfigFileManager().getConfigFileConfig().getBoolean("Enable_Broadcast"))
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', CommandManager.PREFIX +
                            killer.getDisplayName() + " " + plugin.getConfigFileManager().getMsgConfigFile()
                            .getString("BroadCastMessage") + fetchDetails.getWantLvlStars(killer)));
                if (!(killer.hasPermission("GTACops.user.bypass"))) {
                    copsFeature.spawnCops(killer, ListenerClass.playerCopsMap);
                    copsFeature.killCopsAfterTime(killer, ListenerClass.playerCopsMap);
                }
                copsFeature.killCops(deadPlayer, ListenerClass.playerCopsMap);
            }
        }
        if (event.getVictim() instanceof PigZombie && event.getVictim().getCustomName().equals(
                ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigFileManager().getConfigFileConfig().getString("Cops_Name") + ""))) {
                PigZombie deadCop = (PigZombie) event.getVictim();
                if (deadCop.getHealth() <= event.getDamage()) {
                    Player killer = event.getPlayer();
                    copsFeature.removeCopsUponDeath(killer, ListenerClass.playerCopsMap, deadCop);
                    rewardsPunish.giveReward(killer, null, false);
                }
        }
    }


}
