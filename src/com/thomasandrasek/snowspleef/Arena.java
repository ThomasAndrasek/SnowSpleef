package com.thomasandrasek.snowspleef;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {

	private Location minLocation;
	private Location maxLocation;
	private Player player1;
	private Player player2;
	
	
	public Arena(Location location1, Location location2) {
		double minX, maxX, minY, maxY, minZ, maxZ = 0;
		
		minX = Math.min(location1.getX(), location2.getX());
		minY = Math.min(location1.getY(), location2.getY());
		minZ = Math.min(location1.getZ(), location2.getZ());
		
		maxX = Math.max(location1.getX(), location2.getX());
		maxY = Math.max(location1.getY(), location2.getY());
		maxZ = Math.max(location1.getZ(), location2.getZ());
		
		this.minLocation = new Location(Bukkit.getWorld("world"), minX, minY, minZ);
		this.maxLocation = new Location(Bukkit.getWorld("world"), maxX, maxY, maxZ);
	}
	
	public Location getMinLocation() {
		return this.minLocation;
	}
	
	public Location getMaxLocation() {
		return this.maxLocation;
	}
	
	public Player getPlayerOne() {
		return this.player1;
	}
	
	public Player getPlayerTwo() {
		return this.player2;
	}
	
	public void setPlayerOne(Player player) {
		this.player1 = player;
	}
	
	public void setPlayerTwo(Player player) {
		this.player2 = player;
	}
	
	public void wipePlayers() {
		this.player1 = null;
		this.player2 = null;
	}
}
