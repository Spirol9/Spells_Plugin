package org.Spirol9.MagicWand.Spells;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

import org.Spirol9.MagicWand.Util.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class Spells {

  private static Plugin plugin;

  public Spells(Plugin plugin){
    Spells.plugin = plugin;
  }

  public static void Heal(Player p){
    new BukkitRunnable()
    {
      float radius = 5F;
      public void run()
      {
        radius = radius -0.5f;

        if(radius == 1f)
        {
          this.cancel();
        }

        Location loc = p.getEyeLocation();

        Location location2 = loc;

        if(radius == 0.5f)
        {
          loc.subtract(0, radius- 2.5, 0);
        }

        int particles = 15;

        for (int i = 0; i < particles; i++) {
          double angle, x, z;
          angle = 2 * Math.PI * i / particles;
          x = Math.cos(angle) * radius;
          z = Math.sin(angle) * radius;
          location2.add(x, -0.66, z);
          p.getLocation().getWorld().playEffect(location2, Effect.HEART, i);
          location2.subtract(x, -0.66, z);
        } 
      }
    }.runTaskTimer(plugin, 0, 2);

    List<Entity> nbe = p.getNearbyEntities(5, 5, 5);

    for(Entity e :nbe){
      if(e instanceof Player)
      {
        ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 5, false, true, Color.RED));
        ((Player) e).playSound(e.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 20, 1);
      }
    }
    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 5, false, true, Color.RED));
    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 20, 1);  
  }

  public static void Confuse(Player p){

    new BukkitRunnable()
    {
      float radius = 0.5f;
      public void run()
      {
        radius = radius +0.5f;

        if(radius >= 6f)
        {
          this.cancel();
        }

        Location location2 = p.getEyeLocation();
        int particles = 50;

        for (int i = 0; i < particles; i++) {
          double angle, x, z;
          angle = 2 * Math.PI * i / particles;
          x = Math.cos(angle) * radius;
          z = Math.sin(angle) * radius;
          location2.add(x, -0.66, z);
          p.getLocation().getWorld().playEffect(location2, Effect.PORTAL, i);
          p.getLocation().getWorld().playEffect(location2, Effect.SMOKE, i);

          location2.subtract(x, -0.66, z);
        } 
      }
    }.runTaskTimer(plugin, 0, 1);

    List<Entity> nbe = p.getNearbyEntities(5, 5, 5);

    for(Entity e :nbe){
      if(e instanceof Player)
      {
        ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 5, true, true, Color.RED));
        ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 2, false, true, Color.SILVER));
        ((Player) e).playSound(e.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 20, 1); 
      }
    }
    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 20, 1); 
  }

  public static void Launch(Player p)
  {

    new BukkitRunnable()
    {
      int i = 0;
      public void run()
      {

        i++;

        if(i == 55){
          cancel();}
        p.getLocation().getWorld().playEffect(p.getLocation(), Effect.SPELL, 1);
        p.getLocation().getWorld().playEffect(p.getLocation(), Effect.HAPPY_VILLAGER, 1);
      }
    }.runTaskTimer(plugin, 0, 0);

    Vector dir = p.getLocation().getDirection();
    p.setVelocity(dir.setY(1.2));
    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 150, 2, false, false));
    p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_SLIME_SQUISH, 20, 1); 
  }

  @SuppressWarnings("deprecation")
  public static void Spark(Player p)
  {
    Block location = p.getTargetBlock((HashSet<Byte>) null, 100);

    Location location2 = new Location(location.getWorld(),location.getX(),location.getY()+1,location.getZ());

    int particles = 15;
    for (int i = 0; i < particles; i++) {
      float radius = 0.5F;
      double angle, x, z;
      angle = 2 * Math.PI * i / particles;
      x = Math.cos(angle) * radius;
      z = Math.sin(angle) * radius;
      location2.add(x, 0, z);
      Location loc = location2;
      PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
      try
      {
        MathUtil.setValue(packet, "a", EnumParticle.FLAME);
        MathUtil.setValue(packet, "b", (float) loc.getX());
        MathUtil.setValue(packet, "c", (float) loc.getY());
        MathUtil.setValue(packet, "d", (float) loc.getZ());
        MathUtil.setValue(packet, "e", 0);
        MathUtil.setValue(packet, "f", 0);
        MathUtil.setValue(packet, "g", 0);
        MathUtil.setValue(packet, "h", 0);
        MathUtil.setValue(packet, "i", 0);

        for (Player online : Bukkit.getOnlinePlayers()) {
          ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
        }

        location2.getWorld().spawnParticle(Particle.CRIT, location2 ,  7);
        location2.getWorld().spawnParticle(Particle.CRIT, location2 ,  3);
        location2.subtract(x, 0, z);
      } catch (Exception e) {
        
        e.printStackTrace();
      }

      Collection<Entity> players =location2.getWorld().getNearbyEntities(location2, 5d, 5d, 5d);

      for(Entity e :players){
        if(e instanceof Player)
        {
          ((Player) e).setHealth(((Player) e).getHealth() - 0.5);
        }
      }
      location2.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 20, 1);
      location2.getWorld().playSound(p.getLocation(), Sound.ENTITY_SILVERFISH_HURT, 10, 1);
    }
  }

  @SuppressWarnings("deprecation")
  public static void spawnSkeleton(Player p)
  {
	  p.getInventory().addItem(new ItemStack(Material.EMERALD));
	  
    Block location = p.getTargetBlock((HashSet<Byte>) null, 100);

    Location location2 = new Location(location.getWorld(),location.getX(),location.getY() + 2,location.getZ());

    Skeleton Skell = p.getWorld().spawn(location2, Skeleton.class);
    Skell.setGlowing(true);
  }

  @SuppressWarnings("deprecation")
  public static void spawnZombie(Player p)
  {
    Block location = p.getTargetBlock((HashSet<Byte>) null, 100);

    Location location2 = new Location(location.getWorld(),location.getX(),location.getY() + 2,location.getZ());

    Zombie Zomb = p.getWorld().spawn(location2, Zombie.class);

    Zomb.setTarget(null);
    Zomb.setGlowing(true);
    Zomb.setGravity(true);
    Zomb.setCanPickupItems(true);
    Zomb.setTarget(null);

    ItemStack BONE_MEAL = new ItemStack(Material.INK_SACK);
    BONE_MEAL.setDurability((short) 15);

    p.getInventory().addItem(BONE_MEAL);
    location2.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 20, 1);

    new BukkitRunnable()
    {
      public void run()
      {
        Zomb.setTarget(null);
      }
    }.runTaskLater(plugin, 180);
  }

  public static void speedWave(Player p)
  {
    new BukkitRunnable()
    {
      float radius = 0.0F;
      public void run()
      {
        radius = radius +0.2f;

        if(radius >= 4f)
        {
          this.cancel();
        }

        Location location2 = p.getEyeLocation();
        int particles = 7;

        for (int i = 0; i < particles; i++) {
          double angle, x, z;
          angle = 2 * Math.PI * i / particles;
          x = Math.cos(angle) * radius;
          z = Math.sin(angle) * radius;
          location2.add(x, -0.66, z);
          p.getWorld().playEffect(location2, Effect.STEP_SOUND, Material.ICE);
          p.getWorld().playEffect(location2, Effect.SPELL, i);

          location2.subtract(x, -0.66, z);
        } 
      }
    }.runTaskTimer(plugin, 0, 1);

    List<Entity> nbe = p.getNearbyEntities(5, 5, 5);

    for(Entity e :nbe){
      if(e instanceof Player)
      {
        ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 5, false, true, Color.AQUA));
      }
    }
    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, false, true, Color.AQUA));
  }

  public static void poisonWave(Player p)
  {
    new BukkitRunnable()
    {
      float i =0;
      public void run()
      {

        i = i +0.1f;

        if(i >= 4f)
        {
          this.cancel();
        }

        Location location2 = p.getEyeLocation();
        Location location1 = p.getEyeLocation();
        Location location3 = p.getEyeLocation();
        int particles = 25;


        for (int i = 0; i < particles; i++) {
          float radius = 4F;
          double angle, x, z;
          angle = 2 * Math.PI * i / particles;
          x = Math.cos(angle) * radius;
          z = Math.sin(angle) * radius;
          location1.add(x, 0, z);
          location3.add(x, -1.33, z);
          location2.add(x, -0.66, z);
          p.getLocation().getWorld().spawnParticle(Particle.SPELL, location1, 1);
          p.getLocation().getWorld().spawnParticle(Particle.FALLING_DUST, location2,  1);
          p.getLocation().getWorld().spawnParticle(Particle.SPELL, location3, 1);
          location1.subtract(x, 0, z);
          location3.subtract(x, -1.33, z);
          location2.subtract(x, -0.66, z);
        } 

        for (int i = 0; i < particles; i++) {
          float radius = 1F;
          double angle, x, z;
          angle = 2 * Math.PI * i / particles;
          x = Math.cos(angle) * radius;
          z = Math.sin(angle) * radius;
          location1.add(x, 0, z);
          location3.add(x, -1.33, z);
          location2.add(x, -0.66, z);
          p.getLocation().getWorld().spawnParticle(Particle.FALLING_DUST, location1, 1);
          p.getLocation().getWorld().spawnParticle(Particle.FALLING_DUST, location2,  1);
          p.getLocation().getWorld().spawnParticle(Particle.FALLING_DUST, location3, 1);
          location1.subtract(x, 0, z);
          location3.subtract(x, -1.33, z);
          location2.subtract(x, -0.66, z);
        }

        for (int i = 0; i < particles; i++) {
          float radius = 2F;
          double angle, x, z;
          angle = 2 * Math.PI * i / particles;
          x = Math.cos(angle) * radius;
          z = Math.sin(angle) * radius;
          location2.add(x, -0.66, z);
          p.getLocation().getWorld().spawnParticle(Particle.SPELL, location2,  1);
          location2.subtract(x, -0.66, z);
        }
        
        location2.getWorld().playSound(location3, Sound.BLOCK_GLASS_BREAK, 20, 1);
      }
    }.runTaskTimer(plugin, 0, 0);

    List<Entity> nbe = p.getNearbyEntities(5, 5, 5);

    for(Entity e :nbe){
      if(e instanceof Player)
      {
        ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2, false, true, Color.YELLOW));
        ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2, false, true, Color.SILVER));
      }
    }
  }

  @SuppressWarnings("deprecation")
  public static void fireComet(Player p)
  {
    Block location = p.getTargetBlock((HashSet<Byte>) null, 100);

    Location location1 = new Location(location.getWorld(),location.getX(),location.getY(),location.getZ());
    
    new BukkitRunnable()
    {
      int i = 0;

      Location location2 = new Location(location1.getWorld(),location1.getX(), location1.getY()+53, location1.getZ());
      Location loc = location2;
      public void run()
      {

        i++;

        if(i == 30){
          cancel();}

        if(i == 14){
          location1.add(0, 1, 0);
          
          ArmorStand  armorStand = (ArmorStand) p.getWorld().spawnEntity(location1, EntityType.ARMOR_STAND);
          armorStand.setVisible(false);
          
          for(Entity e: armorStand.getNearbyEntities(5, 1, 5)){
        	  if(e.getType()==EntityType.PLAYER){
        		  Player pth = (Player) e;
        		  pth.playEffect(EntityEffect.HURT);
        		  pth.setHealth(pth.getHealth()-3);
        	  }
          }

          for (double i = 0; i <= Math.PI; i += Math.PI / 5) {
            Math.cos(i);
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / 5) {
              double x = Math.cos(a) * 5;
              double z = Math.sin(a) * 5;
              location1.add(x, 0, z);
              if(location1.getBlock().getType() == Material.AIR)
              {
                location1.getBlock().setType(Material.FIRE);
              }
              location1.subtract(x, 0, z);
            } 
          }
          location1.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 50, 1);
          
           new BukkitRunnable(){
        	  public void run(){
        		  armorStand.remove();
        	  }
          }.runTaskLater(plugin, 60);

          
          cancel();}

        loc.subtract(0, i, 0);

        p.getPlayer().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 20, 1);
        p.getLocation().getWorld().playEffect(loc, Effect.SMOKE, 5);
        p.getLocation().getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 15);
        p.getLocation().getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 5);
      
      }
    }.runTaskTimer(plugin, 0, 3);
  }

  public static void Aura(Player p)
  {

    new BukkitRunnable()
    {
      int time = 0;

      public void run()
      {

        if(time == 20)
        {
          cancel();
        }

        List<Entity> nbe = p.getNearbyEntities(4, 4, 4);

        for(Entity e :nbe){
          if(e instanceof Player)
          {
            Vector dir = ((Player) e).getLocation().getDirection();
            ((Player) e).setVelocity(dir.setY(0.2));
          }
        }
        
        Location loc= p.getLocation();
        Location location1 = new Location(loc.getWorld(), loc.getX(),loc.getY() + 0.1, loc.getZ());

        for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
          double radius = Math.sin(i) * 3;
          double y = Math.cos(i) * 3;
          for (double a = 0; a < Math.PI * 2; a+= Math.PI /10) {
            double x = Math.cos(a) * radius;
            double z = Math.sin(a) * radius;
            location1.add(x, y, z);
            p.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location1, 1);
            location1.subtract(x, y, z);
          }
        }
        
        location1.getWorld().playSound(location1, Sound.BLOCK_PORTAL_AMBIENT, 20, 1);

        new BukkitRunnable()
        {
          public void run()
          {
            time++;
          }
        }.runTaskLater(plugin, 45);
      }
    }.runTaskTimer(plugin, 0, 1);
  }

  @SuppressWarnings("deprecation")
  public static void Lightning(Player p)
  {
    Block location = p.getTargetBlock((HashSet<Byte>) null, 100);
    World world = p.getWorld();
    world.strikeLightning(location.getLocation());

    Location location2 = new Location(location.getWorld(),location.getX(),location.getY(),location.getZ());

    for (double i = 0; i <= Math.PI; i += Math.PI / 5) {
      double y = Math.cos(i);
      for (double a = 0; a < Math.PI * 2; a+= Math.PI / 5) {
        double x = Math.cos(a) * 0.5;
        double z = Math.sin(a) * 0.5;
        location2.add(x, y, z);
        location2.getWorld().spawnParticle(Particle.LAVA, location2 ,  1);
        location2.getWorld().spawnParticle(Particle.FLAME, location2 ,  1);
        location2.subtract(x, y, z);
      }
    }
    ArmorStand  armorStand = (ArmorStand) p.getWorld().spawnEntity(location2.add(0, 1, 0), EntityType.ARMOR_STAND);
    armorStand.setVisible(false);
    for(Entity e: armorStand.getNearbyEntities(5, 1, 5)){
  	  if(e.getType()==EntityType.PLAYER){
  		  Player pth = (Player) e;
  		  pth.playEffect(EntityEffect.HURT);
  		  pth.setHealth(pth.getHealth()-3);
  	  }
    }
    new BukkitRunnable(){
 	  public void run(){
 		  armorStand.remove();
 	  }
   }.runTaskLater(plugin, 60);
  }

  private static ArrayList<UUID> fly = new ArrayList<UUID>();

  public static void Fly(Player p)
  {
    if(!fly.contains(p.getUniqueId()))
    {
      p.setAllowFlight(true);
      p.setFlying(true);
      p.getLocation().add(0, 1.5, 0);
      fly.add(p.getUniqueId());
    }
    else
    {
      p.setFlying(false);
      p.setAllowFlight(false);
      fly.remove(p.getUniqueId());
      p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 2, false, true, Color.YELLOW));
      
      Location location1 = p.getLocation();
      int particles = 5;
      for (int i = 0; i < particles; i++) {
        float radius = 1F;
        double angle, x, z;
        angle = 2 * Math.PI * i / particles;
        x = Math.cos(angle) * radius;
        z = Math.sin(angle) * radius;
        location1.add(x, 0, z);
        p.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, location1, 1);
        location1.subtract(x, 0, z);
      }
    }
  }

  public static void flyParticles()
  {
    new BukkitRunnable()
    {
      public void run()
      {
        for(UUID u : Spells.fly)
        {
          if(fly.size() > -1)
          {
            Location location1 = Bukkit.getOfflinePlayer(u).getPlayer().getLocation();
            int particles = 15;
            for (int i = 0; i < particles; i++) {
              float radius = 0.3F;
              double angle, x, z;
              angle = 2 * Math.PI * i / particles;
              x = Math.cos(angle) * radius;
              z = Math.sin(angle) * radius;
              location1.add(x, 0, z);
              Location loc = location1;
              PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
              try
              {
                MathUtil.setValue(packet, "a", EnumParticle.CLOUD);
                MathUtil.setValue(packet, "b", (float) loc.getX());
                MathUtil.setValue(packet, "c", (float) loc.getY());
                MathUtil.setValue(packet, "d", (float) loc.getZ());
                MathUtil.setValue(packet, "e", 0);
                MathUtil.setValue(packet, "f", 0);
                MathUtil.setValue(packet, "g", 0);
                MathUtil.setValue(packet, "h", 0);
                MathUtil.setValue(packet, "i", 0);

                for (Player online : Bukkit.getOnlinePlayers()) {
                  ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
                }
                location1.subtract(x, 0, z);
              } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
          }
        }
      }
    }.runTaskTimer(plugin, 0, 1);
  }
}
