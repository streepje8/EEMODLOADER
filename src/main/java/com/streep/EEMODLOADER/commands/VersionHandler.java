package com.streep.EEMODLOADER.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.utils.ChatUtil;

public class VersionHandler implements CommandHandler {
	
	@Override
	public String getPermission() {
		return "EEMODLOADER.version";
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		sender.sendMessage(ChatUtil.format("&bVersion 0.0.2inDev"));
	}
}
