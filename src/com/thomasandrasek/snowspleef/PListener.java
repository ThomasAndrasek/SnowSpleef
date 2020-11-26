package com.thomasandrasek.snowspleef;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PListener implements Listener{
	
	Main plugin;

    protected PListener(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    	if (event.getBlock().getType().equals(Material.SNOW_BLOCK)) {
    		if (ArenaManager.isArenaActive()) {
    			Arena arena = ArenaManager.getArena();
    			Player player = event.getPlayer();
    			
    			if (arena.getPlayerOne() != null && arena.getPlayerTwo() != null) {
    				if (arena.getPlayerOne().equals(player) || arena.getPlayerTwo().equals(player)) {
        				event.setCancelled(true);
        				event.getBlock().setType(Material.AIR);
        				ItemStack snowball = new ItemStack(Material.SNOWBALL);
        				event.getPlayer().getInventory().addItem(snowball);
        			}
    			}
    		}
    	}
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

    	if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_SHOVEL) &&
    			event.getPlayer().getInventory().getItemInMainHand().getItemMeta().isUnbreakable())
    	{
    		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
    			WandManager.setLocationOne(event.getPlayer().getDisplayName(), event.getClickedBlock().getLocation());
    			event.getPlayer().sendMessage("Set location 1");
    		}
    		else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
    			WandManager.setLocationTwo(event.getPlayer().getDisplayName(), event.getClickedBlock().getLocation());
    			event.getPlayer().sendMessage("Set location 2");
    		}
    		
    		event.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
    	if (event.getEntity().getType().equals(EntityType.SNOWBALL)) {
    		if (ArenaManager.isArenaActive()) {
    			Snowball ball = (Snowball) event.getEntity();
        		Player player = (Player) ball.getShooter();
        		Arena arena = ArenaManager.getArena();
        			
        		if (arena.getPlayerOne() != null && arena.getPlayerTwo() != null) {
        			if (player.equals(arena.getPlayerOne()) || player.equals(arena.getPlayerTwo())) {
        				if (event.getHitBlock() != null) {
        					if (event.getHitBlock().getType().equals(Material.SNOW_BLOCK)) {
            		        	event.getHitBlock().setType(Material.AIR);
            		        }
        				}
        			}
        		}
    		}
    	}
    }
}
