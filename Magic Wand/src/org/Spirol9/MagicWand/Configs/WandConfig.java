package org.Spirol9.MagicWand.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class WandConfig implements Plugin {
  
  public static File newConfig;
  public static FileConfiguration Wand;

  public static void WandSave() {
    try {
      Wand.save(newConfig);
    } catch (IOException e) {
      e.printStackTrace();
      
    }
  }
  
  public static void WandDefaults() {
    if(!Wand.contains("Wand"))
    {
        try {
            Wand.addDefault("Wand.itemName", "&d&lMagic &9&lWand");
            Wand.addDefault("Wand.item", "STICK");
            Wand.addDefault("Wand.lore1", "&9&kMagic");
            Wand.addDefault("Wand.lore2", "&a&kMagic");
            Wand.addDefault("Wand.lore3", "&d&kMagic");
            Wand.addDefault("Wand.coolDown", 5);
            Wand.options().copyDefaults(true);
            Wand.save(newConfig);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

  public static void Wandload() {
    try {
      Wand.load(newConfig);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
