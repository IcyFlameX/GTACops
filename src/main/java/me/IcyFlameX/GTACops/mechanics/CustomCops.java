package me.IcyFlameX.GTACops.mechanics;


import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.PigZombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class CustomCops {

    private Main plugin;

    public CustomCops(Main plugin) {
        this.plugin = plugin;
    }

    public void setBasicProperties(PigZombie pigZombie, int level) {
        pigZombie.setCustomName(ChatColor.translateAlternateColorCodes('&', "" +
                plugin.getConfigFileManager().getConfigFileConfig().getString("Cops_Name")));
        pigZombie.setCustomNameVisible(true);
        Objects.requireNonNull(pigZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(
                plugin.getConfigFileManager().getConfigFileConfig().getDouble("Cops_MaxHealth"));
        pigZombie.setHealth(plugin.getConfigFileManager().getConfigFileConfig().getDouble("Cops_Health.Level" + level));
        pigZombie.setAngry(true);
        pigZombie.setRemoveWhenFarAway(true);
        pigZombie.setBaby(false);
        setPhysicalProperties(pigZombie, level);
    }

    private void setPhysicalProperties(PigZombie pigZombie, int level) {
        FileConfiguration config = plugin.getConfigFileManager().getConfigFileConfig();
        Objects.requireNonNull(pigZombie.getEquipment()).setItemInMainHand(new ItemStack(Objects.requireNonNull(Material.getMaterial(
                Objects.requireNonNull(config.getString("Cops_Weapon.Level" + level))))));
        pigZombie.getEquipment().setHelmet(new ItemStack(Objects.requireNonNull(Material.getMaterial(
                Objects.requireNonNull(config.getString("Cops_Helmet.Level" + level))))));
        pigZombie.getEquipment().setChestplate(new ItemStack(Objects.requireNonNull(Material.getMaterial(
                Objects.requireNonNull(config.getString("Cops_Chestplate.Level" + level))))));
        pigZombie.getEquipment().setLeggings(new ItemStack(Objects.requireNonNull(Material.getMaterial(
                Objects.requireNonNull(config.getString("Cops_Leggings.Level" + level))))));
        pigZombie.getEquipment().setBoots(new ItemStack(Objects.requireNonNull(Material.getMaterial(
                Objects.requireNonNull(config.getString("Cops_Boots.Level" + level))))));
        pigZombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                config.getInt("Cops_Speed.Duration.Level" + level) * 20,
                config.getInt("Cops_Speed.Intensity.Level" + level)));
        pigZombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
                config.getInt("Cops_Strength.Duration.Level" + level) * 20,
                config.getInt("Cops_Strength.Intensity.Level" + level)));
    }
}
