package com.streep.EEMODLOADER.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.JsonFile;

public class CraftHandler implements CommandHandler {
	@Override
	public String getPermission() {
		return "EEMODLOADER.craft";
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		if(arguments.length > 1) {
			String createDeletestring = arguments[0];
			String name = arguments[1];
			name = name.replaceAll("/", "__FORWARDSLASH__");
			name = name.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
			name = name.replaceAll("__FORWARDSLASH__", "/");
			String addon = "";
			if(name.lastIndexOf('/') > 0) {
				addon = "/" + name.substring(0,name.lastIndexOf('/'));
			}
			File folder = new File(EEMODLOADER.plugin.getDataFolder() + "/Crafts" + addon);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			if(createDeletestring.equalsIgnoreCase("create")) {
				sender.sendMessage(ChatUtil.format("EPIC DEBUG MESSAGE!!!"));
			} else {
				if(createDeletestring.equalsIgnoreCase("delete")) {
					File file = new File(EEMODLOADER.plugin.getDataFolder() + "/Crafts/" + name);
					if(file.exists()) {
						if(file.delete()) {
							sender.sendMessage(ChatUtil.format("&aFile deleted! Reload the server to apply the changes!"));
						} else {
							sender.sendMessage(ChatUtil.format("&4Could not delete the file for the craft. Please shut down the server and do it manually!"));
						}
					} else {
						sender.sendMessage(ChatUtil.format("&4The file you are trying to delete does not exist!"));
					}
				} else {
					sender.sendMessage(ChatUtil.format("&4Usage: /<command> <create/delete> <name>"));
				}
			}
		} else {
			sender.sendMessage(ChatUtil.format("&4Usage: /<command> <create/delete> <name>"));
		}
	}
}
