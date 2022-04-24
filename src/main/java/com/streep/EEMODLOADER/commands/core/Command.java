package com.streep.EEMODLOADER.commands.core;

import org.bukkit.command.CommandSender;

public class Command {

	private CommandHandler handler;
	private String command;
	
	public Command(String command, CommandHandler handler) {
		this.command = command;
		this.handler = handler;
	}
	
	public void execute(CommandSender sender, org.bukkit.command.Command command, String label, String[] arguments) {
		if(command.getName().equalsIgnoreCase(this.command)) {
			this.handler.execute(sender, command, label, arguments);
		}
	}
	
}
