package me.IcyFlameX.GTACops.mechanics;


import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.Material;
import org.bukkit.entity.PigZombie;
import org.bukkit.inventory.ItemStack;

public class CustomCops {

    private Main plugin;

    public CustomCops(Main plugin) {
        this.plugin = plugin;
    }

    public void setProperties(PigZombie pigZombie) {
        pigZombie.setAnger(2);
        pigZombie.setAngry(true);
        pigZombie.setCustomName("Cops");
        pigZombie.setCustomNameVisible(true);
        pigZombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
    }
}
