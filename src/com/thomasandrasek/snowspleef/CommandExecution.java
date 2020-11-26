package com.thomasandrasek.snowspleef;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecution {
	
	public static boolean executeCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("ss")) {
			if (args[0].equalsIgnoreCase("wand")) {
				WandManager.givePlayerWand((Player) sender);
				
				return true;
			}
			else if (args[0].equalsIgnoreCase("create-arena")) {
				ArenaManager.createArenaCommand((Player) sender);
				
				return true;
			}
			else if (args[0].equalsIgnoreCase("set-players")) {
				if (args.length == 3) {
					ArenaManager.setPlayersCommand((Player) sender, args[1], args[2]);
					
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase("start")) {
				ArenaManager.startArenaCommand((Player) sender);
				
				return true;
			}
			else if (args[0].equalsIgnoreCase("set-lobby-spawn")) {
				ArenaManager.setLobbyLocationCommand((Player) sender);
				
				return true;
			}
		}
		
		return false;
	}
}
