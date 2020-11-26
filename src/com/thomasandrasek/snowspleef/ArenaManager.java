package com.thomasandrasek.snowspleef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ArenaManager {
	private static Arena arena = null;
	private static boolean arenaActive = false;
	private static Location lobbyLocation = null;
	
	public static Arena getArena() {
		return arena;
	}
	
	public static boolean isArenaActive() {
		return arenaActive;
	}
	
	public static void resetArena(Arena arena) {
		for (double x = arena.getMinLocation().getX(); x <= arena.getMaxLocation().getX(); x++) {
			for (double z = arena.getMinLocation().getZ(); z <= arena.getMaxLocation().getZ(); z++) {
				Location currentBlockLocation = new Location(Bukkit.getWorld("world"), x, arena.getMaxLocation().getY(), z);
				
				currentBlockLocation.getBlock().setType(Material.SNOW_BLOCK);
			}
		}
	}
	
	public static void teleportPlayers(Arena arena) {
		Location location1 = new Location(Bukkit.getWorld("world"), arena.getMaxLocation().getX(), arena.getMaxLocation().getY()+1, arena.getMaxLocation().getZ());
		arena.getPlayerOne().teleport(location1);
		
		Location location2 = new Location(Bukkit.getWorld("world"), arena.getMinLocation().getX()+0.5, arena.getMaxLocation().getY()+1, arena.getMinLocation().getZ()+0.5);
		arena.getPlayerTwo().teleport(location2);
	}
	
	public static void keepPlayerInPlace(Arena arena, Player player, int playerNumber) {
		if (playerNumber == 1) {
			if (Math.abs(player.getLocation().getX() - arena.getMaxLocation().getX()) > 0.5 ||
					Math.abs(player.getLocation().getZ() - arena.getMaxLocation().getZ()) > 0.5) {
				
				Location location = new Location(Bukkit.getWorld("world"), arena.getMaxLocation().getX(), arena.getMaxLocation().getY()+1, arena.getMaxLocation().getZ());
				player.teleport(location);
			}
		}
		else if (playerNumber == 2) {
			if (Math.abs(player.getLocation().getX() - arena.getMinLocation().getX() - 0.5) > 0.5 ||
					Math.abs(player.getLocation().getZ() - arena.getMinLocation().getZ() - 0.5) > 0.5) {
				
				Location location = new Location(Bukkit.getWorld("world"), arena.getMinLocation().getX()+0.5, arena.getMaxLocation().getY()+1, arena.getMinLocation().getZ()+0.5);
				player.teleport(location);
			}
		}
	}
	
	public static boolean playerHasFallen(Arena arena, Player player) {
		if (arena.getMaxLocation().getY() - player.getLocation().getY() > 0.5) {
			return true;
		}
		
		return false;
	}
	
	public static void removeShovel(Player player) {
		for (int i = 0; i < player.getInventory().getContents().length; i++) {
			
			if (player.getInventory().getContents()[i] != null && 
					player.getInventory().getContents()[i].getType().equals(Material.NETHERITE_SHOVEL)) {
				
				if (player.getInventory().getContents()[i].getItemMeta().isUnbreakable()) {
					player.getInventory().remove(player.getInventory().getContents()[i]);
					break;
				}
			}
		}
	}
	
	public static void launchFirework(Location location) {
		Firework fw = (Firework) arena.getMaxLocation().getWorld().spawnEntity(location, EntityType.FIREWORK);
		FireworkMeta meta = fw.getFireworkMeta();
		
		FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.RED).withColor(Color.GREEN).withColor(Color.WHITE).withFade(Color.WHITE).with(Type.BALL_LARGE).trail(true).build();
		meta.addEffect(effect);
		meta.setPower(1);
		fw.setFireworkMeta(meta);
	}
	
	public static void runArena(Arena arena) {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("SnowSpleef"), new Runnable() {
			int teleportTime = 5*4;
			int waitTime = 5*4;
			int matchOverTime = 10*4;
			boolean matchOver = false;
			int playerFallen = -1;
			
			public void run() {
				if (teleportTime > 0) {
					if (teleportTime % 4 == 0) {
						arena.getPlayerOne().sendTitle(ChatColor.translateAlternateColorCodes('&', "Teleporting in: &c" + teleportTime/4), "", 0, 20, 0);
						arena.getPlayerOne().playSound(arena.getPlayerOne().getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1, 1);
						
						arena.getPlayerTwo().sendTitle(ChatColor.translateAlternateColorCodes('&', "Teleporting in: &c" + teleportTime/4), "", 0, 20, 0);
						arena.getPlayerTwo().playSound(arena.getPlayerTwo().getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1, 1);
						
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&2[&fSS&2] &a>> &fTeleporting players in: &c&l" + teleportTime/4));
					}
					teleportTime--;
				}
				else if (teleportTime == 0) {
					teleportPlayers(arena);
					teleportTime--;
				}
				else if (waitTime > 0) {
					if (waitTime % 4 == 0) {
						arena.getPlayerOne().sendTitle(ChatColor.translateAlternateColorCodes('&', "Starting in: &c" + waitTime/4), "", 0, 20, 0);
						arena.getPlayerOne().playSound(arena.getPlayerOne().getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
						
						arena.getPlayerTwo().sendTitle(ChatColor.translateAlternateColorCodes('&', "Starting in: &c" + waitTime/4), "", 0, 20, 0);
						arena.getPlayerTwo().playSound(arena.getPlayerTwo().getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
						
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&2[&fSS&2] &a>> &fMatch starting in: &c&l" + waitTime/4));
					}
					keepPlayerInPlace(arena, arena.getPlayerOne(), 1);
					keepPlayerInPlace(arena, arena.getPlayerTwo(), 2);
					waitTime--;
				}
				else if (waitTime == 0) {
					ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL);
					shovel.addEnchantment(Enchantment.DIG_SPEED, 5);
					ItemMeta meta = shovel.getItemMeta();
					meta.setUnbreakable(true);
					shovel.setItemMeta(meta);
					
					arena.getPlayerOne().getInventory().addItem(shovel);
					arena.getPlayerTwo().getInventory().addItem(shovel);
					
					arena.getPlayerOne().setInvulnerable(true);
					arena.getPlayerTwo().setInvulnerable(true);
					
					arenaActive = true;
					
					waitTime--;
				}
				else if (!matchOver) {
					arena.getPlayerOne().setFoodLevel(20);
					arena.getPlayerTwo().setFoodLevel(20);
					
					if (playerHasFallen(arena, arena.getPlayerOne()) || playerHasFallen(arena, arena.getPlayerTwo())) {
						if (playerHasFallen(arena, arena.getPlayerOne())) {
							Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&2[&fSS&2] &a>> &c&l" + arena.getPlayerTwo().getDisplayName() + "&f&l has won!"));
							arena.getPlayerOne().teleport(lobbyLocation);
							playerFallen = 1;
						}
						else {
							Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&2[&fSS&2] &a>> &c&l" + arena.getPlayerOne().getDisplayName() + "&f&l has won!"));
							arena.getPlayerTwo().teleport(lobbyLocation);
							playerFallen = 2;
						}
						
						for (int i = 0; i < Bukkit.getOnlinePlayers().toArray().length; i++) {
							Player player = (Player) Bukkit.getOnlinePlayers().toArray()[i];
							
							player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1, 1);
						}
						
						removeShovel(arena.getPlayerOne());
						removeShovel(arena.getPlayerTwo());
						
						matchOver = true;
						
						arenaActive = false;
					}
				}
				else if (matchOverTime > 0) {
					if (matchOverTime % 4 == 0) {
						Location location = new Location(arena.getMaxLocation().getWorld(), arena.getMaxLocation().getX(), arena.getMaxLocation().getY()+5, arena.getMaxLocation().getZ());
						launchFirework(location);
						
						location = new Location(arena.getMaxLocation().getWorld(), arena.getMinLocation().getX(), arena.getMaxLocation().getY()+5, arena.getMaxLocation().getZ());
						launchFirework(location);
						
						location = new Location(arena.getMaxLocation().getWorld(), arena.getMaxLocation().getX(), arena.getMaxLocation().getY()+5, arena.getMinLocation().getZ());
						launchFirework(location);
						
						location = new Location(arena.getMaxLocation().getWorld(), arena.getMinLocation().getX(), arena.getMaxLocation().getY()+5, arena.getMinLocation().getZ());
						launchFirework(location);
					}
					matchOverTime--;
				}
				else if (matchOverTime == 0) {
					if (playerFallen == 1) {
						arena.getPlayerTwo().teleport(lobbyLocation);
					}
					else if (playerFallen == 2) {
						arena.getPlayerOne().teleport(lobbyLocation);
					}
					
					arena.getPlayerOne().setInvulnerable(false);
					arena.getPlayerTwo().setInvulnerable(false);
					
					resetArena(arena);
					
					Bukkit.getScheduler().cancelTasks(Bukkit.getPluginManager().getPlugin("SnowSpleef"));
				}
			}
		}, 0, 5);
	}
	
	public static void startArenaCommand(Player player) {
		
		if (arena != null) {
			if (arena.getPlayerOne() != null && arena.getPlayerTwo() != null) {
				runArena(arena);
				
				player.sendMessage("Starting arena.");
			}
			else {
				player.sendMessage("Need to set both players first");
			}
		}
		else {
			player.sendMessage("Need to create an arena first.");
		}
	}
	
	public static boolean createArena(Location location1, Location location2) {
		if (arena == null) {
			arena = new Arena(location1, location2);
			resetArena(arena);
			return true;
		}
		
		return false;
	}
	
	public static void setPlayers(Arena arena, Player player1, Player player2) {
		arena.setPlayerOne(player1);
		arena.setPlayerTwo(player2);
	}
	
	public static void setPlayersCommand(Player player, String name1, String name2) {
		if (arena != null) {
			Player player1 = Bukkit.getPlayer(name1);
			Player player2 = Bukkit.getPlayer(name2);
			
			if (player1 != null && player2 != null) {
				setPlayers(arena, player1, player2);
				
				player.sendMessage("Arena has been set with players: " + name1 + " and " + name2);
				player1.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&fSS&2] &a>> &fYou are queued to play against &c&l" + player2.getDisplayName()));
				player2.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&fSS&2] &a>> &fYou are queued to play against &c&l" + player1.getDisplayName()));
			}
			else {
				player.sendMessage("One or both of the players is invalid");
			}
		}
		else {
			player.sendMessage("You must first create an arena before adding players.");
		}
	}
	
	public static void createArenaCommand(Player player) {
		Wand wand = WandManager.getWand(player.getDisplayName());
		
		if (wand != null) {
			if (wand.getLocationOne() != null && wand.getLocationTwo() != null) {
				if (createArena(wand.getLocationOne(), wand.getLocationTwo())) {
					player.sendMessage("Arena has been created.");
				}
				else {
					player.sendMessage("Arena has already been created.");
				}
			}
			else {
				player.sendMessage("You must set both locations before creating an arena.");
			}
		}
		else {
			player.sendMessage("Before creating an arena, you must have an arena wand to select locations.");
		}
	}
	
	public static void setLobbyLocationCommand(Player player) {
		lobbyLocation = player.getLocation();
		
		player.sendMessage("Set lobby location to your current location.");
	}
}
