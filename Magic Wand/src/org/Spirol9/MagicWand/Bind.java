package org.Spirol9.MagicWand;

import org.Spirol9.MagicWand.Util.ReturnMsgs;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Bind implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(sender instanceof ConsoleCommandSender)
    {
      Bukkit.getLogger().info("Can Only be Run By Player | Magic Wand");
    }

    if(sender instanceof Player) {

      Player p = (Player) sender;

      if(args.length > 0)
      {
        if(p.hasPermission("MagicWand.bind"))
        {
          StringBuilder message = new StringBuilder(args[0]);
          for (int arg = 1; arg < args.length; arg++) {
            message.append(" ").append(args[arg]);
          }
          ReturnMsgs.bindSpell(p, String.valueOf(message));
        }
        else
        {
          ReturnMsgs.noPerm(p);
        }
        return true;

      }else{

        ReturnMsgs.usage(p, "/bind (Spell Name)");
      }
    }
    return false;
  }
}
