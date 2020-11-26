package com.thomasandrasek.snowspleef;

import org.bukkit.Location;

public class Wand {
	private String playerName;
	private Location location1;
	private Location location2;
	
	public Wand(String playerName) {
		this.playerName = playerName;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public Location getLocationOne() {
		return this.location1;
	}
	
	public Location getLocationTwo() {
		return this.location2;
	}
	
	public void setLocationOne(Location location) {
		this.location1 = location;
	}
	
	public void setLocationTwo(Location location) {
		this.location2 = location;
	}
}
