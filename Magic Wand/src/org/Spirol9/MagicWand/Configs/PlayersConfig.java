package org.Spirol9.MagicWand.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class PlayersConfig implements Plugin {
  
  public static File newConfig;
  public static FileConfiguration Player;

  public static void PlayerSave() {
    try {
      Player.save(newConfig);
    } catch (IOException e) {
      e.printStackTrace();
      
    }
  }

  public static void Playerload() {
    try {
      Player.load(newConfig);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}