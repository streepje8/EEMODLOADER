package com.streep.EEMODLOADER.core;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.streep.EEMODLOADER.handlers.CommandHandler;

public class Main extends JavaPlugin {

	private final ArrayList<CommandHandler> handlers = new ArrayList<CommandHandler>() {
		private static final long serialVersionUID = 1L;
		
	};
	
	@Override
	public void onEnable() {
		getLogger().info("Starting EE MOD LOADER");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Shutting down EE MOD LOADER");
	}
	
	public boolean onCommand(CommandSender  sender, Command command, String label, String[] args) {
	    for(CommandHandler handler : handlers) {
	    	handler.execute(sender, command, label, args);
	    }
		return true;
	}
}
