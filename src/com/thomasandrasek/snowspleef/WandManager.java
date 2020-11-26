package com.thomasandrasek.snowspleef;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandManager {
	
	private static ArrayList<Wand> wands = new ArrayList<Wand>();

	public static void givePlayerWand(Player p) {
		Inventory inventory = p.getInventory();
		
		ItemStack wand = new ItemStack(Material.GOLDEN_SHOVEL);
		
		ItemMeta meta = wand.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lSS WAND"));
		meta.setUnbreakable(true);
		wand.setItemMeta(meta);
		
		inventory.addItem(wand);
		
		wands.add(new Wand(p.getDisplayName()));
		
		p.sendMessage("Adding wand to inventory.");
	}
	
	public static Wand getWand(String name) {
		for (int i = 0; i < wands.size(); i++) {
			if (wands.get(i).getPlayerName().equals(name)) {
				return wands.get(i);
			}
		}
		
		return null;
	}
	
	public static void setLocationOne(String name, Location location) {
		Wand wand = getWand(name);
		
		if (wand == null) {
			wand = new Wand(name);
			wands.add(wand);
		}
		wand.setLocationOne(location);
	}
	
	public static void setLocationTwo(String name, Location location) {
		Wand wand = getWand(name);
		
		if (wand == null) {
			wand = new Wand(name);
			wands.add(wand);
		}
		wand.setLocationTwo(location);
	}
}
