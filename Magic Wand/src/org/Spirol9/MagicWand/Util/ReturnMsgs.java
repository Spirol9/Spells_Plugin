package org.Spirol9.MagicWand.Util;

import java.util.ArrayList;

import org.Spirol9.MagicWand.Configs.MessagesConfig;
import org.Spirol9.MagicWand.Configs.PlayersConfig;
import org.Spirol9.MagicWand.Configs.SpellsConfig;
import org.Spirol9.MagicWand.Configs.WandConfig;
import org.Spirol9.MagicWand.Listeners.onWandUse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReturnMsgs {

  static ChatColor pink = ChatColor.LIGHT_PURPLE;
  static ChatColor white = ChatColor.WHITE;
  static ChatColor lGray = ChatColor.GRAY;
  static ChatColor bold = ChatColor.BOLD;
  static ChatColor reset = ChatColor.RESET;

  public static void helpCommand(Player p) {

    p.sendMessage("");
    p.sendMessage(pink + "===================" + "{" + reset + white + "Magic Wand" + bold + "" + pink
      + "}" + "=======================");

    if(p.hasPermission("MagicWand.Admin")) {
      p.sendMessage("");
      p.sendMessage(white + "/wand" + pink + " - " + lGray + "" + bold
        + "Get Wand.");
      p.sendMessage("");
      p.sendMessage(white + "/bind (Spell)" + pink + " - " + lGray + "" + bold
        + "Binds Spell To Wand.");
      p.sendMessage("");
      p.sendMessage(white + "/wand list" + pink + " - " + lGray + "" + bold
        + "See All Avalible Spells.");
      p.sendMessage("");
      p.sendMessage(white + "/wand reload" + pink + " - " + lGray + "" + bold
        + "Reload The Plugin's Configuration.");
    } else {
      p.sendMessage(white + "/wand" + pink + " - " + lGray + "" + bold
        + "Get Wand.");
      p.sendMessage("");
      p.sendMessage(white + "/bind (Spell)" + pink + " - " + lGray + "" + bold
        + "Binds Spell To Wand.");
      p.sendMessage("");
      p.sendMessage(white + "/wand list" + pink + " - " + lGray + "" + bold
        + "See All Avalible Spells.");
      p.sendMessage("");
    }

    p.sendMessage("");
    p.sendMessage(pink + "=====================================================");
  }

  public static void spellsList(Player p) {

    for(String Spells : SpellsConfig.Spells.getConfigurationSection("Spells").getKeys(false))
    {
      p.sendMessage(pink+ "* " +lGray+ Spells);
    }

    
    
  }

  public static void getWand(Player p)
  {
    ItemStack Wand = new ItemStack(Material.getMaterial(WandConfig.Wand.getString("Wand.item")));
    ItemMeta im = Wand.getItemMeta();
    ArrayList<String> loreList = new ArrayList<String>();
    loreList.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore1"))));
    loreList.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore2"))));
    loreList.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore3"))));
    im.setLore(loreList);
    im.setDisplayName(ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.itemName")));
    Wand.setItemMeta(im);

    if(p.getInventory().contains(Wand)|| p.getInventory().getItemInOffHand().equals(Wand))
    {

      p.sendMessage(lGray + "You Currently Already Have A Wand.");
      
    }
    else if (!p.getInventory().contains(Wand)|| !p.getInventory().getItemInOffHand().equals(Wand))
    {
      p.getInventory().setItem(0, Wand);
      p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 20f,10f);
    }
  }

  public static void bindSpell(Player p, String spell) {

    if(configContains("Spells."+ spell) == true)
    {

      ItemStack Wand = new ItemStack(Material.getMaterial(WandConfig.Wand.getString("Wand.item")));
      ItemMeta im2 = Wand.getItemMeta();
      ArrayList<String> loreList2 = new ArrayList<String>();
      loreList2.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore1"))));
      loreList2.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore2"))));
      loreList2.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore3"))));
      im2.setLore(loreList2);
      im2.setDisplayName(ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.itemName")));
      Wand.setItemMeta(im2);

      if(p.getInventory().contains(Wand)|| p.getInventory().getItemInOffHand().equals(Wand))
      {

        p.giveExpLevels(-5);

        if(onWandUse.spells.containsKey(p.getUniqueId()))
        {

          ArrayList<String> addNewSpell = onWandUse.spells.get(p.getUniqueId());

          onWandUse.spells.get(p.getUniqueId()).add(spell);


          onWandUse.spells.replace(p.getUniqueId(), addNewSpell);
          onWandUse.selected.replace(p.getUniqueId(), false);
        }
        else
        {
          ArrayList<String> addNewSpell = new ArrayList<String>();

          addNewSpell.add(spell);

          onWandUse.spells.put(p.getUniqueId(), addNewSpell);
          onWandUse.spellCount.put(p.getUniqueId(), -1);
          onWandUse.selected.put(p.getUniqueId(), false);
          onWandUse.ready.put(p.getUniqueId(), false);
        }        

        p.sendMessage(lGray + "Bound Spell " +pink+ spell +lGray+ ".");

      }
      else
      {
        noWand(p);
      }
    }
    else
    {
      inValidSpell(p, spell);
    }
  }

  public static boolean configContains(String arg){
    boolean boo = false;
    ArrayList<String> keys = new ArrayList<String>();
    keys.addAll(SpellsConfig.Spells.getKeys(true));
    for(int i = 0; i < keys.size(); i++){
      if(keys.get(i).equalsIgnoreCase(arg)){
        boo = true;
      }
    }
    if(boo){
      return true;
    } else {
      return false;
    }
  }

  public static void reload(Player p) {

    if(p.hasPermission("MagicWand.Admin")) {
      WandConfig.Wandload();
      SpellsConfig.Spellsload();
      PlayersConfig.Playerload();

      p.sendMessage(lGray + "Reloaded Configurations.");
      
    }
    else
    {
      noPerm(p);
    }
  }

  public static void noSpellBound(Player p) {

    p.sendMessage(lGray + "No Spell Bound To Wand.");

  }

  public static void notSelected(Player p) {    

    p.sendMessage(lGray + "No Selected Spell To Cast.");
    
  }

  public static void selected(Player p, String spell) {
    
    
    String selected = MessagesConfig.Messages.getString("Messages.spellSelected").replaceAll("%spell%", spell);

    p.sendMessage(ChatColor.translateAlternateColorCodes('&', selected));

  }

  public static void suffSelected(Player p, String spell) {

    
    p.sendMessage(pink + "===================" + "{" + reset + white + "Magic Wand" + bold + "" + pink
      + "}" + "=======================");
    

    p.sendMessage(lGray + "Already Selected Spell " + spell + ".");

    
    
  }

  public static void suffBound(Player p, String spell) {

    p.sendMessage(lGray + "Already Bound Spell " + spell + ".");
    
  }

  public static void noExp(Player p, int exp, String spell) {
  
    
    String noExp3 = MessagesConfig.Messages.getString("Messages.noXP2").replaceAll("%xp%", String.valueOf(exp));
    String noExp4 = noExp3.replaceAll("%spell%", spell);

    p.sendMessage(lGray + "" + bold + "Not Enough Exp To Do That");
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', noExp4));
    
    String noExp1 = MessagesConfig.Messages.getString("Messages.noXP1").replaceAll("%xp%", String.valueOf(exp));
    String noExp2 = noExp1.replaceAll("%spell%", spell);
    
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', noExp2));
    
  }

  private static void inValidSpell(Player p, String Spell) {    

    p.sendMessage(pink + "No Spells By The Name: " + white + Spell);

  }

  private static void noWand(Player p) {    

    p.sendMessage(lGray + "No Wand To Bind Spell With.");

  }

  public static void noPerm(Player p) {    

    p.sendMessage(white + "You do not have permission to do that.");
    
  }

  public static void coolDown(Player p) {
    

    String coolDown = MessagesConfig.Messages.getString("Messages.coolDown").replaceAll("%time%", String.valueOf(WandConfig.Wand.getInt("Wand.coolDown")));
    
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', coolDown));
    
  }

  public static void usage(Player p, String usage) {
    
    p.sendMessage(pink+ "Too Many Or Few Args.");
    
    p.sendMessage(lGray+ "Try: " +pink+ "- " +white+ usage);

  }
}
