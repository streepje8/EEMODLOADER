package com.streep.EEMODLOADER.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.modloader.EEMOD;
import com.streep.EEMODLOADER.modloader.MODLOADER;
import com.streep.EEMODLOADER.utils.ChatUtil;
public class ModsHandler implements CommandHandler {
	
	@Override
	public String getPermission() {
		return "EEMODLOADER.mods";
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		sender.sendMessage(ChatUtil.format("&f=== &bMODS &f==="));
		for(EEMOD mod : MODLOADER.getMods()) {
			sender.sendMessage(ChatUtil.format(mod.displayName + " &r&fby: " + mod.author + " &r&fusage name: " + mod.name));
		}
	}
}