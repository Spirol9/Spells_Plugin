package org.Spirol9.MagicWand.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class MessagesConfig implements Plugin {

  public static File newConfig;
  public static FileConfiguration Messages;

  public static void MessagesSave() {
    try {
      Messages.save(newConfig);
    } catch (IOException e) {
      e.printStackTrace();

    }
  }

  public static void MessagesDefaults() {
    if(!Messages.contains("Messages"))
    {
      try {
        Messages.addDefault("Messages.noXP1", "&8&lNot Enough XP Levels To Cast Spell.");
        Messages.addDefault("Messages.noXP2", "&a&l %xp%  XP &8Needed To Cast %spell%.");
        Messages.addDefault("Messages.coolDown", "&d&l Please Wait &f %time% &d&l Sec. Between Casting A Spell.");
        Messages.addDefault("Messages.spellSelected", "&8 Selected Spell %spell%.");
        MessagesConfig.Messages.addDefault("ColorCodes", "https://proxy.spigotmc.org/9b807c84f6dabfe63ef9c8ca915f69f89acb9cb6?url=http%3A%2F%2Fwww11.pic-upload.de%2F21.07.15%2Fvwk4qs2sng4u.png");
        Messages.options().copyDefaults(true);
        Messages.save(newConfig);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static void Messagesload() {
    try {
      Messages.load(newConfig);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}