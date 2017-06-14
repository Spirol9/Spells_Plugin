package org.Spirol9.MagicWand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.Spirol9.MagicWand.Configs.MessagesConfig;
import org.Spirol9.MagicWand.Configs.PlayersConfig;
import org.Spirol9.MagicWand.Configs.SpellsConfig;
import org.Spirol9.MagicWand.Configs.WandConfig;
import org.Spirol9.MagicWand.Listeners.Mobs;
import org.Spirol9.MagicWand.Listeners.onWandUse;
import org.Spirol9.MagicWand.Spells.Spells;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

  private Plugin plugin;

  File file;
  File Config;

  Spells spells;

  @Override
  public void onEnable()
  {
    plugin = this;

    // /////////////////////////////////////
    // // [ Listeners ] ////
    // /////////////////////////////////////

    Bukkit.getServer().getPluginManager().registerEvents(this, this);
    Bukkit.getServer().getPluginManager().registerEvents(new onWandUse(this), this);
    //Bukkit.getServer().getPluginManager().registerEvents(new Mobs(this), this);

    // /////////////////////////////
    // // [ Commands ] ////
    // /////////////////////////////

    getCommand("wand").setExecutor(new Wand());
    getCommand("bind").setExecutor(new Bind());



    // ////////////////////////
    // // [ Config ] ///
    // ////////////////////////
    
    getConfigFileConfiguration4("Messages");
    getConfigFileConfiguration3("Players");
    getConfigFileConfiguration("Wand");
    getConfigFileConfiguration2("Spells");

    // ////////////////
    // // [ Other ] ///
    // ////////////////

    spells = new Spells(plugin);

    for(Player p : Bukkit.getOnlinePlayers())
    {
      if(Bukkit.getOnlinePlayers().size() > 0)
      {
        if(PlayersConfig.Player.contains(String.valueOf(p.getUniqueId())))
        {
          ArrayList<String> addSpells = new ArrayList<String>();

          for(String getSpells : PlayersConfig.Player.getStringList(String.valueOf(p.getUniqueId()) + ".Spells"))
          {
            addSpells.add(getSpells);

            onWandUse.spells.put(p.getUniqueId(), addSpells);
            onWandUse.spellCount.put(p.getUniqueId(), 0);
            onWandUse.selected.put(p.getUniqueId(), true);
            onWandUse.ready.put(p.getUniqueId(), true);
          }
        }
        else
        {
          String[] saveSpells = {"heal"};

          PlayersConfig.Player.set(String.valueOf(p.getUniqueId()) + ".Spells", saveSpells);
          PlayersConfig.PlayerSave();
        }
      }
    }
    
    Spells.flyParticles();
  }

  @Override
  public void onDisable()
  {
    for(Player p : Bukkit.getOnlinePlayers())
    {
      if(Bukkit.getOnlinePlayers().size() > 0)
      {
        if(PlayersConfig.Player.contains(String.valueOf(p.getUniqueId())))
        {
          PlayersConfig.Player.set(String.valueOf(p.getUniqueId()) + ".Spells", onWandUse.spells.get(p.getUniqueId()));
          PlayersConfig.PlayerSave();
        }
        else
        {
          String[] saveSpells = {"heal"};

          PlayersConfig.Player.set(String.valueOf(p.getUniqueId()) + ".Spells", saveSpells);
          PlayersConfig.PlayerSave();
        }
      }
    }
  }

  private FileConfiguration getConfigFileConfiguration(String fileName) {
    file = new File(getDataFolder(), fileName + ".yml");
    WandConfig.newConfig = file;
    FileConfiguration fileConfiguration = new YamlConfiguration();
    WandConfig.Wand = fileConfiguration;

    try {
      fileConfiguration.load(file);
      WandConfig.WandDefaults();
    } catch (IOException | InvalidConfigurationException e) {
      getLogger().info("Generating fresh configuration file: " + fileName + ".yml");
    }

    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        InputStream in = getResource(fileName + ".yml");
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
          out.write(buf, 0, len);
        out.close();
        in.close();
      }
      fileConfiguration.load(file);
      WandConfig.WandDefaults();
    } catch (IOException | InvalidConfigurationException ex) {
      getLogger().severe("Plugin unable to write configuration file " + fileName + ".yml!");
      getLogger().severe("Disabling...");
      getServer().getPluginManager().disablePlugin(this);
      ex.printStackTrace();
    }

    return fileConfiguration;
  }
  private FileConfiguration getConfigFileConfiguration2(String fileName) {
    file = new File(getDataFolder(), fileName + ".yml");
    SpellsConfig.newConfig = file;
    FileConfiguration fileConfiguration = new YamlConfiguration();
    SpellsConfig.Spells = fileConfiguration;

    try {
      fileConfiguration.load(file);
      SpellsConfig.SpellsDefaults();
    } catch (IOException | InvalidConfigurationException e) {
      getLogger().info("Generating fresh configuration file: " + fileName + ".yml");
    }

    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        InputStream in = getResource(fileName + ".yml");
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
          out.write(buf, 0, len);
        out.close();
        in.close();
      }
      fileConfiguration.load(file);
      SpellsConfig.SpellsDefaults();
    } catch (IOException | InvalidConfigurationException ex) {
      getLogger().severe("Plugin unable to write configuration file " + fileName + ".yml!");
      getLogger().severe("Disabling...");
      getServer().getPluginManager().disablePlugin(this);
      ex.printStackTrace();
    }

    return fileConfiguration;
  }
  private FileConfiguration getConfigFileConfiguration3(String fileName) {
    file = new File(getDataFolder(), fileName + ".yml");
    PlayersConfig.newConfig = file;
    FileConfiguration fileConfiguration = new YamlConfiguration();
    PlayersConfig.Player = fileConfiguration;

    try {
      fileConfiguration.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      getLogger().info("Generating fresh configuration file: " + fileName + ".yml");
    }

    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        InputStream in = getResource(fileName + ".yml");
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
          out.write(buf, 0, len);
        out.close();
        in.close();
      }
      fileConfiguration.load(file);
    } catch (IOException | InvalidConfigurationException ex) {
      getLogger().severe("Plugin unable to write configuration file " + fileName + ".yml!");
      getLogger().severe("Disabling...");
      getServer().getPluginManager().disablePlugin(this);
      ex.printStackTrace();
    }

    return fileConfiguration;
  }
  private FileConfiguration getConfigFileConfiguration4(String fileName) {
    file = new File(getDataFolder(), fileName + ".yml");
    MessagesConfig.newConfig = file;
    FileConfiguration fileConfiguration = new YamlConfiguration();
    MessagesConfig.Messages = fileConfiguration;

    try {
      fileConfiguration.load(file);
      MessagesConfig.MessagesDefaults();
    } catch (IOException | InvalidConfigurationException e) {
      getLogger().info("Generating fresh configuration file: " + fileName + ".yml");
    }

    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        InputStream in = getResource(fileName + ".yml");
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
          out.write(buf, 0, len);
        out.close();
        in.close();
      }
      fileConfiguration.load(file);
      MessagesConfig.MessagesDefaults();
    } catch (IOException | InvalidConfigurationException ex) {
      getLogger().severe("Plugin unable to write configuration file " + fileName + ".yml!");
      getLogger().severe("Disabling...");
      getServer().getPluginManager().disablePlugin(this);
      ex.printStackTrace();
    }

    return fileConfiguration;
  }
}
