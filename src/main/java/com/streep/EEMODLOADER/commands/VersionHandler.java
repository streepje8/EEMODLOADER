package com.streep.EEMODLOADER.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.streep.EEMODLOADER.Utils.ChatUtil;
import com.streep.EEMODLOADER.commands.core.CommandHandler;

@org.bukkit.plugin.java.annotation.command.Command(name = "version", desc = "Plugin Version", permission = "EEMODLOADER.version", permissionMessage = "You do not have permission to run this command!", usage = "/<command>")
public class VersionHandler implements CommandHandler {
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		sender.sendMessage(ChatUtil.format("&bVersion 0.0.1inDev"));
	}
}
