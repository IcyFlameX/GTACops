package me.IcyFlameX.GTACops.dataManager;

import me.IcyFlameX.GTACops.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigFileManager {
    private Main plugin;
    private File statsFile = null, configFile = null, msgsFile = null;
    private FileConfiguration statsFileConfig = null;
    private FileConfiguration configFileConfig = null;
    private FileConfiguration msgConfigFile = null;

    public ConfigFileManager(Main plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        //================ Stats.yml ================//
        if (this.statsFile == null)
            this.statsFile = new File(this.plugin.getDataFolder(), "Stats.yml");
        this.statsFileConfig = YamlConfiguration.loadConfiguration(this.statsFile);
        InputStream defaultStream = this.plugin.getResource("Stats.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.statsFileConfig.setDefaults(defaultConfig);
        }
        //================ Config.yml ================//
        if (this.configFileConfig != null || this.configFile != null) {
            configFileConfig = YamlConfiguration.loadConfiguration(configFile);
        }
        //================ Messages.yml ================//
        if (this.msgConfigFile != null || this.msgsFile != null) {
            msgConfigFile = YamlConfiguration.loadConfiguration(msgsFile);
        } else {
            this.plugin.getLogger().log(Level.SEVERE, "Could Not Reload Config! Try Restarting the Server!");
        }
    }


    public void saveDefaultConfig() {
        //================ Stats.yml ================//
        if (this.statsFile == null) {
            this.statsFile = new File(this.plugin.getDataFolder(), "Stats.yml");
        }
        if (!this.statsFile.exists())
            plugin.saveResource("Stats.yml", false);
        //================ Config.yml ================//
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "Config.yml");
        if (!this.configFile.exists())
            plugin.saveResource("Config.yml", false);
        this.configFileConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream configStream = this.plugin.getResource("Config.yml");
        if (configStream != null) {
            YamlConfiguration configYaml = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream));
            this.configFileConfig.setDefaults(configYaml);
        }
        //================ Messages.yml ================//
        if (this.msgsFile == null)
            this.msgsFile = new File(this.plugin.getDataFolder(), "Messages.yml");
        if (!this.msgsFile.exists())
            plugin.saveResource("Messages.yml", false);
        this.msgConfigFile = YamlConfiguration.loadConfiguration(this.msgsFile);
        InputStream msgsStream = this.plugin.getResource("Messages.yml");
        if (msgsStream != null) {
            YamlConfiguration msgsYaml = YamlConfiguration.loadConfiguration(new InputStreamReader(msgsStream));
            this.msgConfigFile.setDefaults(msgsYaml);
        }
    }

    public void saveStatsFile() {
        if (!(this.statsFileConfig == null || this.statsFile == null)) {
            try {
                this.getStatsFileConfig().save(statsFile);
            } catch (IOException e) {
                this.plugin.getLogger().log(Level.SEVERE, "Could not Save Stats in Stats.yml file");
            }
        }
    }

    public FileConfiguration getConfigFileConfig() {
        if (this.configFileConfig == null)
            reloadConfig();
        return this.configFileConfig;
    }

    public FileConfiguration getStatsFileConfig() {
        if (this.statsFileConfig == null)
            reloadConfig();
        return this.statsFileConfig;
    }

    public FileConfiguration getMsgConfigFile() {
        if (this.msgConfigFile == null)
            reloadConfig();
        return this.msgConfigFile;
    }
}
