package com.streep.EEMODLOADER.commands.core;

import org.bukkit.command.CommandSender;

import com.streep.EEMODLOADER.utils.ChatUtil;

public class Command {

	private CommandHandler handler;
	private String command;
	
	public Command(String command, CommandHandler handler) {
		this.command = command;
		this.handler = handler;
	}
	
	public void execute(CommandSender sender, org.bukkit.command.Command command, String label, String[] arguments) {
		if(command.getName().equalsIgnoreCase(this.command) && (handler.getPermission() == null || sender.hasPermission(handler.getPermission()))) {
			this.handler.execute(sender, command, label, arguments);
		} else {
			if(command.getName().equalsIgnoreCase(this.command)) {
				sender.sendMessage(ChatUtil.format("&4You do not have permission to perform this command!"));
			}
		}
	}
	
}
