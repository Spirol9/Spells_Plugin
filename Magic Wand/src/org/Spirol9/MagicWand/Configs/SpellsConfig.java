package org.Spirol9.MagicWand.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class SpellsConfig implements Plugin {

  public static File newConfig;
  public static FileConfiguration Spells;

  public static void SpellsSave() {
    try {
      Spells.save(newConfig);
    } catch (IOException e) {
      e.printStackTrace();

    }
  }

  public static void SpellsDefaults() {
    if(!Spells.contains("Spells"))
    {
      try {
        Spells.addDefault("Spells.Heal.Cost", 5);
        Spells.addDefault("Spells.Confuse.Cost", 5);
        Spells.addDefault("Spells.Launch.Cost", 5);
        Spells.addDefault("Spells.Spark.Cost", 5);
        Spells.addDefault("Spells.Spawn Skeleton.Cost", 5);
        Spells.addDefault("Spells.Spawn Zombie.Cost", 5);
        Spells.addDefault("Spells.Speed Wave.Cost", 5);
        Spells.addDefault("Spells.Poison Wave.Cost", 5);
        Spells.addDefault("Spells.Fire Comet.Cost", 5);
        Spells.addDefault("Spells.Aura.Cost", 5);
        Spells.addDefault("Spells.Lightning.Cost", 5);
        Spells.addDefault("Spells.Fly.Cost", 5);
        Spells.options().copyDefaults(true);
        Spells.save(newConfig);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static void Spellsload() {
    try {
      Spells.load(newConfig);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
