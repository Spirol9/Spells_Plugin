package org.Spirol9.MagicWand.Listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Mobs implements Listener {

	private static Plugin plugin;

	public Mobs(Plugin plugin) {
		Mobs.plugin = plugin;
	}

	ArrayList<Entity> mobPets = new ArrayList<Entity>();

	@EventHandler
	public void mobAttacker(EntityTargetEvent e)
	{
		if(e.getTarget().getType() == EntityType.PLAYER)
		{
			Bukkit.broadcastMessage("meep meep");

			Player p = (Player) e.getTarget();
			if(mobPets.contains(e.getEntity()))
			{

				if(e.getTarget().getCustomName() == e.getEntity().getCustomName())
				{
					e.setCancelled(true);
					e.setTarget(null);
					Bukkit.broadcastMessage(" target name matches pet's name");
				}
				else if(p.getDisplayName() == p.getDisplayName())
				{
					e.setCancelled(true);
					e.setTarget(e.getEntity());
					e.setTarget(null);
					Bukkit.broadcastMessage(" target name matches owners name");

				}
			}
		}
	}

	@EventHandler
	public void setPet(PlayerInteractAtEntityEvent e)
	{
		ItemStack emerald = new ItemStack(Material.EMERALD);

		Player p = (Player) e.getPlayer();

		if(e.getPlayer().getInventory().getItemInMainHand().getType() == emerald.getType() || e.getPlayer().getInventory().getItemInOffHand().getType() == emerald.getType())
		{
			p.getInventory().remove(emerald);

			if(e.getRightClicked().getType() == EntityType.SKELETON)
			{
				Skeleton pet = (Skeleton) e.getRightClicked();

				pet.setCustomName(p.getName());
				pet.setCustomNameVisible(true);

				mobPets.add(pet);

				new BukkitRunnable()
				{
					public void run()
					{

						for(Entity e:p.getNearbyEntities(5, 1, 5)){


							if(e.getType()== EntityType.PLAYER){

								if(pet.getTarget().getCustomName() == pet.getCustomName())
								{
									pet.setTarget(null);
									Bukkit.broadcastMessage(" target name matches pet's name");
								}
								else if(pet.getTarget().getName() == p.getDisplayName())
								{
									pet.setTarget(null);
									Bukkit.broadcastMessage(" target name matches owners name");

								}
							}
						}
					}
				}.runTaskLater(plugin, 20);
			}

			if(e.getRightClicked().getType() == EntityType.ZOMBIE)
			{
				Zombie pet = (Zombie) e.getRightClicked();

				pet.setCustomName(p.getName());
				pet.setCustomNameVisible(true);
				pet.setGlowing(false);
				pet.setInvulnerable(false);
				pet.setGravity(true);

				mobPets.add(pet);

				new BukkitRunnable()
				{
					public void run()
					{
						pet.setTarget(null);
						pet.setTarget(null);
					}
				}.runTaskLater(plugin, 60);
			}


			Location location2 = e.getRightClicked().getLocation();
			Location location1 = e.getRightClicked().getLocation();
			Location location3 = e.getRightClicked().getLocation();
			int particles = 10;

			location1.add(0, 1, 0);
			location2.add(0, 1, 0);
			location3.add(0, 1, 0);

			for (int i = 0; i < particles; i++) {
				float radius = 0.4F;
				double angle, x, z;
				angle = 2 * Math.PI * i / particles;
				x = Math.cos(angle) * radius;
				z = Math.sin(angle) * radius;
				location1.add(x, 0, z);
				location3.add(x, -1.33, z);
				location2.add(x, -0.66, z);
				e.getRightClicked().getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location1, 1);
				e.getRightClicked().getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location2,  1);
				e.getRightClicked().getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location3, 1);
				location1.subtract(x, 0, z);
				location3.subtract(x, -1.33, z);
				location2.subtract(x, -0.66, z);
			} 
		}
	}
}
