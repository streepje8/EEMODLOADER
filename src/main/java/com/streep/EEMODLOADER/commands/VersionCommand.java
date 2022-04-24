package com.streep.EEMODLOADER.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.streep.EEMODLOADER.Utils.ChatUtil;
import com.streep.EEMODLOADER.handlers.CommandHandler;

public class VersionCommand implements CommandHandler {

	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		sender.sendMessage(ChatUtil.format("&bVersion 0.0.1inDev"));
	}
}
