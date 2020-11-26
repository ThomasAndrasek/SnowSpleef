package com.thomasandrasek.snowspleef;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new PListener(this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	 @Override
	 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		 return CommandExecution.executeCommand(sender, cmd, label, args);
	 }
}
