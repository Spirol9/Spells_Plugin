package org.Spirol9.MagicWand;

import org.Spirol9.MagicWand.Util.ReturnMsgs;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Wand implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(sender instanceof ConsoleCommandSender)
    {
      Bukkit.getLogger().info("Can Only be Run By Player | Magic Wand");
    }

    if(sender instanceof Player) {

      Player p = (Player) sender;

      if(args.length == 0)
      {
        if(p.hasPermission("MagicWand.wand"))
        {
          ReturnMsgs.getWand(p);
        }
        else
        {
          ReturnMsgs.noPerm(p);
        }
        return true;
      }

      else if(args.length == 1)
      {
        if(args[0].equalsIgnoreCase("help"))
        {
          if(p.hasPermission("MagicWand.help"))
          {

            ReturnMsgs.helpCommand(p);
          }
          else
          {
            ReturnMsgs.noPerm(p);
          }
        }
        else if(args[0].equalsIgnoreCase("list"))
        {
          if(p.hasPermission("MagicWand.list"))
          {
            ReturnMsgs.spellsList(p);
          }
          else
          {
            ReturnMsgs.noPerm(p);
          }
        }
        else if(args[0].equalsIgnoreCase("reload"))
        {
          ReturnMsgs.reload(p);
        }
        return true;

      }else{

        if(args[0].equalsIgnoreCase("help"))
        {
          if(p.hasPermission("MagicWand.help"))
          {
            ReturnMsgs.usage(p, "/wand help");
          }
          else
          {
            ReturnMsgs.noPerm(p);
          }
        }
        else if(args[0].equalsIgnoreCase("list"))
        {
          if(p.hasPermission("MagicWand.list"))
          {
          ReturnMsgs.usage(p, "/wand list");
          }
          else
          {
            ReturnMsgs.noPerm(p);
          }
        }
        else if(args[0].equalsIgnoreCase("reload"))
        {
          ReturnMsgs.usage(p, "/wand reload");
        }
      }
    }
    return false;
  }
}
