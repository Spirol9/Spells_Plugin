package org.Spirol9.MagicWand.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.Spirol9.MagicWand.Configs.PlayersConfig;
import org.Spirol9.MagicWand.Configs.SpellsConfig;
import org.Spirol9.MagicWand.Configs.WandConfig;
import org.Spirol9.MagicWand.Spells.Spells;
import org.Spirol9.MagicWand.Util.ReturnMsgs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class onWandUse implements Listener {

  private static Plugin plugin;

  public static HashMap<UUID,ArrayList<String>> spells = new HashMap<UUID,ArrayList<String>>();
  public static HashMap<UUID,Integer> spellCount = new HashMap<UUID,Integer>();
  public static HashMap<UUID,Boolean> selected = new HashMap<UUID,Boolean>();
  public static HashMap<UUID,Boolean> ready = new HashMap<UUID,Boolean>();

  public static ArrayList<UUID> coolDown = new ArrayList<UUID>();

  public onWandUse(Plugin plugin) {
    onWandUse.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
    Player p = e.getPlayer();

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

  @EventHandler
  public void onJoin(PlayerQuitEvent e)
  {
    Player p = e.getPlayer();
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

  @EventHandler
  public void onwandUse(PlayerInteractEvent e)
  {
    Player p = e.getPlayer();

    ItemStack Wand = new ItemStack(Material.getMaterial(WandConfig.Wand.getString("Wand.item")));
    ItemMeta im2 = Wand.getItemMeta();
    ArrayList<String> loreList2 = new ArrayList<String>();
    loreList2.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore1"))));
    loreList2.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore2"))));
    loreList2.add((ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.lore3"))));
    im2.setLore(loreList2);
    im2.setDisplayName(ChatColor.translateAlternateColorCodes('&',WandConfig.Wand.getString("Wand.itemName")));
    Wand.setItemMeta(im2);

    try{
      if(p.getInventory().getItemInMainHand().getType() == Wand.getType() || p.getInventory().getItemInOffHand().getType() == Wand.getType())
      {
        if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(Wand.getItemMeta().getDisplayName())|| p.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equalsIgnoreCase(Wand.getItemMeta().getDisplayName()))
        {
          if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
          {
            if(spells.containsKey(p.getUniqueId()))
            {
              if(selected.containsKey(p.getUniqueId()))
              {
                if(p.getLevel() >= SpellsConfig.Spells.getInt("Spells." + spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).substring(0, 1).toUpperCase()+spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).substring(1) + ".Cost"))
                {
                  if(selected.get(p.getUniqueId()) == true)
                  {
                    if(!coolDown.contains(p.getUniqueId()))
                    {       
                      
                      String spell = spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).substring(0, 1).toUpperCase()+spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).substring(1);
                      
                      
                      if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Heal"))
                      {
                        Spells.Heal(p);
                         
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }

                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Confuse"))
                      {
                        Spells.Confuse(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }

                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Speed Wave"))
                      {
                        Spells.speedWave(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }

                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Aura"))
                      {
                        Spells.Aura(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }

                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Lightning"))
                      {
                        Spells.Lightning(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }

                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Poison Wave"))
                      {
                        Spells.poisonWave(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }

                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Fire Comet"))
                      {
                        Spells.fireComet(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }
                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Launch"))
                      {
                        Spells.Launch(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }

                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Spark"))
                      {
                        Spells.Spark(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }
                      //else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Spawn Skeleton"))
                      //{
                        //Spells.spawnSkeleton(p);
                        
                        //p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      //}

                      //else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Spawn Zombie"))
                      //{
                        //Spells.spawnZombie(p);
                        
                       //p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      //}

                      else if(spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).equalsIgnoreCase("Fly"))
                      {
                        Spells.Fly(p);
                        
                        p.setLevel(p.getLevel() - SpellsConfig.Spells.getInt("Spells." + spell + ".Cost"));
                      }

                      coolDown.add(p.getUniqueId());

                      new BukkitRunnable()
                      {
                        public void run()
                        {
                          coolDown.remove(p.getUniqueId());
                        }
                      }.runTaskLater(plugin, WandConfig.Wand.getInt("Wand.coolDown")*20);
                    }
                    else
                    {
                      ReturnMsgs.coolDown(p);
                    }
                  }
                  else
                  {
                    ReturnMsgs.notSelected(p);
                  }
                }
                else
                {
                  ReturnMsgs.noExp(p, SpellsConfig.Spells.getInt("Spells." + spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).substring(0, 1).toUpperCase()+spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())).substring(1) + ".Cost"), spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())));
                }
              }
              else
              {
                ReturnMsgs.notSelected(p);
              }
            }
            else
            {
              ReturnMsgs.noSpellBound(p); 
            }
          }

          if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
          {
            if(spells.containsKey(p.getUniqueId()))
            {
              p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 20, 1);

              if(selected.containsKey(p.getUniqueId()))
              {
                if(spellCount.get(p.getUniqueId()) == spells.get(p.getUniqueId()).size() -1)
                {
                  selected.replace(p.getUniqueId(), true);
                  if(ready.get(p.getUniqueId()) == true)
                  {
                    spellCount.replace(p.getUniqueId(), -1);  

                    spellCount.replace(p.getUniqueId(), spellCount.get(p.getUniqueId()) + 1);


                    ReturnMsgs.selected(p, spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())));
                  }
                  else
                  {
                    ready.replace(p.getUniqueId(), true);
                  }
                }
                else
                {
                  spellCount.replace(p.getUniqueId(), spellCount.get(p.getUniqueId()) + 1);

                  ReturnMsgs.selected(p, spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId()))); 
                }
              }
              else
              {
                selected.replace(p.getUniqueId(), true);

                spellCount.replace(p.getUniqueId(), spellCount.get(p.getUniqueId()) + 1);

                ReturnMsgs.selected(p, spells.get(p.getUniqueId()).get(spellCount.get(p.getUniqueId())));
              }
            }
            else
            {
              ReturnMsgs.noSpellBound(p); 
            }
          }
        }
      }
    }catch(Exception n){}
  }
}
